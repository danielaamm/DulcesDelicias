package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.dulcedelicias.data.network.model.Producto

@Composable
fun ProductoCardAdmin(
    producto: Producto,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${producto.descripcion?.take(30)}...",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${producto.precio} | Stock: ${producto.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            // Botón de Edición
            IconButton(onClick = { onEdit(producto) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar Producto",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            // Botón de Eliminación
            IconButton(onClick = { onDelete(producto) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar Producto",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}