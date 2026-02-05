package com.example.aiplantscanner

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.outlined.CameraAlt   // These two imports could be redundant
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(selectedScreen: MutableState<String>) {
    NavigationBar(tonalElevation = 8.dp) {

        NavigationBarItem(
            selected = selectedScreen.value == "camera",
            onClick = { selectedScreen.value = "camera" },
            label = { Text("Camera") },
            icon = { Icon(Icons.Default.Camera, contentDescription = "Camera") }
        )

        NavigationBarItem(
            selected = selectedScreen.value == "gallery",
            onClick = { selectedScreen.value = "gallery" },
            label = { Text("Gallery") },
            icon = { Icon(Icons.Default.Image, contentDescription = "Gallery") }
        )

        NavigationBarItem(
            selected = selectedScreen.value == "info",
            onClick = { selectedScreen.value = "info" },
            label = { Text("Info") },
            icon = { Icon(Icons.Filled.Info, contentDescription = "Info") }
        )
    }
}