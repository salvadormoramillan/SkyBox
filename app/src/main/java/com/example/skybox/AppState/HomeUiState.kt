package com.example.skybox.AppState

data class HomeUiState(
    val userName: String = "Usuario",
    val isLoading: Boolean = true,
    val documents: List<Document> = emptyList()
)