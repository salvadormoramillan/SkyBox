package com.example.skybox.Navegacion

sealed class Rutas(val ruta: String) {
    data object First : Rutas("first")
    data object Login : Rutas("login")
    data object Register : Rutas("register")
    data object Home : Rutas("home")
}