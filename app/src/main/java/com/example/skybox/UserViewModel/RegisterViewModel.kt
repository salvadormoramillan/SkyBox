package com.example.skybox.UserViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.skybox.State.RegisterUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val cleanName = name.trim()
        val cleanEmail = email.trim()

        when {
            cleanName.isEmpty() -> {
                uiState = uiState.copy(errorMessage = "Ingresa tu nombre")
                return
            }

            cleanEmail.isEmpty() -> {
                uiState = uiState.copy(errorMessage = "Ingresa tu correo")
                return
            }

            password.isEmpty() -> {
                uiState = uiState.copy(errorMessage = "Ingresa tu contraseña")
                return
            }

            password.length < 6 -> {
                uiState = uiState.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
                return
            }

            password != confirmPassword -> {
                uiState = uiState.copy(errorMessage = "Las contraseñas no coinciden")
                return
            }
        }

        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null,
            success = false
        )

        auth.createUserWithEmailAndPassword(cleanEmail, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(cleanName)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                user.reload().addOnCompleteListener {
                                    uiState = uiState.copy(
                                        isLoading = false,
                                        errorMessage = null,
                                        success = true
                                    )
                                }
                            } else {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    errorMessage = "Usuario creado, pero no se pudo guardar el nombre",
                                    success = false
                                )
                            }
                        }
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = task.exception?.message ?: "No se pudo registrar el usuario",
                        success = false
                    )
                }
            }
    }

    fun clearMessage() {
        uiState = uiState.copy(errorMessage = null)
    }

    fun consumeSuccess() {
        uiState = uiState.copy(success = false)
    }
}