package mx.edu.utez.dulcedelicias.data.network.dao

import mx.edu.utez.dulcedelicias.data.network.model.PedidoRequest
import mx.edu.utez.dulcedelicias.data.network.model.PedidoResponse

interface PedidoDao {
    fun create(
        pedido: PedidoRequest,
        onSuccess: (PedidoResponse) -> Unit,
        onError: (String) -> Unit
    )
}
