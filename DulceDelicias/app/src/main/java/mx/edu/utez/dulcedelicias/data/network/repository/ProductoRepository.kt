package mx.edu.utez.dulcedelicias.data.network.repository

import android.content.Context
import mx.edu.utez.dulcedelicias.data.network.api.ProductoAPI
import mx.edu.utez.dulcedelicias.data.network.dao.ProductoDao
import mx.edu.utez.dulcedelicias.data.network.model.Producto


class ProductoRepository(context: Context) : ProductoDao {
    private val api = ProductoAPI(context)

    override fun getAll(
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getAll(onSuccess, onError)
    }

    override fun get(
        productoId: Int,
        onSuccess: (Producto) -> Unit,
        onError: (String) -> Unit
    ) {
        api.get(productoId, onSuccess, onError)
    }

    override fun create(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.create(producto, onSuccess, onError)
    }

    override fun update(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.update(producto, onSuccess, onError)
    }

    override fun delete(
        productoId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.delete(productoId, onSuccess, onError)
    }
}