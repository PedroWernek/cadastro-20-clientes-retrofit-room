package br.edu.up.cadastrode20clientes.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.up.cadastrode20clientes.domain.Usuario

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
