package mx.edu.utez.dulcedelicias.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.utez.dulcedelicias.data.network.VolleySingleton
import mx.edu.utez.dulcedelicias.data.network.model.LoginResponse
import org.json.JSONObject

class AutenticacionAPI(private val context: Context) {
    val baseURL = "http://192.168.109.38:3000/api"
    //Autenticacion
    fun iniciarSesion(
        nombreUsuario: String,
        clave: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/iniciar_sesion"
        val metodo = Request.Method.POST

        val body = JSONObject().apply {
            put("nombreUsuario", nombreUsuario)
            put("clave", clave)
        }

        val listener = Response.Listener<JSONObject>{ response ->
            val loginResponse = LoginResponse(
                exito = response.getBoolean("exito"),
                mensaje = response.getString("mensaje"),
                idUsuario = response.optInt("idUsuario").let { if (it == 0) null else it }
            )
            onSuccess(loginResponse)
        }

        val errorListener = Response.ErrorListener{ error ->
            onError(error.message ?: "Error de conexión o credenciales incorrectas.")
        }

        val request = JsonObjectRequest(metodo, url, body, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
}