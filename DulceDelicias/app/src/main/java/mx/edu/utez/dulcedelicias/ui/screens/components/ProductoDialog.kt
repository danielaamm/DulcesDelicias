package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import mx.edu.utez.dulcedelicias.data.network.model.Producto
private object ProductoDialogColors {
    val DulceDeliciasPrimary = Color(0xFF6D4C41)
    val DulceDeliciasOnPrimary = Color.White
    val DulceDeliciasSecondary = Color(0xFF1F1B16)
    val DulceDeliciasOnSecondary = Color(0xFF1F1B16)
    val DulceDeliciasSurface = Color(0xFFFFFDE7)
    val DulceDeliciasOutline = Color(0xFFC6A49A)
    val DulceDeliciasDanger = Color(0xFFB71C1C)
    val DulceDeliciasOnDanger = Color.White
}

@Composable
fun ProductoDialog(
    onInsert: (String, String, Double, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ProductoDialogColors.DulceDeliciasPrimary,
        unfocusedBorderColor = ProductoDialogColors.DulceDeliciasOutline,
        focusedLabelColor = ProductoDialogColors.DulceDeliciasPrimary,
        cursorColor = ProductoDialogColors.DulceDeliciasPrimary
    )

    AlertDialog(
        containerColor = ProductoDialogColors.DulceDeliciasSurface,
        titleContentColor = ProductoDialogColors.DulceDeliciasOnSecondary,
        title = { Text("Agregar Producto", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = { precioText = it },
                    label = { Text("Precio") },
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = { stockText = it },
                    label = { Text("Stock") },
                    colors = textFieldColors
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: 0.0
                    val stock = stockText.toIntOrNull() ?: 0
                    onInsert(nombre, descripcion, precio, stock)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ProductoDialogColors.DulceDeliciasPrimary,
                    contentColor = ProductoDialogColors.DulceDeliciasOnPrimary
                )
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    "Cancelar",
                    color = ProductoDialogColors.DulceDeliciasSecondary
                )
            }
        },
        onDismissRequest = onDismiss
    )
}


@Composable
fun ProductoUpdateDialog(
    producto: Producto,
    onUpdate: (Producto) -> Unit,
    onDismiss: () -> Unit
) {
    var nombre by remember { mutableStateOf(producto.nombre) }
    var descripcion by remember { mutableStateOf(producto.descripcion ?: "") }
    var precioText by remember { mutableStateOf(producto.precio.toString()) }
    var stockText by remember { mutableStateOf(producto.stock.toString()) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ProductoDialogColors.DulceDeliciasPrimary,
        unfocusedBorderColor = ProductoDialogColors.DulceDeliciasOutline,
        focusedLabelColor = ProductoDialogColors.DulceDeliciasPrimary,
        cursorColor = ProductoDialogColors.DulceDeliciasPrimary
    )

    AlertDialog(
        containerColor = ProductoDialogColors.DulceDeliciasSurface,
        titleContentColor = ProductoDialogColors.DulceDeliciasOnSecondary,
        title = { Text("Actualizar Producto", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = { precioText = it },
                    label = { Text("Precio") },
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = { stockText = it },
                    label = { Text("Stock") },
                    colors = textFieldColors
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: producto.precio
                    val stock = stockText.toIntOrNull() ?: producto.stock

                    val updatedProducto = producto.copy(
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio,
                        stock = stock
                    )
                    onUpdate(updatedProducto)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ProductoDialogColors.DulceDeliciasPrimary,
                    contentColor = ProductoDialogColors.DulceDeliciasOnPrimary
                )
            ) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    "Cancelar",
                    color = ProductoDialogColors.DulceDeliciasSecondary
                )
            }
        },
        onDismissRequest = onDismiss
    )
}
@Composable
fun DeleteConfirmDialog(
    producto: Producto,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ProductoDialogColors.DulceDeliciasSurface,
        titleContentColor = ProductoDialogColors.DulceDeliciasOnSecondary,
        textContentColor = ProductoDialogColors.DulceDeliciasOnSecondary,
        title = {
            Text(
                "Confirmar Eliminación",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text("¿Estás seguro de eliminar ${producto.nombre}?")
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(producto.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ProductoDialogColors.DulceDeliciasDanger,
                    contentColor = ProductoDialogColors.DulceDeliciasOnDanger
                )
            ) {
                Text("Sí, Eliminar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    "Cancelar",
                    color = ProductoDialogColors.DulceDeliciasSecondary
                )
            }
        }
    )
}