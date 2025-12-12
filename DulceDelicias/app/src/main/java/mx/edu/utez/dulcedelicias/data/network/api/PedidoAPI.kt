package mx.edu.utez.dulcedelicias.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.utez.dulcedelicias.data.network.VolleySingleton
import mx.edu.utez.dulcedelicias.data.network.model.DetallePedido
import org.json.JSONArray
import org.json.JSONObject

class PedidoAPI(private val context: Context) {
    private val baseURL = "http://192.168.0.9:3000/api"

    fun crearPedido(
        nombreCliente: String,
        ubicacion: String,
        total: Double,
        detalles: List<DetallePedido>,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/pedidos"

        val pedidoJson = JSONObject().apply {
            put("nombre_cliente", nombreCliente)
            put("ubicacion", ubicacion)
            put("total", total)
        }

        val detallesArray = JSONArray().apply {
            detalles.forEach {
                put(JSONObject().apply {
                    put("idProducto", it.idProducto)
                    put("cantidad", it.cantidad)
                })
            }
        }

        val body = JSONObject().apply {
            put("pedido", pedidoJson)
            put("detalles", detallesArray)
        }

        val request = JsonObjectRequest(
            Request.Method.POST, url, body,
            { response ->
                if (response.optBoolean("exito", false)) {
                    onSuccess(response.getInt("idPedido"))
                } else {
                    onError(response.optString("mensaje", "Error al crear pedido"))
                }
            },
            { error -> onError(error.message ?: "Error desconocido") }
        )

        VolleySingleton.getInstance(context).add(request)
    }
}
