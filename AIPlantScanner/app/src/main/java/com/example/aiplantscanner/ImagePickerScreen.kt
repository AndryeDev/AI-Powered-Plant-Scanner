package com.example.aiplantscanner

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagePickerScreen(
    modelHelper: ModelHelper,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var predictionText by remember { mutableStateOf("No scan yet") }
    var treatmentText by remember { mutableStateOf("") }

    var selectedPlant by remember { mutableStateOf("Calamansi") }

    val plantOptions = listOf(
        "Calamansi",
        "Ampalaya",
        "Sitaw",
        "Gabi",
        "Talong"
    )

    var expanded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
        uri ?: return@rememberLauncherForActivityResult

        val bitmap = uriToBitmap(context, uri, 224, 224)

// Uses the new wrapper that checks for non-plant first
        val (finalLabel, confidence) = PlantScannerHelper.classifyWithNonPlantCheck(
            modelHelper,
            selectedPlant,
            bitmap
        )


        predictionText = finalLabel // Already handles "No plant detected"

        val plantInfo = PlantDatabase.getPlant(finalLabel)
        treatmentText = if (finalLabel == "No plant detected") {
            ""
        } else {
            "Scientific Name: ${plantInfo?.scientificName ?: "N/A"}\n\n" +
                    "Description:\n${plantInfo?.description ?: "No description available."}\n\n" +
                    "Treatment:\n${plantInfo?.treatment ?: "No treatment available."}"
        }

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 170.dp),
            verticalArrangement = Arrangement.Top
        ) {

            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(MaterialTheme.colorScheme.inverseOnSurface),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))

            if (predictionText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            predictionText,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            treatmentText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
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
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(selectedPlant)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    plantOptions.forEach { plant ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    plant,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = {
                                selectedPlant = plant
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Pick Image From Gallery")
            }
        }
    }
}
