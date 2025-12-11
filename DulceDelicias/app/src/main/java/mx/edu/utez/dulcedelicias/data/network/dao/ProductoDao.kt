package mx.edu.utez.dulcedelicias.data.network.dao
import mx.edu.utez.dulcedelicias.data.network.model.Producto

interface ProductoDao {
    fun getAll(
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    )
    fun get(
        productoId: Int,
        onSuccess: (Producto) -> Unit,
        onError: (String) -> Unit
    )
    fun create(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun update(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun delete(
        productoId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}