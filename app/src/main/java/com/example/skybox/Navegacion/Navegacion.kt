package com.example.skybox.Navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skybox.AppScreen.DocumentScreen
import com.example.skybox.AppScreen.HomeScreen
import com.example.skybox.AppViewModel.HomeViewModel
import com.example.skybox.UserScreen.FirstScreen
import com.example.skybox.UserScreen.LoginScreen
import com.example.skybox.UserScreen.RegisterScreen

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()

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
            HomeScreen(
                viewModel = homeViewModel,
                onCreateDocument = { documentId ->
                    navController.navigate(Rutas.Editor.crearRuta(documentId))
                },
                onOpenDocument = { documentId ->
                    navController.navigate(Rutas.Editor.crearRuta(documentId))
                }
            )
        }

        composable(
            route = Rutas.Editor.ruta,
            arguments = listOf(
                navArgument("documentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("documentId") ?: ""

            DocumentScreen(
                documentId = documentId,
                viewModel = homeViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}