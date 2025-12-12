package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.dulcedelicias.data.network.model.DetallePedido
import mx.edu.utez.dulcedelicias.data.network.model.Producto

@Composable
fun CarritoCard(
    producto: Producto,
    detallePedido: DetallePedido,
    onIncrement: (DetallePedido) -> Unit,
    onDecrement: (DetallePedido) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Precio unitario: $${"%.2f".format(detallePedido.precioUnitario)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Subtotal: $${"%.2f".format(detallePedido.cantidad * detallePedido.precioUnitario)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onDecrement(detallePedido) }) {
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Disminuir cantidad")
                    }
                    Text(
                        text = detallePedido.cantidad.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { onIncrement(detallePedido) }) {
                        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Aumentar cantidad")
                    }
                }
            }
        }
    }
}
