package org.example.project.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AddViewModel() : ViewModel() {

    //drag&drop
    private val _images = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val images: StateFlow<List<ImageBitmap>> = _images


    fun clearImage() {
        viewModelScope.launch {
            _images.value = emptyList()
        }
    }
    fun addImage(img: ImageBitmap) {
        _images.value = _images.value + img
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun updateName(newName: String) {
        _name.value = newName
    }

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    private val _swichName = MutableStateFlow("")
    val swichName: StateFlow<String> = _swichName

    fun updateSwichName(newSwichName: String) {
        _swichName.value = newSwichName
    }
    private val _swichType = MutableStateFlow("")
    val swichType: StateFlow<String> = _swichType

    fun updateSwichType(newSwichType: String) {
        _swichType.value = newSwichType
    }

    private val _keycapsType = MutableStateFlow("")
    val keycapsType: StateFlow<String> = _keycapsType

    fun updateKeycapsType(newKeycapsType: String) {
        _keycapsType.value = newKeycapsType
    }

    private val _keycapsMaterial = MutableStateFlow("")
    val keycapsMaterial: StateFlow<String> = _keycapsMaterial

    fun updateKeycapsMaterial(newKeycapsMaterial: String) {
        _keycapsMaterial.value = newKeycapsMaterial
    }

    private val _formFactor = MutableStateFlow("")
    val formFactor: StateFlow<String> = _formFactor

    fun updateFormFactor(newFormFactor: String) {
        _formFactor.value = newFormFactor
    }

    private val _price = MutableStateFlow("")
    val price: StateFlow<String> = _price

    fun updatePrice(newPrice: String) {
        _price.value = newPrice
    }

    private val _stockQuantity = MutableStateFlow("")
    val stockQuantity: StateFlow<String> = _stockQuantity

    fun updateStockQuantity(newStockQuantity: String) {
        _stockQuantity.value = newStockQuantity
    }


}