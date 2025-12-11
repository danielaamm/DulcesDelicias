package mx.edu.utez.dulcedelicias.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.ui.screens.components.ProductoDialog
import mx.edu.utez.dulcedelicias.ui.screens.components.ProductoListAdmin
import mx.edu.utez.dulcedelicias.ui.screens.components.ProductoUpdateDialog
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.ProductoViewModel

val PrimaryBrown = Color(0xFF6D4C41)
val BackgroundCream = Color(0xFFFFF8E1)


@Composable
fun ProductoScreenAdmin(
    navController: NavController,
    viewModel: ProductoViewModel) {

    val productos by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var addDialog by remember { mutableStateOf(false) }
    var updateDialog by remember { mutableStateOf(false) }
    var deleteConfirm by remember { mutableStateOf(false) }

    var selectedProducto by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addDialog = true },
                containerColor = PrimaryBrown,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Producto"
                )
            }
        },
        containerColor = BackgroundCream
    ) { innerPadding ->

        ProductoListAdmin(
            productos = productos,
            onEdit = { producto ->
                selectedProducto = producto
                updateDialog = true
            },
            onDelete = { producto ->
                selectedProducto = producto
                deleteConfirm = true
            },
        )

        if (addDialog) {
            ProductoDialog(
                onInsert = { nombre, descripcion, precio, stock ->
                    viewModel.insertProducto(nombre, descripcion, precio, stock)
                    addDialog = false
                },
                onDismiss = { addDialog = false }
            )
        }

        selectedProducto?.let { producto ->
            if (updateDialog) {
                ProductoUpdateDialog(
                    producto = producto,
                    onUpdate = { updatedProducto ->
                        viewModel.updateProducto(updatedProducto)
                        updateDialog = false
                        selectedProducto = null
                    },
                    onDismiss = {
                        updateDialog = false
                        selectedProducto = null
                    }
                )
            }

            if (deleteConfirm) {
                DeleteConfirmDialog(
                    producto = producto,
                    onConfirm = { id ->
                        viewModel.deleteProducto(id)
                        deleteConfirm = false
                        selectedProducto = null
                    },
                    onDismiss = {
                        deleteConfirm = false
                        selectedProducto = null
                    }
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Snackbar(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                containerColor = PrimaryBrown.copy(alpha = 0.9f),
                contentColor = Color.White, // Texto blanco
                action = {
                    Button(
                        onClick = { viewModel.errorMessage.value = "" },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Cerrar", color = PrimaryBrown)
                    }
                }
            ) {
                Text(errorMessage)
            }
        }
    }
}

@Composable
fun DeleteConfirmDialog(
    producto: Producto,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar Eliminación", color = PrimaryBrown) },
        text = { Text("¿Estás seguro de eliminar ${producto.nombre}?") },
        confirmButton = {
            Button(
                onClick = { onConfirm(producto.id) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) { Text("Sí, Eliminar") }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(PrimaryBrown)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryBrown
                )
            ) { Text("Cancelar") }
        }
    )
}