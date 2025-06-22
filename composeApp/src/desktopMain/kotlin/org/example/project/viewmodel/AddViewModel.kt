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
}