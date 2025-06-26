package org.example.project.screens.addKeyBoard

import androidx.compose.ui.graphics.ImageBitmap

data class AddKeyBoardUiState(
    val name: String = "",
    val description: String = "",
    val swichName: String = "",
    val swichType: String = "",
    val keycapsType: String = "",
    val keycapsMaterial: String = "",
    val formFactor: String = "",
    val stockQuantity: String = "",
    val price: String = "",

    val listSwichName: List<String> = emptyList(),
    val listSwichType: List<String> = emptyList(),
    val listKeycapsType: List<String> = emptyList(),
    val listKeycapsMaterial: List<String> = emptyList(),
    val listFormFactor: List<String> = emptyList(),
    val listImages: List<ImageBitmap> = emptyList()

)