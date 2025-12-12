package mx.edu.utez.dulcedelicias.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito_items")
data class CarritoItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val nombre: String,
    val precioUnitario: Double,
    val cantidad: Int
)
