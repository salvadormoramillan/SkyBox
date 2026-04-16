package com.example.skybox.AppViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.skybox.AppState.HomeUiState
import com.google.firebase.auth.FirebaseAuth



class HomeViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadUser()
    }

    private fun loadUser() {
        val user = auth.currentUser

        val name = user?.displayName
            ?: user?.email?.substringBefore("@")
            ?: "Usuario"

        uiState = uiState.copy(
            userName = name,
            isLoading = false
        )
    }
}