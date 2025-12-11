package mx.edu.utez.dulcedelicias.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import java.time.LocalTime
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)


private fun shouldUseDarkTheme(): Boolean {
    val hora = LocalTime.now().hour
    println("HORA ACTUAL -> $hora")
    return hora >= 18 || hora < 7
}

@Composable
fun DulceDeliciasTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = shouldUseDarkTheme()
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
