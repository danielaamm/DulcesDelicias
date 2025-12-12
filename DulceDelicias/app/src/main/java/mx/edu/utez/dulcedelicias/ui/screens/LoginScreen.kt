package mx.edu.utez.dulcedelicias.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val PrimaryBrown = Color(0xFF6D4C41)
    val BackgroundCream = Color(0xFFFFF8E1)
    val AccentGold = Color(0xFFFFB74D)

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
            .background(color = BackgroundCream)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Bienvenido a Dulces Delicias",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBrown,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Image(
            painter = painterResource(R.drawable.dulcedelicias),
            contentDescription = "Logo de la pastelería",
            modifier = Modifier
                .size(180.dp)
                .padding(bottom = 32.dp)
        )


        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text(text = "Usuario") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBrown,
                unfocusedBorderColor = PrimaryBrown.copy(alpha = 0.5f),
                cursorColor = PrimaryBrown,
                focusedLabelColor = PrimaryBrown
            ),
            modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBrown,
                unfocusedBorderColor = PrimaryBrown.copy(alpha = 0.5f),
                cursorColor = PrimaryBrown,
                focusedLabelColor = PrimaryBrown
            ),
            modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 24.dp)
        )

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Button(
                onClick = {
                    viewModel.autenticar(usuario, contrasena)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBrown,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) { Text(text = "Iniciar sesión", fontSize = 16.sp) }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}