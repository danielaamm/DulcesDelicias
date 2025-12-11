package mx.edu.utez.dulcedelicias.data.network.dao
import android.content.Context
import mx.edu.utez.dulcedelicias.data.network.api.AutenticacionAPI
import mx.edu.utez.dulcedelicias.data.network.model.LoginResponse

interface UsuarioDao {
    fun iniciarSesion(
        nombreUsuario: String,
        clave: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    )
}

class UsuarioDaoImpl(private val context: Context) : UsuarioDao {
    private val api = AutenticacionAPI(context)

    override fun iniciarSesion(
        nombreUsuario: String,
        clave: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        api.iniciarSesion(nombreUsuario, clave, onSuccess, onError)
    }
}