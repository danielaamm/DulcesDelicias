package mx.edu.utez.dulcedelicias.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun EditarPerfilScreen(){
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.statusBarsPadding().fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = "Nombre")}
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text(text = "Correo Electrónico")}
        )
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(text = "Teléfono")}
        )
        OutlinedTextField(
            value = "",
            onValueChange = {}
        )
    }
    Row {
        Button(onClick = {
            if(nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty())
                mensaje = "Llena todos los campos"
            else
                mensaje = "Se guardaron los cambios!"
        }){ Text(text = "Guardar cambios")}

    }
}