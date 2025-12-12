package mx.edu.utez.dulcedelicias.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import mx.edu.utez.dulcedelicias.ui.screens.components.CarritoList
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavHostController, viewModel: CarritoViewModel) {
    val context = LocalContext.current
    val detallePedidos = viewModel.carrito
    val subtotal = detallePedidos.sumOf { it.second.cantidad * it.second.precioUnitario }
    val envio = 3.50
    val total = subtotal + envio

    Scaffold(
        topBar = { TopAppBar(title = { Text("Carrito") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CarritoList(
                detallePedidos = detallePedidos,
                onIncrement = { viewModel.incrementar(it) },
                onDecrement = { viewModel.decrementar(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Subtotal: $${"%.2f".format(subtotal)}")
                Text("Envío: $${"%.2f".format(envio)}")
                Text("Total: $${"%.2f".format(total)}", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.confirmarPedido(
                            nombreCliente = "Cliente Demo",
                            ubicacion = "Tlaltizapán",
                            onSuccess = { idPedido ->
                                Toast.makeText(context, "Pedido creado: $idPedido", Toast.LENGTH_LONG).show()
                                navController.navigate("home")
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar pedido")
                }
            }
        }
    }
}
