package mx.edu.utez.dulcedelicias.ui.screens.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.dulcedelicias.data.local.db.AppDatabase
import mx.edu.utez.dulcedelicias.data.local.model.CarritoItemEntity
import mx.edu.utez.dulcedelicias.data.network.model.DetalleData
import mx.edu.utez.dulcedelicias.data.network.model.PedidoData
import mx.edu.utez.dulcedelicias.data.network.model.PedidoRequest
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.data.network.repository.PedidoRepository

class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    private val carritoDao = AppDatabase.getDatabase(application).carritoDao()
    private val pedidoRepository = PedidoRepository(application.applicationContext)

    private var _carrito = mutableStateListOf<CarritoItemEntity>()
    val carrito: List<CarritoItemEntity> get() = _carrito

    init {
        viewModelScope.launch {
            _carrito.clear()
            _carrito.addAll(carritoDao.getCarrito())
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            val existente = _carrito.find { it.productoId == producto.id }
            if (existente != null) {
                val actualizado = existente.copy(cantidad = existente.cantidad + 1)
                carritoDao.actualizarProducto(actualizado)
            } else {
                val nuevo = CarritoItemEntity(
                    productoId = producto.id,
                    nombre = producto.nombre,
                    precioUnitario = producto.precio,
                    cantidad = 1
                )
                carritoDao.agregarProducto(nuevo)
            }
            _carrito.clear()
            _carrito.addAll(carritoDao.getCarrito())
        }
    }

    fun incrementar(item: CarritoItemEntity) {
        viewModelScope.launch {
            val actualizado = item.copy(cantidad = item.cantidad + 1)
            carritoDao.actualizarProducto(actualizado)
            _carrito.clear()
            _carrito.addAll(carritoDao.getCarrito())
        }
    }

    fun decrementar(item: CarritoItemEntity) {
        viewModelScope.launch {
            if (item.cantidad > 1) {
                val actualizado = item.copy(cantidad = item.cantidad - 1)
                carritoDao.actualizarProducto(actualizado)
            } else {
                carritoDao.eliminarProducto(item)
            }
            _carrito.clear()
            _carrito.addAll(carritoDao.getCarrito())
        }
    }

    fun limpiarCarrito() {
        viewModelScope.launch {
            carritoDao.limpiarCarrito()
            _carrito.clear()
        }
    }

    fun confirmarPedido(
        nombreCliente: String,
        ubicacion: String,
        envio: Double = 3.50,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        val subtotal = _carrito.sumOf { it.precioUnitario * it.cantidad }
        val total = subtotal + envio

        val detalles = _carrito.map {
            DetalleData(idProducto = it.productoId, cantidad = it.cantidad)
        }

        val request = PedidoRequest(
            pedido = PedidoData(
                nombre_cliente = nombreCliente,
                ubicacion = ubicacion,
                total = total
            ),
            detalles = detalles
        )

        pedidoRepository.create(
            pedido = request,
            onSuccess = { response ->
                limpiarCarrito()
                onSuccess(response.idPedido ?: -1)
            },
            onError = { error ->
                onError(error)
            }
        )
    }
}

