package br.edu.up.cadastrode20clientes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.edu.up.cadastrode20clientes.data.RetrofitInstance
import br.edu.up.cadastrode20clientes.domain.Usuario
import br.edu.up.cadastrode20clientes.domain.UsuarioDao
import retrofit2.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaUsuariosScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {

    var usuarios by remember { mutableStateOf<List<Usuario>>(usuarioViewModel.buscarUsuarios(
        UsuarioDao)) }
    var erro by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getUsuarios().await()
            usuarios = response
        } catch (e: Exception) {
            erro = "Erro ao carregar dados"
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Lista de Usuários") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                }
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Lista de Usuários")

            if (erro != null) {
                Text(text = erro!!)
            } else {
                LazyColumn {
                    items(usuarios) { usuario ->
                        Text(text = "${usuario.nome} - ${usuario.email}")
                    }
                }
            }
        }
    }
}