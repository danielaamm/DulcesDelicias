package mx.edu.utez.dulcedelicias.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.edu.utez.dulcedelicias.data.local.dao.CarritoDao
import mx.edu.utez.dulcedelicias.data.local.model.CarritoItemEntity

@Database(entities = [CarritoItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carrito_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
