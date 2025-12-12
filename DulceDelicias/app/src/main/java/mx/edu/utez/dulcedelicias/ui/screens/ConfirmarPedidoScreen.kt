package mx.edu.utez.dulcedelicias.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import mx.edu.utez.dulcedelicias.data.network.model.DetalleData
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.CarritoViewModel
import mx.edu.utez.dulcedelicias.ui.theme.BackgroundCream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmarPedidoScreen(
    navController: NavController,
    viewModel: CarritoViewModel,
    detalles: List<DetalleData>
) {
    val context = LocalContext.current
    var nombreCliente by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    val envio = 3.50
    val subtotal = viewModel.carrito.sumOf { it.precioUnitario * it.cantidad }
    val total = subtotal + envio

    // Definición de colores (puedes mover estos a tu Color.kt)
    val PrimaryPink = Color(0xFF6D4C41)
    val SoftBrown = Color(0xFF795548)

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            detectarUbicacion(context) { lat, lng -> ubicacion = "Lat: $lat, Lng: $lng" }
        } else {
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Finalizar Pedido", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BackgroundCream
                )
            )
        },
        containerColor = BackgroundCream
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Información de Entrega",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryPink,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = nombreCliente,
                        onValueChange = { nombreCliente = it },
                        label = { Text("Nombre Completo") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryPink) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = ubicacion,
                        onValueChange = { ubicacion = it },
                        label = { Text("Ubicación / Dirección") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryPink) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            } else {
                                detectarUbicacion(context) { lat, lng ->
                                    ubicacion = "Lat: $lat, Lng: $lng"
                                    Toast.makeText(context, "Ubicación detectada", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SoftBrown),
                        modifier = Modifier.padding(top = 8.dp).align(Alignment.End),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Auto-detectar", fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- SECCIÓN RESUMEN DE PAGO ---
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Resumen del Pedido",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryPink,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal", color = Color.Gray)
                        Text("$${"%.2f".format(subtotal)}", fontWeight = FontWeight.Medium)
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Costo de Envío", color = Color.Gray)
                        Text("$${"%.2f".format(envio)}", fontWeight = FontWeight.Medium)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total a Pagar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("$${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryPink)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- BOTÓN FINAL ---
            Button(
                onClick = {
                    viewModel.confirmarPedido(
                        nombreCliente,
                        ubicacion,
                        envio,
                        onSuccess = { idPedido ->
                            Toast.makeText(context, "¡Pedido #$idPedido realizado!", Toast.LENGTH_LONG).show()
                            navController.navigate("home")
                        },
                        onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                enabled = nombreCliente.isNotBlank() && ubicacion.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPink,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("CONFIRMAR COMPRA", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

private fun detectarUbicacion(
    context: Context,
    onLocation: (lat: Double, lng: Double) -> Unit
) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                onLocation(location.latitude, location.longitude)
                locationManager.removeUpdates(this)
            }
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, listener)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, listener)

            val lastLocation: Location? =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            lastLocation?.let { onLocation(it.latitude, it.longitude) }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}