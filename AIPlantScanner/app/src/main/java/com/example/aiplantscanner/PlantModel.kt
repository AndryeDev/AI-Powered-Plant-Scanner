package com.example.aiplantscanner

enum class PlantModel(
    val key: String,
    val modelFile: String,
    val labelFile: String
) {
    CALAMANSI("Calamansi", "calamansi_model.tflite", "calamansi_labels.txt"),
    AMPALAYA("Ampalaya", "ampalaya_model_unquant.tflite", "ampalaya_labels.txt"),
    SITAW("Sitaw", "sitaw_model_unquant.tflite", "sitaw_labels.txt"),
    GABI("Gabi", "gabi_model_unquant.tflite", "gabi_labels.txt"),
    TALONG("Talong", "talong_model_unquant.tflite", "talong_labels.txt")
}
