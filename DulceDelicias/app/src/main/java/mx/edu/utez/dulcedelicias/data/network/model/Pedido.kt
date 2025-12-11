package mx.edu.utez.dulcedelicias.data.network.model
import java.util.Date

data class Pedido(
    val id: Int = 0,

    // Datos del Cliente
    val nombreCliente: String,
    val ubicacion: String,

    // Datos del Pedido
    val total: Double,
    val estado: String = "Pendiente",
    val fechaPedido: Date?,
    val fechaEntrega: Date?
)