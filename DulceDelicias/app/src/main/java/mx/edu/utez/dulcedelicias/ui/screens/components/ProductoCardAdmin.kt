package mx.edu.utez.dulcedelicias.ui.screens.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mx.edu.utez.dulcedelicias.data.network.model.Producto
val StrongBrown = Color(0xFF6D4C41)
val CardBackground = Color(0xFFFAF0E6)
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
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
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
                    style = MaterialTheme.typography.titleMedium,
                    color = StrongBrown
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${producto.descripcion?.take(30)}...",
                    style = MaterialTheme.typography.bodySmall,
                    color = StrongBrown.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${producto.precio} | Stock: ${producto.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StrongBrown
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            // Botón de Edición
            IconButton(onClick = { onEdit(producto) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar Producto",
                    tint = StrongBrown
                )
            }

            // Botón de Eliminación
            IconButton(onClick = { onDelete(producto) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar Producto",
                    tint = StrongBrown
                )
            }
        }
    }
}