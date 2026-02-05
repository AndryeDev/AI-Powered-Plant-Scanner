package com.example.aiplantscanner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import org.tensorflow.lite.Interpreter
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import android.content.res.AssetFileDescriptor

class ModelHelper(private val context: Context) {

    private val modelFiles = mapOf(
        "Calamansi" to "calamansi_model.tflite",
        "Ampalaya" to "ampalaya_model_unquant.tflite",
        "Sitaw"     to "sitaw_model_unquant.tflite",
        "Gabi"      to "gabi_model_unquant.tflite",
        "Talong"      to "talong_model_unquant.tflite",
        "NonPlant" to "non_plant_objects_model_unquant.tflite",
    )

    private val labelFiles = mapOf(
        "Calamansi" to "calamansi_labels.txt",
        "Ampalaya"  to "ampalaya_labels.txt",
        "Sitaw"     to "sitaw_labels.txt",
        "Gabi"      to "gabi_labels.txt",
        "Talong"      to "talong_labels.txt",
        "NonPlant" to "non_plant_objects_labels.txt",
    )

    private val interpreters = mutableMapOf<String, Interpreter>()
    private val labelsCache = mutableMapOf<String, List<String>>()

    // Load a single model
    fun getInterpreter(plant: String): Interpreter {
        return interpreters.getOrPut(plant) {
            val modelFileName = modelFiles[plant] ?: error("Model not found for $plant")
            Interpreter(loadModelFile(modelFileName))
        }
    }

    fun getLabels(plant: String): List<String> {
        return labelsCache.getOrPut(plant) {
            val fileName = labelFiles[plant] ?: error("Labels not found for $plant")
            context.assets.open(fileName).bufferedReader().useLines { it.toList() }
        }
    }

    // Load all models (general mode, decrepit)
    fun getAllModels(): List<Triple<String, Interpreter, List<String>>> {
        return modelFiles.keys.map { plant ->
            Triple(plant, getInterpreter(plant), getLabels(plant))
        }
    }

    fun classifySingleModel(
        interpreter: Interpreter,
        bitmap: Bitmap,
        labels: List<String>
    ): Pair<String, Float> {

        val inputBuffer = convertBitmapToByteBuffer(bitmap)
        val output = Array(1) { FloatArray(labels.size) }

        interpreter.run(inputBuffer, output)

        val confidences = output[0]
        val maxIndex = confidences.indices.maxByOrNull { confidences[it] } ?: 0

        return labels[maxIndex] to confidences[maxIndex] * 100
    }

    // Classify using all models, in contrast with classifySingleModel
    fun classifyGeneral(bitmap: Bitmap): Pair<String, Float> {
        val results = mutableListOf<Pair<String, Float>>()

        for ((_, interpreter, labels) in getAllModels()) {
            val (label, confidence) = classifySingleModel(interpreter, bitmap, labels)
            results.add(label to confidence)
        }

        return results.maxByOrNull { it.second }!!
    }

    private fun loadModelFile(modelName: String): MappedByteBuffer {
        val fileDescriptor: AssetFileDescriptor = context.assets.openFd(modelName)
        val inputStream = fileDescriptor.createInputStream()
        val fileChannel = inputStream.channel
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            fileDescriptor.startOffset,
            fileDescriptor.declaredLength
        )
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val inputSize = 224
        val byteBuffer =
            ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3).order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputSize * inputSize)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var index = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val pixel = pixels[index++]

                byteBuffer.putFloat(((pixel shr 16) and 0xFF) / 255f)
                byteBuffer.putFloat(((pixel shr 8) and 0xFF) / 255f)
                byteBuffer.putFloat((pixel and 0xFF) / 255f)
            }
        }

        return byteBuffer
    }
}

fun uriToBitmap(context: Context, uri: Uri, width: Int, height: Int): Bitmap {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val original = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()
    return Bitmap.createScaledBitmap(original, width, height, true)
}
