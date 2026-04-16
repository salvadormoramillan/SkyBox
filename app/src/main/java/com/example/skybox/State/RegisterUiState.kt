package com.example.skybox.State

data class RegisterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false
)