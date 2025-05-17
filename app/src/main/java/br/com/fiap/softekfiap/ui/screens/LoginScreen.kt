package br.com.fiap.softekfiap.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softekfiap.data.AppDatabase
import br.com.fiap.softekfiap.repository.UserRepository
import br.com.fiap.softekfiap.viewmodel.LoginViewModel
import br.com.fiap.softekfiap.viewmodel.LoginViewModelFactory
import br.com.fiap.softekfiap.util.SessionManager


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val repository = UserRepository(db.userDao())
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult by viewModel.loginResult.collectAsState()

    LaunchedEffect(loginResult) {
        loginResult?.let {
            if (it.isSuccess) {
                val user = it.getOrNull()
                SessionManager.salvarIdUsuario(context, user!!.id)
                Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Ir para próxima tela (ex: checkin)
                navController.navigate("checkin?userId=${user.id}") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                Toast.makeText(context, it.exceptionOrNull()?.message ?: "Erro", Toast.LENGTH_SHORT).show()
            }
            viewModel.clearResults()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login Anônimo", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Apelido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        viewModel.login(username, password)
                    } else {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text("Criar conta fictícia")
            }
        }
    }
}
