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
import mx.edu.utez.dulcedelicias.ui.theme.PrimaryBrown
import mx.edu.utez.dulcedelicias.ui.theme.BackgroundCream

@Composable
fun CarritoCard(
    producto: Producto,
    detallePedido: DetallePedido,
    onIncrement: (DetallePedido) -> Unit,
    onDecrement: (DetallePedido) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCream),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryBrown
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Precio unitario: $${"%.2f".format(detallePedido.precioUnitario)}",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryBrown.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Subtotal
            Text(
                text = "Subtotal: $${"%.2f".format(detallePedido.cantidad * detallePedido.precioUnitario)}",
                style = MaterialTheme.typography.bodySmall,
                color = PrimaryBrown
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Controles de cantidad
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onDecrement(detallePedido) },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = PrimaryBrown)
                ) {
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Disminuir cantidad")
                }

                Text(
                    text = detallePedido.cantidad.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = PrimaryBrown,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                IconButton(
                    onClick = { onIncrement(detallePedido) },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = PrimaryBrown)
                ) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Aumentar cantidad")
                }
            }
        }
    }
}

