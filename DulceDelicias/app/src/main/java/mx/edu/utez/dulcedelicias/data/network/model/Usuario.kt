package mx.edu.utez.dulcedelicias.data.network.model

data class Usuario(
    val id: Int = 0,
    val nombre: String?,
    val correo: String,
    val usuario: String,
    val contrasena: String
)