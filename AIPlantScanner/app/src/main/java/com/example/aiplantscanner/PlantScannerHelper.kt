package com.example.aiplantscanner

import android.graphics.Bitmap

object PlantScannerHelper {

    // Runs the new NonPlant model first before proceeding as usual

    fun classifyWithNonPlantCheck(
        modelHelper: ModelHelper,
        selectedPlant: String,
        bitmap: Bitmap
    ): Pair<String, Float> {

        val nonPlantInterpreter = modelHelper.getInterpreter("NonPlant")
        val nonPlantLabels = modelHelper.getLabels("NonPlant")
        val (nonPlantLabel, nonPlantConfidence) =
            modelHelper.classifySingleModel(nonPlantInterpreter, bitmap, nonPlantLabels)

        if (nonPlantLabel.contains("non-plant", ignoreCase = true)) {
            return "No plant detected" to nonPlantConfidence
        }

        return classifySingle(modelHelper, selectedPlant, bitmap)
    }

    // Classify using only one plant model

    fun classifySingle(
        modelHelper: ModelHelper,
        plant: String,
        bitmap: Bitmap
    ): Pair<String, Float> {

        val interpreter = modelHelper.getInterpreter(plant)
        val labels = modelHelper.getLabels(plant)

        return modelHelper.classifySingleModel(interpreter, bitmap, labels)
    }

    // General mode (Runs all 5 models, currently decrepit but will be useful for future upgrades.)
    fun classifyGeneral(
        modelHelper: ModelHelper,
        bitmap: Bitmap
    ): Pair<String, Float> {

        return modelHelper.classifyGeneral(bitmap)
    }

    fun formatResult(label: String, confidence: Float): String {
        return "$label (${String.format("%.2f", confidence)}%)"
    }

    fun getTreatmentFor(label: String): String {
        val info = PlantDatabase.getPlant(label)
        return info?.treatment ?: "No treatment information available."
    }
}