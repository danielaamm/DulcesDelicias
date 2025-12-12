package mx.edu.utez.dulcedelicias.ui.screens.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.ProductoViewModel
private object ProductoScreenAdminColors {
    val DulceDeliciasPrimary = Color(0xFF6D4C41)
    val DulceDeliciasOnPrimary = Color.White
    val DulceDeliciasBackground = Color(0xFFFCF5E3)
    val DulceDeliciasDanger = Color(0xFF6D4C41)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreenAdmin(
    navController: NavController,
    viewModel: ProductoViewModel
) {
    val productos by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var addDialog by remember { mutableStateOf(false) }
    var updateDialog by remember { mutableStateOf(false) }
    var deleteConfirm by remember { mutableStateOf(false) }
    var selectedProducto by remember { mutableStateOf<Producto?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        containerColor = ProductoScreenAdminColors.DulceDeliciasBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Panel Admin",
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ProductoScreenAdminColors.DulceDeliciasPrimary,
                    titleContentColor = ProductoScreenAdminColors.DulceDeliciasOnPrimary,
                ),
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { addDialog = true },
                containerColor = ProductoScreenAdminColors.DulceDeliciasPrimary,
                contentColor = ProductoScreenAdminColors.DulceDeliciasOnPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Producto"
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ProductoListAdmin(
                productos = productos,
                onEdit = { producto ->
                    selectedProducto = producto
                    updateDialog = true
                },
                onDelete = { producto ->
                    selectedProducto = producto
                    deleteConfirm = true
                }
            )

            // ✅ Modal de cerrar sesión
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Cerrar sesión") },
                    text = { Text("¿Seguro que quieres cerrar sesión?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }) {
                            Text("Sí", color = ProductoScreenAdminColors.DulceDeliciasPrimary)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text("Cancelar", color = ProductoScreenAdminColors.DulceDeliciasDanger)
                        }
                    }
                )
            }

            // ✅ Diálogos de producto
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

            // ✅ Manejo de errores
            if (errorMessage.isNotEmpty()) {
                Snackbar(
                    containerColor = ProductoScreenAdminColors.DulceDeliciasDanger,
                    contentColor = ProductoScreenAdminColors.DulceDeliciasOnPrimary,
                    action = {
                        Button(
                            onClick = { viewModel.errorMessage.value = "" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ProductoScreenAdminColors.DulceDeliciasOnPrimary,
                                contentColor = ProductoScreenAdminColors.DulceDeliciasDanger
                            )
                        ) {
                            Text("Cerrar")
                        }
                    }
                ) {
                    Text(errorMessage)
                }
            }
        }
    }
}
