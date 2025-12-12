package mx.edu.utez.dulcedelicias.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.dulcedelicias.R
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val mensaje by viewModel.mensaje.observeAsState("")
    val loginExitoso by viewModel.loginExitoso.observeAsState(false)

    LaunchedEffect(loginExitoso) {
        if (loginExitoso) {
            navController.navigate("screenAdmin") {
                popUpTo("login") { inclusive = true }
            }
            viewModel.resetLoginStatus()
        }
    }

    Column(
        modifier = Modifier.statusBarsPadding().fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.dulcedelicias),
            contentDescription = "Inicio de sesión",
            modifier = Modifier.size(200.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text(text = "Usuario") }
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Text(text = mensaje)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.autenticar(usuario, contrasena)
                }
            ) { Text(text = "Iniciar sesión") }

            Button(onClick = {
            }) { Text(text = "Cerrar") }
        }
    }
}