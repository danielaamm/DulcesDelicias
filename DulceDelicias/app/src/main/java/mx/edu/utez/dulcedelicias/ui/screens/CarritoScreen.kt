package mx.edu.utez.dulcedelicias.ui.screens

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.dulcedelicias.ui.screens.components.CarritoCard
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.CarritoViewModel
import mx.edu.utez.dulcedelicias.data.local.model.CarritoItemEntity
import mx.edu.utez.dulcedelicias.data.network.model.DetallePedido
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.ui.theme.PrimaryBrown
import mx.edu.utez.dulcedelicias.ui.theme.BackgroundCream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: CarritoViewModel) {
    val context = LocalContext.current
    val detallePedidos = viewModel.carrito
    val subtotal = detallePedidos.sumOf { it.cantidad * it.precioUnitario }
    val envio = 3.50
    val total = subtotal + envio

    var showDialog by remember { mutableStateOf(false) }
    var nombreCliente by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            "Carrito",
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
                )
            )
        },
        containerColor = BackgroundCream
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(detallePedidos) { item: CarritoItemEntity ->
                CarritoCard(
                    producto = Producto(
                        id = item.productoId,
                        nombre = item.nombre,
                        precio = item.precioUnitario,
                        descripcion = "",
                        disponible = true,
                        imagenUrl = "",
                        stock = 1
                    ),
                    detallePedido = DetallePedido(
                        idDetalle = 0,
                        idPedido = 0,
                        idProducto = item.productoId,
                        cantidad = item.cantidad,
                        precioUnitario = item.precioUnitario
                    ),
                    onIncrement = { viewModel.incrementar(item) },
                    onDecrement = { viewModel.decrementar(item) }
                )
            }

            // ✅ Totales y botón al final
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        "Subtotal: $${"%.2f".format(subtotal)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryBrown
                    )
                    Text(
                        "Envío: $${"%.2f".format(envio)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryBrown.copy(alpha = 0.8f)
                    )
                    Text(
                        "Total de compra: $${"%.2f".format(total)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryBrown
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { navController.navigate("confirmarPedido") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = detallePedidos.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBrown,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Confirmar pedido", fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }

    // ✅ Modal para ingresar nombre y dirección
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.confirmarPedido(
                            nombreCliente = nombreCliente,
                            ubicacion = ubicacion,
                            envio = envio,
                            onSuccess = { idPedido ->
                                Toast.makeText(context, "Pedido creado: $idPedido", Toast.LENGTH_LONG).show()
                                showDialog = false
                                navController.navigate("home")
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    enabled = nombreCliente.isNotBlank() && ubicacion.isNotBlank(),
                    colors = ButtonDefaults.textButtonColors(contentColor = PrimaryBrown)
                ) {
                    Text("Confirmar", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }
            },
            title = { Text("Confirmar pedido", color = PrimaryBrown, fontWeight = FontWeight.ExtraBold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = nombreCliente,
                        onValueChange = { nombreCliente = it },
                        label = { Text("Nombre del cliente") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ubicacion,
                        onValueChange = { ubicacion = it },
                        label = { Text("Ubicación") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            containerColor = BackgroundCream
        )
    }
}
