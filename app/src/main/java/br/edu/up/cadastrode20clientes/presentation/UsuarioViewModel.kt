package br.edu.up.cadastrode20clientes.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.cadastrode20clientes.data.AppDatabase
import br.edu.up.cadastrode20clientes.data.RetrofitInstance
import br.edu.up.cadastrode20clientes.domain.Usuario
import br.edu.up.cadastrode20clientes.domain.UsuarioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao: UsuarioDao

    init {
        val db = AppDatabase.getDatabase(application)
        usuarioDao = db.usuarioDao()
    }

    fun getAllUsuarios(): Flow<List<Usuario>> {
        return usuarioDao.getAllClientes()
    }

    fun inserirUsuario(nome: String, email: String) {
        if (nome.isBlank() || email.isBlank()) {
            Log.w("UsuarioViewModel", "Tentativa de inserir usuário com campos vazios.")
            return
        }

        // Garante que a inserção ocorra em segundo plano (IO Dispatcher)
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("UsuarioViewModel", "Iniciando inserção do usuário: $nome")
            val usuario = Usuario(nome = nome, email = email)
            usuarioDao.inserir(usuario)
            Log.d("UsuarioViewModel", "Usuário $nome inserido com sucesso.")
        }
    }

    fun fetchUsuariosFromApi() {
        // Garante que a verificação e a chamada de rede ocorram em segundo plano
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // CORREÇÃO: A verificação do banco de dados está DENTRO da corrotina
                val userCount = usuarioDao.getAllClientes().firstOrNull()?.size ?: 0
                if (userCount > 0) {
                    Log.d("UsuarioViewModel", "Banco de dados já populado. Não buscando da API.")
                    return@launch
                }

                Log.d("UsuarioViewModel", "Banco de dados vazio. Buscando usuários da API.")
                val response = RetrofitInstance.api.getUsuarios()

                if (response.isSuccessful && response.body() != null) {
                    val usuariosDaApi = response.body()!!
                    usuariosDaApi.forEach { usuario ->
                        usuarioDao.inserir(usuario)
                    }
                    Log.d("UsuarioViewModel", "Usuários da API inseridos no banco com sucesso.")
                } else {
                    Log.e("UsuarioViewModel", "Erro na resposta da API: ${response.message()}")
                }
            } catch (e: Exception) {
                // Log genérico para qualquer tipo de erro
                Log.e("UsuarioViewModel", "Ocorreu um erro ao buscar dados da API.", e)
            }
        }
    }
}