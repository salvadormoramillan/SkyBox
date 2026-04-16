package com.example.skybox.Navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skybox.AppScreen.HomeScreen
import com.example.skybox.UserScreen.FirstScreen
import com.example.skybox.UserScreen.LoginScreen
import com.example.skybox.UserScreen.RegisterScreen

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.First.ruta
    ) {
        composable(Rutas.First.ruta) {
            FirstScreen(
                OnLoginClick = { navController.navigate(Rutas.Login.ruta) },
                OnRegisteClick = { navController.navigate(Rutas.Register.ruta) }
            )
        }

        composable(Rutas.Login.ruta) {
            LoginScreen(
                OnLoginSuccess = { navController.navigate(Rutas.Home.ruta) },
                OnRegisterClick = { navController.navigate(Rutas.Register.ruta) }
            )
        }

        composable(Rutas.Register.ruta) {
            RegisterScreen(
                OnLoginClick = { navController.navigate(Rutas.Login.ruta) },
                OnRegisterSuccess = { navController.navigate(Rutas.Home.ruta) }
            )
            }

        composable(Rutas.Home.ruta) {
            HomeScreen()
        }

    }
}