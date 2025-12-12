package mx.edu.utez.dulcedelicias.ui.screens


import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.dulcedelicias.R
import mx.edu.utez.dulcedelicias.ui.screens.viewmodel.LoginViewModel

val PrimaryBrown = Color(0xFF6D4C41)
val LightBrownBackground = Color(0xFFF5EFE7)
val AccentBrown = Color(0xFFBCAAA4)
val TextLight = Color(0xFFFFFFFF)

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val app = context.applicationContext as Application
    val viewModel: LoginViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
    )

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
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(LightBrownBackground)
            .padding(horizontal = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.dulcedelicias),
            contentDescription = "Inicio de sesión",
            modifier = Modifier.size(230.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text(text = "Usuario", color = PrimaryBrown) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBrown,
                unfocusedBorderColor = AccentBrown,
                focusedLabelColor = PrimaryBrown,
                cursorColor = PrimaryBrown,
                focusedTextColor = PrimaryBrown,
                unfocusedTextColor = PrimaryBrown
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text(text = "Contraseña", color = PrimaryBrown) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBrown,
                unfocusedBorderColor = AccentBrown,
                focusedLabelColor = PrimaryBrown,
                cursorColor = PrimaryBrown,
                focusedTextColor = PrimaryBrown,
                unfocusedTextColor = PrimaryBrown
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = mensaje, color = PrimaryBrown)

        Button(
            onClick = { viewModel.autenticar(usuario, contrasena) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBrown,
                contentColor = TextLight
            ),
        ) { Text(text = "Iniciar sesión") }
    }
}
