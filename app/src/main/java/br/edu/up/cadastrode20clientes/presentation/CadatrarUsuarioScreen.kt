package br.edu.up.cadastrode20clientes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.up.cadastrode20clientes.data.AppDatabase
import br.edu.up.cadastrode20clientes.domain.Usuario
import br.edu.up.cadastrode20clientes.presentation.components.UsuarioItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroUsuarioScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val usuarioDao = db.usuarioDao()

    // Carregar os usuários ao montar o Composable
    LaunchedEffect(Unit) {
        usuarios = usuarioViewModel.buscarUsuarios(usuarioDao)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Cadastrar Usuário") },
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            // Campos de entrada de dados
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text(text = "Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nome.isNotBlank() && email.isNotBlank()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            usuarioViewModel.inserirUsuario(nome, email, username, usuarioDao)
                            usuarios = usuarioViewModel.buscarUsuarios(usuarioDao)
                        }

                        // Limpa os campos de texto após o cadastro
                        nome = ""
                        email = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cadastrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate("listar")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Lista de usuários")
            }
        }
    }
}

