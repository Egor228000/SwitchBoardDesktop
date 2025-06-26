package org.example.project.screens.addKeyBoard

import androidx.compose.ui.graphics.ImageBitmap

sealed class AddKeyBoardEvent {
    data class AddKeyBoardName(val name: String) : AddKeyBoardEvent()
    data class AddKeyBoardDescription(val description: String) : AddKeyBoardEvent()
    data class AddKeyBoardSwichName(val swichName: String) : AddKeyBoardEvent()
    data class AddKeyBoardSwichType(val swichType: String) : AddKeyBoardEvent()
    data class AddKeyBoardKeycapsType(val keycapsType: String) : AddKeyBoardEvent()
    data class AddKeyBoardKeycapsMaterial(val keycapsMaterial: String) : AddKeyBoardEvent()
    data class AddKeyBoardFormFactor(val formFactor: String) : AddKeyBoardEvent()
    data class AddKeyBoardStockQuantity(val stockQuantity: String) : AddKeyBoardEvent()
    data class AddKeyBoardPrice(val price: String) : AddKeyBoardEvent()

    data class AddKeyBoardListSwichName(val listSwichName: List<String>) : AddKeyBoardEvent()
    data class AddKeyBoardListSwichType(val listSwichType: List<String>) : AddKeyBoardEvent()
    data class AddKeyBoardListKeycapsType(val listKeycapsType: List<String>) : AddKeyBoardEvent()
    data class AddKeyBoardListKeycapsMaterial(val listKeycapsMaterial: List<String>) : AddKeyBoardEvent()
    data class AddKeyBoardlistFormFactor(val listFormFactor: List<String>) : AddKeyBoardEvent()
    data class AddKeyBoardListImages(val listImages:  ImageBitmap) : AddKeyBoardEvent()

    object  onClearButton : AddKeyBoardEvent()

}
