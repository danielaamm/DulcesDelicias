package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mx.edu.utez.dulcedelicias.data.network.model.Producto

@Composable
fun ProductoListAdmin(
    productos: List<Producto>,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(productos, key = { it.id }){ producto ->
            ProductoCardAdmin(
                producto,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}