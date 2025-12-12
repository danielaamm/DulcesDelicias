package mx.edu.utez.dulcedelicias.data.network.navigation

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.dulcedelicias.data.network.model.DetalleData
import mx.edu.utez.dulcedelicias.ui.screens.LoginScreen
import mx.edu.utez.dulcedelicias.ui.screens.HomeScreen
import mx.edu.utez.dulcedelicias.ui.screens.CarritoScreen
import mx.edu.utez.dulcedelicias.ui.screens.ConfirmarPedidoScreen
import mx.edu.utez.dulcedelicias.ui.screens.components.ProductoScreenAdmin
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.CarritoViewModel
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.ProductoViewModel
val CafeOscuroPrincipal = Color(0xFF3E2723)
val CafeClaroFondo = Color(0xFFF5F5DC)
val CafeMedioSeleccion = Color(0xFFD7CCC8)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as Application
    val carritoViewModel: CarritoViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == "home" || currentRoute == "carrito") {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(navController = navController)
            }
            composable("home") {
                val productoViewModel: ProductoViewModel = viewModel(
                    factory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
                )
                HomeScreen(
                    navController = navController,
                    viewModel = productoViewModel,
                    carritoViewModel = carritoViewModel
                )
            }
            composable("carrito") {
                CarritoScreen(navController = navController, viewModel = carritoViewModel)
            }
            composable("confirmarPedido") {
                ConfirmarPedidoScreen(
                    navController = navController,
                    viewModel = carritoViewModel,
                    detalles = carritoViewModel.carrito.map { item ->
                        DetalleData(
                            idProducto = item.productoId,
                            cantidad = item.cantidad
                        )
                    }
                )
            }
            composable("screenAdmin") {
                val productoViewModel: ProductoViewModel = viewModel(
                    factory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
                )
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

    NavigationBar(
        containerColor = Color(0xFFD7CCC8),
        tonalElevation = 3.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) CafeOscuroPrincipal else CafeOscuroPrincipal.copy(alpha = 0.5f)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) CafeOscuroPrincipal else CafeOscuroPrincipal.copy(alpha = 0.5f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = CafeMedioSeleccion
                )
            )
        }
    }
}

data class NavItem(val route: String, val label: String, val icon: ImageVector)