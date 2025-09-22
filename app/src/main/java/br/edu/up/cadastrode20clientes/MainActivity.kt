package br.edu.up.cadastrode20clientes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroUsuarioScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroUsuarioScreen() {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val usuarioDao = db.usuarioDao()

    // Carregar os usuários ao montar o Composable
    LaunchedEffect(Unit) {
        usuarios = buscarUsuarios(usuarioDao)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        TextField(
            value = username,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nome.isNotBlank() && email.isNotBlank() && username.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        inserirUsuario(nome, email,username, usuarioDao)
                        usuarios = buscarUsuarios(usuarioDao)
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

        // Exibindo a lista de usuários cadastrados
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(usuarios) { usuario ->
                UsuarioItem(usuario)
            }
        }
    }
}

// Composable que exibe os detalhes de cada usuário
@Composable
fun UsuarioItem(usuario: Usuario) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text(text = "Nome: ${usuario.nome}")
        Text(text = "Email: ${usuario.email}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}

// Função para inserir um usuário no banco de dados
suspend fun inserirUsuario(nome: String, email: String, username: String, usuarioDao: UsuarioDao) {
    try {
        usuarioDao.inserir(Usuario(
            nome = nome, email = email,
            username = username
        ))
    } catch (e: Exception) {
        Log.e("Erro ao inserir", "Erro ao inserir usuário: ${e.message}")
    }
}

// Função para buscar todos os usuários
suspend fun buscarUsuarios(usuarioDao: UsuarioDao): List<Usuario> {
    return try {
        usuarioDao.buscarTodos()
    } catch (e: Exception) {
        Log.e("Erro ao buscar", "Erro ao buscar usuários: ${e.message}")
        emptyList()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCadastroUsuarioScreen() {
    CadastroUsuarioScreen()
}
