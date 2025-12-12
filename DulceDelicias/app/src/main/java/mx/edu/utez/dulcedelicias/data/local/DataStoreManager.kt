package mx.edu.utez.dulcedelicias.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "carrito_prefs")

class DataStoreManager(private val context: Context) {
    private val CARRITO_KEY = stringPreferencesKey("carrito_json")

    suspend fun guardarCarrito(json: String) {
        context.dataStore.edit { prefs ->
            prefs[CARRITO_KEY] = json
        }
    }

    val carritoFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[CARRITO_KEY]
    }
}
