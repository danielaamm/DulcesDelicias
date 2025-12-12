package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.ui.theme.PrimaryBrown
import mx.edu.utez.dulcedelicias.ui.theme.BackgroundCream

// 🎨 Color café más bajito
val LightBrown = Color(0xFFD7CCC8)

@Composable
fun ProductoCardHome(
    producto: Producto,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundCream),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre del producto
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryBrown
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Precio
            Text(
                text = "Precio: $${"%.2f".format(producto.precio)}",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryBrown.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de agregar al carrito
            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBrown,
                    contentColor = LightBrown // ✅ texto café bajito
                )
            ) {
                Text(
                    "Agregar al carrito",
                    fontWeight = FontWeight.ExtraBold // ✅ grosor de la letra
                )
            }
        }
    }
}
