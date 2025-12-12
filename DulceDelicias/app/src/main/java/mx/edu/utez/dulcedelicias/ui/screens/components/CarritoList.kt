package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mx.edu.utez.dulcedelicias.data.network.model.DetallePedido
import mx.edu.utez.dulcedelicias.data.network.model.Producto

@Composable
fun CarritoList(
    detallePedidos: List<Pair<Producto, DetallePedido>>,
    onIncrement: (DetallePedido) -> Unit,
    onDecrement: (DetallePedido) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(detallePedidos) { (producto, detalle) ->
            CarritoCard(
                producto = producto,
                detallePedido = detalle,
                onIncrement = { onIncrement(detalle) },
                onDecrement = { onDecrement(detalle) }
            )
        }
    }
}
