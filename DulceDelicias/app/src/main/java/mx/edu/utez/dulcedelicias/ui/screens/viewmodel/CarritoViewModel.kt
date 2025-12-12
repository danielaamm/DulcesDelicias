package mx.edu.utez.dulcedelicias.ui.screens.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import mx.edu.utez.dulcedelicias.data.network.api.PedidoAPI
import mx.edu.utez.dulcedelicias.data.network.model.DetallePedido
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.data.network.repository.PedidoRepository

class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoRepository = PedidoRepository(PedidoAPI(application))

    private var _carrito = mutableStateListOf<Pair<Producto, DetallePedido>>()
    val carrito: List<Pair<Producto, DetallePedido>> get() = _carrito

    fun agregarProducto(producto: Producto) {
        val index = _carrito.indexOfFirst { it.first.id == producto.id }
        if (index >= 0) {
            val actual = _carrito[index].second
            val actualizado = actual.copy(cantidad = actual.cantidad + 1)
            _carrito[index] = producto to actualizado
        } else {
            val nuevoDetalle = DetallePedido(
                idDetalle = 0,
                idPedido = 0,
                idProducto = producto.id,
                cantidad = 1,
                precioUnitario = producto.precio
            )
            _carrito.add(producto to nuevoDetalle)
        }
    }

    fun incrementar(detalle: DetallePedido) {
        val index = _carrito.indexOfFirst { it.second.idProducto == detalle.idProducto }
        if (index >= 0) {
            val (producto, actual) = _carrito[index]
            _carrito[index] = producto to actual.copy(cantidad = actual.cantidad + 1)
        }
    }

    fun decrementar(detalle: DetallePedido) {
        val index = _carrito.indexOfFirst { it.second.idProducto == detalle.idProducto }
        if (index >= 0) {
            val (producto, actual) = _carrito[index]
            if (actual.cantidad > 1) {
                _carrito[index] = producto to actual.copy(cantidad = actual.cantidad - 1)
            } else {
                _carrito.removeAt(index)
            }
        }
    }

    fun confirmarPedido(
        nombreCliente: String,
        ubicacion: String,
        envio: Double = 3.50,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        val subtotal = _carrito.sumOf { it.second.precioUnitario * it.second.cantidad }
        val total = subtotal + envio
        val detalles = _carrito.map { it.second }

        pedidoRepository.crearPedido(nombreCliente, ubicacion, total, detalles, onSuccess, onError)
    }
}
