package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mx.edu.utez.dulcedelicias.data.network.model.Producto


val PrimaryBrown = Color(0xFF6D4C41)
val BackgroundCream = Color(0xFFFFF8E1)

@Composable
fun ProductoDialog(
    onInsert: (String, String, Double, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("") }

    // Colores de TextField para que coincidan con la temática
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PrimaryBrown,
        unfocusedBorderColor = PrimaryBrown.copy(alpha = 0.5f),
        cursorColor = PrimaryBrown,
        focusedLabelColor = PrimaryBrown
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        // Aplicamos el color de fondo claro (BackgroundCream) al Dialog
        containerColor = BackgroundCream,
        title = { Text("Agregar Producto", color = PrimaryBrown) },
        text = {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {nombre = it},
                    label = { Text("Nombre") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {descripcion = it},
                    label = { Text("Descripción") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = {
                        // Permite solo números (o un punto decimal)
                        precioText = it.filter { char -> char.isDigit() || char == '.' }
                    },
                    label = { Text("Precio") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = {
                        // Permite solo dígitos para el stock
                        stockText = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("Stock") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: 0.0
                    val stock = stockText.toIntOrNull() ?: 0
                    if (nombre.isNotBlank() && precio > 0 && stock >= 0) {
                        onInsert(nombre, descripcion, precio, stock)
                    } else {
                        // Opcional: Mostrar un error al usuario si la validación falla
                    }
                },
                // Botón principal café
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBrown)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            // Botón secundario con borde café
            OutlinedButton(
                onClick = onDismiss,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(PrimaryBrown)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryBrown
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}


@Composable
fun ProductoUpdateDialog(
    producto: Producto,
    onUpdate: (Producto) -> Unit,
    onDismiss: () -> Unit
) {
    // Inicialización de estados con valores del producto existente
    var nombre by remember { mutableStateOf(producto.nombre) }
    var descripcion by remember { mutableStateOf(producto.descripcion ?: "") }
    var precioText by remember { mutableStateOf(producto.precio.toString()) }
    var stockText by remember { mutableStateOf(producto.stock.toString()) }

    // Colores de TextField para que coincidan con la temática
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PrimaryBrown,
        unfocusedBorderColor = PrimaryBrown.copy(alpha = 0.5f),
        cursorColor = PrimaryBrown,
        focusedLabelColor = PrimaryBrown
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        // Aplicamos el color de fondo claro (BackgroundCream) al Dialog
        containerColor = BackgroundCream,
        title = { Text("Actualizar Producto", color = PrimaryBrown) },
        text = {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {nombre = it},
                    label = { Text("Nombre") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {descripcion = it},
                    label = { Text("Descripción") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = {
                        precioText = it.filter { char -> char.isDigit() || char == '.' }
                    },
                    label = { Text("Precio") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = {
                        stockText = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("Stock") },
                    colors = textFieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: producto.precio
                    val stock = stockText.toIntOrNull() ?: producto.stock

                    if (nombre.isNotBlank()) {
                        val updatedProducto = producto.copy(
                            nombre = nombre,
                            descripcion = descripcion.ifBlank { null }, // Si es vacío, enviamos nulo
                            precio = precio,
                            stock = stock
                            // La imagenUrl se mantiene si no se maneja aquí
                        )
                        onUpdate(updatedProducto)
                    } else {
                        // Opcional: Mostrar un error al usuario si la validación falla
                    }
                },
                // Botón principal café
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBrown)
            ) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            // Botón secundario con borde café
            OutlinedButton(
                onClick = onDismiss,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(PrimaryBrown)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryBrown
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}