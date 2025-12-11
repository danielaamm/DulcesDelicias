package mx.edu.utez.dulcedelicias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.utez.dulcedelicias.data.network.navigation.AppNavigation
import mx.edu.utez.dulcedelicias.ui.theme.DulceDeliciasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DulceDeliciasTheme {
                AppNavigation()
            }
        }
    }
}
