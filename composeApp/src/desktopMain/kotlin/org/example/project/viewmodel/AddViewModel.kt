package org.example.project.viewmodel

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.screens.addKeyBoard.AddKeyBoardEvent
import org.example.project.screens.addKeyBoard.AddKeyBoardUiState


class AddViewModel() : ViewModel() {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val _uiState = MutableStateFlow(AddKeyBoardUiState())
    val uiState: StateFlow<AddKeyBoardUiState> = _uiState

    fun onEvent(event: AddKeyBoardEvent) {
        viewModelScope.launch {

            when (event) {

                is AddKeyBoardEvent.AddKeyBoardName -> {
                    _uiState.update { it.copy(event.name) }
                }

                is AddKeyBoardEvent.AddKeyBoardDescription -> {
                    _uiState.update { it.copy(description = event.description) }
                }

                is AddKeyBoardEvent.AddKeyBoardSwichName -> {
                    _uiState.update { it.copy(swichName = event.swichName) }
                }

                is AddKeyBoardEvent.AddKeyBoardSwichType -> {
                    _uiState.update { it.copy(swichType = event.swichType) }
                }

                is AddKeyBoardEvent.AddKeyBoardKeycapsType -> {
                    _uiState.update { it.copy(keycapsType = event.keycapsType) }
                }

                is AddKeyBoardEvent.AddKeyBoardKeycapsMaterial -> {
                    _uiState.update { it.copy(keycapsMaterial = event.keycapsMaterial) }
                }

                is AddKeyBoardEvent.AddKeyBoardFormFactor -> {
                    _uiState.update { it.copy(formFactor = event.formFactor) }
                }

                is AddKeyBoardEvent.AddKeyBoardStockQuantity -> {
                    _uiState.update { it.copy(stockQuantity = event.stockQuantity) }
                }

                is AddKeyBoardEvent.AddKeyBoardPrice -> {
                    _uiState.update { it.copy(price = event.price) }
                }

                is AddKeyBoardEvent.AddKeyBoardListKeycapsMaterial -> {
                    _uiState.update { it.copy(listKeycapsMaterial = event.listKeycapsMaterial) }
                }

                is AddKeyBoardEvent.AddKeyBoardListKeycapsType -> {
                    _uiState.update { it.copy(listKeycapsType = event.listKeycapsType) }

                }

                is AddKeyBoardEvent.AddKeyBoardListSwichName -> {
                    _uiState.update { it.copy(listSwichName = event.listSwichName) }

                }

                is AddKeyBoardEvent.AddKeyBoardListSwichType -> {
                    _uiState.update { it.copy(listSwichType = event.listSwichType) }

                }

                is AddKeyBoardEvent.AddKeyBoardlistFormFactor -> {
                    _uiState.update { it.copy(listFormFactor = event.listFormFactor) }

                }

                is AddKeyBoardEvent.onClearButton -> {
                    _uiState.update { it.copy(listImages = emptyList()) }
                }

                is AddKeyBoardEvent.AddKeyBoardListImages -> {
                    _uiState.update { it.copy(listImages = it.listImages + event.listImages) }

                }

            }
        }
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
}