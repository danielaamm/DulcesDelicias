package mx.edu.utez.dulcedelicias.data.network.model

data class PedidoData(
    val nombre_cliente: String,
    val ubicacion: String,
    val total: Double
)

data class DetalleData(
    val idProducto: Int,
    val cantidad: Int
)

data class PedidoRequest(
    val pedido: PedidoData,
    val detalles: List<DetalleData>
)
