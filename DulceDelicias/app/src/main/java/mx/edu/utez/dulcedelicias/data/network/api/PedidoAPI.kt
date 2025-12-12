import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.utez.dulcedelicias.data.network.VolleySingleton
import mx.edu.utez.dulcedelicias.data.network.model.PedidoRequest
import mx.edu.utez.dulcedelicias.data.network.model.PedidoResponse
import org.json.JSONArray
import org.json.JSONObject

class PedidoAPI(private val context: Context) {
    private val baseURL = "http://192.168.110.84:3000/api"

    fun create(
        pedido: PedidoRequest,
        onSuccess: (PedidoResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/pedidos"

        val body = JSONObject().apply {
            val pedidoJson = JSONObject().apply {
                put("nombre_cliente", pedido.pedido.nombre_cliente)
                put("ubicacion", pedido.pedido.ubicacion)
                put("total", pedido.pedido.total)
            }
            val detallesJson = JSONArray().apply {
                pedido.detalles.forEach {
                    val detalleObj = JSONObject().apply {
                        put("idProducto", it.idProducto)
                        put("cantidad", it.cantidad)
                    }
                    put(detalleObj)
                }
            }
            put("pedido", pedidoJson)
            put("detalles", detallesJson)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            body,
            { response ->
                val exito = response.optBoolean("exito", false)
                val mensaje = response.optString("mensaje", "")
                val idPedido = response.optInt("idPedido", -1)
                onSuccess(PedidoResponse(exito, mensaje, idPedido))
            },
            { error ->
                onError(error.message ?: "Error desconocido")
            }
        )

        VolleySingleton.getInstance(context).add(request)
    }
}
