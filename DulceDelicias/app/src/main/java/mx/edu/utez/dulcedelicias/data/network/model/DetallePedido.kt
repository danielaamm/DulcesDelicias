package mx.edu.utez.dulcedelicias.data.network.model

data class DetallePedido(
    val idDetalle: Int = 0,
    val idPedido: Int,
    val idProducto: Int,
    val cantidad: Int,
    val precioUnitario: Double
)