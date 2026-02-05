package com.example.aiplantscanner

data class PlantInfo(
    val label: String,
    val commonName: String,
    val condition: String,
    val scientificName: String,
    val description: String,
    val treatment: String
)