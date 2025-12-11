package mx.edu.utez.dulcedelicias.data.network.util

import java.time.LocalTime

fun shouldUseDarkTheme(): Boolean {
    val hora = LocalTime.now().hour
    println("HORA ACTUAL -> $hora")
    return (hora >= 18 || hora < 7) // 6 PM - 6 AM oscuro
}


