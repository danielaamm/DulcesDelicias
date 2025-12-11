package mx.edu.utez.dulcedelicias.data.network.model

data class Producto(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    val disponible: Boolean,
    val imagenUrl: String?
)