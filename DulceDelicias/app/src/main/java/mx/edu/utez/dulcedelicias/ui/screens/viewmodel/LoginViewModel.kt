package mx.edu.utez.dulcedelicias.ui.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.edu.utez.dulcedelicias.data.network.repository.UsuarioRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UsuarioRepository(application.applicationContext)
    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _loginExitoso = MutableLiveData<Boolean>()
    val loginExitoso: LiveData<Boolean> = _loginExitoso


    fun autenticar(usuario: String, contrasena: String) {
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            _mensaje.value = "Llena todos los campos."
            return
        }

        repository.iniciarSesion(
            nombreUsuario = usuario,
            clave = contrasena,
            onSuccess = { response ->
                if (response.exito) {
                    _loginExitoso.value = true
                } else {
                    _mensaje.value = response.mensaje
                    _loginExitoso.value = false
                }
            },
            onError = { error ->
                _mensaje.value = "Error de conexión: $error"
                _loginExitoso.value = false
            }
        )
    }
    fun resetLoginStatus() {
        _loginExitoso.value = false
    }
}