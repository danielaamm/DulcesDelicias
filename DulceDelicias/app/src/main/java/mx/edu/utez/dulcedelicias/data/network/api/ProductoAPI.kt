package mx.edu.utez.dulcedelicias.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.utez.dulcedelicias.data.network.VolleySingleton
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import org.json.JSONObject

class ProductoAPI(private val context: Context) {
    val baseURL = "http://192.168.0.9:3000/api"

    fun getAll(
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/productos"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val productosJson = response.getJSONArray("productos")
                    val lista = mutableListOf<Producto>()

                    for (i in 0 until productosJson.length()) {
                        val obj = productosJson.getJSONObject(i)

                        lista.add(
                            Producto(
                                id = obj.getInt("id"),
                                nombre = obj.getString("nombre"),
                                descripcion = obj.optString("descripcion", null),
                                precio = obj.getDouble("precio"),
                                stock = obj.getInt("stock"),
                                disponible = obj.optInt("disponible", 0) == 1,
                                imagenUrl = obj.optString("imagenUrl", null)
                            )
                        )
                    }

                    onSuccess(lista)
                } catch (e: Exception) {
                    e.printStackTrace()
                    onError("Error convirtiendo JSON: ${e.message}")
                }
            },
            { error ->
                onError(error.message ?: "Error desconocido")
            }
        )

        VolleySingleton.getInstance(context).add(request)
    }

    fun get(
        productoId: Int,
        onSuccess: (Producto) -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/productos/$productoId"
        val metodo = Request.Method.GET
        val listener = Response.Listener<JSONObject>{ objeto ->
            val producto = Producto(
                objeto.getInt("id"),
                objeto.getString("nombre"),
                objeto.getString("descripcion"),
                objeto.getDouble("precio"),
                objeto.getInt("stock"),
                objeto.getBoolean("disponible"),
                objeto.getString("imagenUrl")
            )
            onSuccess(producto)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(metodo, url, null,
            listener, errorListener
        )
        VolleySingleton.getInstance(context).add(request)
    }

    fun create(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/productos"
        val body = JSONObject().apply {
            put("nombre", producto.nombre)
            put("descripcion", producto.descripcion)
            put("precio", producto.precio)
            put("stock", producto.stock)
            put("disponible", producto.disponible)
            put("imagen_url", producto.imagenUrl)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            body,
            { response ->
                if (response.optBoolean("exito")) {
                    onSuccess()
                } else {
                    onError(response.optString("mensaje", "Error desconocido"))
                }
            },
            { error ->
                onError(error.message ?: "Error desconocido")
            }
        )
        VolleySingleton.getInstance(context).add(request)
    }



    fun update(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/productos"
        val metodo = Request.Method.PUT
        val body = JSONObject()
        body.put("id", producto.id)
        body.put("nombre", producto.nombre)
        body.put("descripcion", producto.descripcion)
        body.put("precio", producto.precio)
        body.put("stock", producto.stock)
        body.put("disponible", producto.disponible)
        body.put("imagenUrl", producto.imagenUrl)
        val listener = Response.Listener<JSONObject>{ response ->
            if(response.getInt("affectedRows") == 1){
                onSuccess()
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(metodo, url, body,
            listener, errorListener
        )
        VolleySingleton.getInstance(context).add(request)
    }

    fun delete(
        productoId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/productos/$productoId"
        val metodo = Request.Method.DELETE
        val listener = Response.Listener<JSONObject>{ response ->
            if(response.getInt("affectedRows") == 1){
                onSuccess()
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(metodo, url, null,
            listener, errorListener
        )
        VolleySingleton.getInstance(context).add(request)
    }
}