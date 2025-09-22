package br.edu.up.cadastrode20clientes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun ListaUsuariosScreen() {

    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var erro by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getUsuarios().await()
            usuarios = response
        } catch (e: Exception) {
            erro = "Erro ao carregar dados"
        }
    }

    Column {
        Text(text = "Lista de Usuários")

        if (erro != null) {
            Text(text = erro!!)
        } else {
            LazyColumn {
                items(usuarios) { usuario ->
                    Text(text = "${usuario.name} - ${usuario.email}")
                }
            }
        }
    }
}