package org.example.project.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class AddViewModel() : ViewModel() {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

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
    // Запрос на сервер
    private val _listSwitchName = MutableStateFlow<List<String>>(emptyList())
    val listSwitchName: StateFlow<List<String>> = _listSwitchName

    fun loadSwitchNames() {
        viewModelScope.launch {
            try {
                // ① Use .body() to parse the JSON array into List<String>
                val list: List<String> = client
                    .get("http://127.0.0.1:8080/switch-names")
                    .body()
                _listSwitchName.value = list
            } catch (e: Exception) {
            }
        }
    }




    private val _swichType = MutableStateFlow("")
    val swichType: StateFlow<String> = _swichType

    fun updateSwichType(newSwichType: String) {
        _swichType.value = newSwichType
    }
    private val _listSwichType = MutableStateFlow<List<String>>(emptyList())
    val listSwichType: StateFlow<List<String>> = _listSwichType

    fun loadSwichType() {
        viewModelScope.launch {
            try {
                // ① Use .body() to parse the JSON array into List<String>
                val list: List<String> = client
                    .get("http://127.0.0.1:8080/switch-types")
                    .body()
                _listSwichType.value = list
            } catch (e: Exception) {
            }
        }
    }

    private val _keycapsType = MutableStateFlow("")
    val keycapsType: StateFlow<String> = _keycapsType

    fun updateKeycapsType(newKeycapsType: String) {
        _keycapsType.value = newKeycapsType
    }

    private val _listKeycapsType = MutableStateFlow<List<String>>(emptyList())
    val listKeycapsType: StateFlow<List<String>> = _listKeycapsType

    fun loadKeycapsType() {
        viewModelScope.launch {
            try {
                // ① Use .body() to parse the JSON array into List<String>
                val list: List<String> = client
                    .get("http://127.0.0.1:8080/keycap-profiles")
                    .body()
                _listKeycapsType.value = list
            } catch (e: Exception) {
            }
        }
    }

    private val _keycapsMaterial = MutableStateFlow("")
    val keycapsMaterial: StateFlow<String> = _keycapsMaterial

    fun updateKeycapsMaterial(newKeycapsMaterial: String) {
        _keycapsMaterial.value = newKeycapsMaterial
    }

    private val _listKeycapsMaterial = MutableStateFlow<List<String>>(emptyList())
    val listKeycapsMaterial: StateFlow<List<String>> = _listKeycapsMaterial

    fun loadKeycapsMaterial() {
        viewModelScope.launch {
            try {
                // ① Use .body() to parse the JSON array into List<String>
                val list: List<String> = client
                    .get("http://127.0.0.1:8080/keycap-materials")
                    .body()
                _listKeycapsMaterial.value = list
            } catch (e: Exception) {
            }
        }
    }

    private val _formFactor = MutableStateFlow("")
    val formFactor: StateFlow<String> = _formFactor

    fun updateFormFactor(newFormFactor: String) {
        _formFactor.value = newFormFactor
    }

    private val _listFormFactor = MutableStateFlow<List<String>>(emptyList())
    val listFormFactor: StateFlow<List<String>> = _listFormFactor

    fun loadFormFactor() {
        viewModelScope.launch {
            try {
                // ① Use .body() to parse the JSON array into List<String>
                val list: List<String> = client
                    .get("http://127.0.0.1:8080/layout-scales")
                    .body()
                _listFormFactor.value = list
            } catch (e: Exception) {
            }
        }
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