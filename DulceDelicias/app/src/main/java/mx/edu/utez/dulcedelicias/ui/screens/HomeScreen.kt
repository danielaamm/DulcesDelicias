package mx.edu.utez.dulcedelicias.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
        topBar = {
            Surface(shadowElevation = 8.dp) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                "Dulce Delicias",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PrimaryBrown,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        Button(
                            onClick = { navController.navigate("login") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = PrimaryBrown
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = "Iniciar sesión",
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBrown
                            )
                        }
                    }
                )
            }
        },
        containerColor = BackgroundCream
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
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
