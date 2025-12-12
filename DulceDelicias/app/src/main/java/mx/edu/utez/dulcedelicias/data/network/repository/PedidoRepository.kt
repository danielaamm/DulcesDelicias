package mx.edu.utez.dulcedelicias.data.network.repository

import android.content.Context
import mx.edu.utez.dulcedelicias.data.network.api.PedidoAPI
import mx.edu.utez.dulcedelicias.data.network.model.DetallePedido

class PedidoRepository(private val api: PedidoAPI) {
    fun crearPedido(
        nombreCliente: String,
        ubicacion: String,
        total: Double,
        detalles: List<DetallePedido>,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        api.crearPedido(nombreCliente, ubicacion, total, detalles, onSuccess, onError)
    }
}


