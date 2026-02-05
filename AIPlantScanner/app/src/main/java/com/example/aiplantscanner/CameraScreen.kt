package com.example.aiplantscanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.core.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraScreen(
    modelHelper: ModelHelper,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var predictionText by remember { mutableStateOf("Tap Capture to Scan the Leaf") }
    var treatmentText by remember { mutableStateOf("") }

    val imageCapture = remember { ImageCapture.Builder().build() }
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    var selectedPlant by remember { mutableStateOf("Calamansi") }

    val plantOptions = listOf(
        "Calamansi",
        "Ampalaya",
        "Sitaw",
        "Gabi",
        "Talong"
    )

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 170.dp),
            verticalArrangement = Arrangement.Top
        ) {

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().apply {
                            setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )
                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                }
            )

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = predictionText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = treatmentText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(selectedPlant)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    plantOptions.forEach { plant ->
                        DropdownMenuItem(
                            text = { Text(plant) },
                            onClick = {
                                selectedPlant = plant
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val file = context.cacheDir.resolve("temp.jpg")
                    val output = ImageCapture.OutputFileOptions.Builder(file).build()

                    imageCapture.takePicture(
                        output,
                        cameraExecutor,
                        object : ImageCapture.OnImageSavedCallback {

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val bmp = BitmapFactory.decodeFile(file.absolutePath)
                                val scaled = Bitmap.createScaledBitmap(bmp, 224, 224, true)

                                val (finalLabel, confidence) = PlantScannerHelper.classifyWithNonPlantCheck(
                                    modelHelper,
                                    selectedPlant,
                                    scaled
                                )

                                val plantInfo = PlantDatabase.getPlant(finalLabel)

                                predictionText =
                                    "${plantInfo?.commonName ?: finalLabel} (${String.format("%.1f", confidence)}%)"

                                treatmentText =
                                    "Scientific Name: ${plantInfo?.scientificName ?: "N/A"}\n\n" +
                                            "Description:\n${plantInfo?.description ?: "No description available."}\n\n" +
                                            "Treatment:\n${plantInfo?.treatment ?: "No treatment available."}"
                            }

                            override fun onError(exception: ImageCaptureException) {
                                predictionText = "Capture failed: ${exception.message}"
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Capture & Scan")
            }
        }
    }
}
