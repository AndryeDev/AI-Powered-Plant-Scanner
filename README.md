# AI-Powered Plant Scanner
Android app using AI image recognition technology to identify plant diseases and provide localized care recommendations for common crops in Daet, Camarines Norte.

This is an Android application that leverages custom trained image recognition AI to identify plant health conditions.<br>
The app uses six TensorFlow Lite models running entirely on-device. One model is trained to determine whether a scanned image contains a plant or a non-plant object. If a plant is detected, the app proceeds to use one of five plant specific models to classify the plant as healthy or diseased.<br>
When a plant is successfully identified, the app displays its common name, scientific name, description, and recommended treatment information. Images can be scanned using either the device camera or the image gallery.<br>

## Supported Plants
- Calamansi (Citrus × microcarpa)
- Ampalaya (Momordica charantia)
- Sitaw (Vigna unguiculata subsp. sesquipedalis)
- Gabi (Colocasia esculenta)
- Talong (Solanum melongena)

## AI Models
Each plant has a separate TensorFlow Lite model trained on healthy and diseased leaves.<br>
A non-plant model detects non-leaf images to prevent false positives.

## Requirements
- **Android Studio**: Jellyfish 2023.3.1
- **Kotlin**: 1.9.0
- **Android SDK**: 36

## Installation
1. Clone the repository.
2. Open the project in Android Studio.
3. Ensure Gradle builds successfully.
4. Run the app on an emulator or physical device.

## File Structure
- app/src/main/java/com/example/aiplantscanner/ - Kotlin source files
- app/src/main/assets/ - TFLite models and label files
- gradle/wrapper/ - Gradle wrapper files for reproducible builds

## Usage
1. Select a plant from the dropdown menu or set it to general mode. (which automatically picks the model based on the image).
2. Capture an image of the selected leaf or pick an image from your gallery.
3. The app will indicate if the image contains a plant leaf or not.
4. If a plant is detected, its condition, treatment, and additional information is displayed.

## Notes
- Designed for academic demonstration.<br>
- Works offline. All inference is on-device.<br>
- Only tested on selected local crops (Calamansi, Ampalaya, Sitaw, Gabi, Talong).<br>
- The general mode setting is currently disabled to maintain reliable and interpretable classification results. However, the underlying code has been retained for future upgrades.
