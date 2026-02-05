package com.example.aiplantscanner

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding


@Composable
fun AppNavigation(modelHelper: ModelHelper) {
    var selectedScreen = remember { mutableStateOf("camera") }

    Scaffold(
        topBar = {
            BottomBar(selectedScreen)
        }
    ) { padding ->
        when (selectedScreen.value) {
            "camera" ->
                CameraScreen(
                    modelHelper = modelHelper,
                    modifier = Modifier.padding(padding)
                )
            "gallery" ->
                ImagePickerScreen(
                    modelHelper = modelHelper,
                    modifier = Modifier.padding(padding)
                )
                "info" ->
            InformationScreen(
            modifier = Modifier.padding(padding)
            )
        }
    }
}
