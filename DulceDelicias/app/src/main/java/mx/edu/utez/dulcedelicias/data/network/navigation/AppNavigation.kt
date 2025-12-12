package mx.edu.utez.dulcedelicias.data.network.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector
import mx.edu.utez.dulcedelicias.ui.screens.LoginScreen
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.ProductoViewModel
import mx.edu.utez.dulcedelicias.ui.screens.HomeScreen
import mx.edu.utez.dulcedelicias.ui.screens.CarritoScreen
import mx.edu.utez.dulcedelicias.ui.screens.components.ProductoScreenAdmin
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.CarritoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Crear el CarritoViewModel aquí para que sea compartido
    val carritoViewModel: CarritoViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("home") {
                val productoViewModel: ProductoViewModel = viewModel()
                HomeScreen(
                    navController = navController,
                    viewModel = productoViewModel,
                    carritoViewModel = carritoViewModel
                )
            }
            composable("carrito") {
                CarritoScreen(
                    navController = navController,
                    viewModel = carritoViewModel
                )
            }
            composable("screenAdmin") {
                val productoViewModel: ProductoViewModel = viewModel()
                ProductoScreenAdmin(navController = navController, viewModel = productoViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavItem("home", "Home", Icons.Filled.Home),
        NavItem("carrito", "Carrito", Icons.Filled.ShoppingCart)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}


data class NavItem(val route: String, val label: String, val icon: ImageVector)