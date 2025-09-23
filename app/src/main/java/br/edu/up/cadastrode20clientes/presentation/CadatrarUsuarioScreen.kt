package br.edu.up.cadastrode20clientes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroUsuarioScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Usuário") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (nome.isNotBlank() && email.isNotBlank()) {
                        usuarioViewModel.inserirUsuario(nome, email)
                        nome = ""
                        email = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nome.isNotBlank() && email.isNotBlank()

            ) {
                Text("Cadastrar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("listar")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lista de usuários")
            }
        }
    }
}