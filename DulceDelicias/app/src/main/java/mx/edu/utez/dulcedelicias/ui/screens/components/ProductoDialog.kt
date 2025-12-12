package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import mx.edu.utez.dulcedelicias.data.network.model.Producto

@Composable
fun ProductoDialog(
    onInsert: (String, String, Double, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("") }

    AlertDialog(
        title = {Text("Agregar Producto")},
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {nombre = it},
                    label = { Text("Nombre") }
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {descripcion = it},
                    label = { Text("Descripción") }
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = {precioText = it},
                    label = { Text("Precio") }
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = {stockText = it},
                    label = { Text("Stock") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: 0.0
                    val stock = stockText.toIntOrNull() ?: 0
                    onInsert(nombre, descripcion, precio, stock)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Cancelar")
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

    AlertDialog(
        title = {Text("Actualizar Producto")},
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {nombre = it},
                    label = { Text("Nombre") }
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {descripcion = it},
                    label = { Text("Descripción") }
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = {precioText = it},
                    label = { Text("Precio") }
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = {stockText = it},
                    label = { Text("Stock") }
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
                }
            ) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Cancelar")
            }
        },
        onDismissRequest = onDismiss
    )
}