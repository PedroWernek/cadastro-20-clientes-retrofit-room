package br.edu.up.cadastrode20clientes.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.cadastrode20clientes.data.AppDatabase
import br.edu.up.cadastrode20clientes.domain.Usuario
import br.edu.up.cadastrode20clientes.domain.UsuarioDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao: UsuarioDao

    init {
        val db = AppDatabase.getDatabase(application)
        usuarioDao = db.usuarioDao()
    }

    fun getAllUsuarios(): Flow<List<Usuario>> {
        return usuarioDao.getAllClientes()
    }

    fun inserirUsuario(nome: String, email: String, username: String) {
        viewModelScope.launch {
            val usuario = Usuario(nome = nome, email = email)
            usuarioDao.inserir(usuario)
        }
    }
}