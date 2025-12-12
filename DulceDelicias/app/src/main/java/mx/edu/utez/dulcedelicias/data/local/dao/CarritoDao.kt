package mx.edu.utez.dulcedelicias.data.local.dao

import androidx.room.*
import mx.edu.utez.dulcedelicias.data.local.model.CarritoItemEntity

@Dao
interface CarritoDao {
    @Query("SELECT * FROM carrito_items")
    suspend fun getCarrito(): List<CarritoItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarProducto(item: CarritoItemEntity)

    @Update
    suspend fun actualizarProducto(item: CarritoItemEntity)

    @Delete
    suspend fun eliminarProducto(item: CarritoItemEntity)

    @Query("DELETE FROM carrito_items")
    suspend fun limpiarCarrito()
}
