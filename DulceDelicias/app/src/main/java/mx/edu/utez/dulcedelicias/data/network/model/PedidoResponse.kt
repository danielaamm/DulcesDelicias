package mx.edu.utez.dulcedelicias.data.network.model

data class PedidoResponse(
    val exito: Boolean,
    val mensaje: String,
    val idPedido: Int?
)
