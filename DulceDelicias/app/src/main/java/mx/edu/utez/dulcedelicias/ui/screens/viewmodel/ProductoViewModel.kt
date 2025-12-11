package mx.edu.utez.dulcedelicias.ui.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import mx.edu.utez.dulcedelicias.data.network.model.Producto
import mx.edu.utez.dulcedelicias.data.network.repository.ProductoRepository

class ProductoViewModel(application: Application):
    AndroidViewModel(application) {
    val repository = ProductoRepository(application.applicationContext)
    val products = MutableStateFlow<List<Producto>>(emptyList())
    val errorMessage = MutableStateFlow("")

    init {
        getProducts()
    }

    fun getProducts(){
        repository.getAll(
            onSuccess = {lista ->
                products.value = lista
                errorMessage.value = ""
            },
            onError = { error ->
                errorMessage.value = "Error al obtener productos: $error"
            }
        )
    }

    fun insertProducto(nombre: String, descripcion: String, precio: Double, stock: Int){
        val nuevoProducto = Producto(
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            stock = stock,
            disponible = true,
            imagenUrl = null
        )
        repository.create(
            producto = nuevoProducto,
            onSuccess = {
                getProducts()
            },
            onError = { error ->
                errorMessage.value = "Error al insertar producto: $error"
            }
        )
    }

    fun updateProducto(producto: Producto){
        repository.update(
            producto = producto,
            onSuccess = {
                getProducts()
            },
            onError = { error ->
                errorMessage.value = "Error al actualizar producto: $error"
            }
        )
    }

    fun deleteProducto(productoId: Int){
        repository.delete(
            productoId = productoId,
            onSuccess = {
                getProducts()
            },
            onError = { error ->
                errorMessage.value = "Error al eliminar producto: $error"
            }
        )
    }
}