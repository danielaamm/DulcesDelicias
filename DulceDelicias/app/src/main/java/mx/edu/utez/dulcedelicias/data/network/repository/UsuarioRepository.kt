package mx.edu.utez.dulcedelicias.data.network.repository

import android.content.Context
import mx.edu.utez.dulcedelicias.data.network.dao.UsuarioDaoImpl
import mx.edu.utez.dulcedelicias.data.network.model.LoginResponse

class UsuarioRepository(context: Context) {
    private val usuarioDao = UsuarioDaoImpl(context)
    fun iniciarSesion(
        nombreUsuario: String,
        clave: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        usuarioDao.iniciarSesion(nombreUsuario, clave, onSuccess, onError)
    }
}