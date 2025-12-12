package mx.edu.utez.dulcedelicias.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.ui.screens.components.ProductoCardHome
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.CarritoViewModel
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.ProductoViewModel
import mx.edu.utez.dulcedelicias.ui.theme.PrimaryBrown
import mx.edu.utez.dulcedelicias.ui.theme.BackgroundCream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val productos by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) { viewModel.getProducts() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Dulce Delicias") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
            }

            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(productos) { producto ->
                    ProductoCardHome(
                        producto = producto,
                        onAddToCart = { carritoViewModel.agregarProducto(producto) }
                    )
                }
            }
        }
    }
}
