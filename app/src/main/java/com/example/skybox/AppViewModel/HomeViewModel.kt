package com.example.skybox.AppViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.skybox.AppState.Document
import com.example.skybox.AppState.HomeUiState
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class HomeViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadUser()
    }

    private fun loadUser() {
        val user = auth.currentUser

        if (user != null) {
            user.reload().addOnCompleteListener {
                val refreshedUser = auth.currentUser

                val name = refreshedUser?.displayName?.takeIf { it.isNotBlank() }
                    ?: refreshedUser?.email?.substringBefore("@")
                    ?: "Usuario"

                uiState = uiState.copy(
                    userName = name,
                    isLoading = false
                )
            }
        } else {
            uiState = uiState.copy(
                userName = "Usuario",
                isLoading = false
            )
        }
    }

    fun createDocument(): String {
        val newDocument = Document(
            id = UUID.randomUUID().toString(),
            title = "Nuevo documento ${uiState.documents.size + 1}",
            content = ""
        )

        uiState = uiState.copy(
            documents = uiState.documents + newDocument
        )

        return newDocument.id
    }

    fun getDocumentById(documentId: String): Document? {
        return uiState.documents.find { it.id == documentId }
    }

    fun updateDocumentContent(documentId: String, newContent: String) {
        uiState = uiState.copy(
            documents = uiState.documents.map { document ->
                if (document.id == documentId) {
                    document.copy(content = newContent)
                } else {
                    document
                }
            }
        )
    }

    fun updateDocumentTitle(documentId: String, newTitle: String) {
        uiState = uiState.copy(
            documents = uiState.documents.map { document ->
                if (document.id == documentId) {
                    document.copy(title = newTitle)
                } else {
                    document
                }
            }
        )
    }
}