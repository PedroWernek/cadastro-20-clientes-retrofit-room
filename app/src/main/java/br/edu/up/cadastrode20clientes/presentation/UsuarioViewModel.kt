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
import kotlinx.coroutines.flow.firstOrNull // ADICIONADO: Import correto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao: UsuarioDao

    init {
        val db = AppDatabase.getDatabase(application)
        usuarioDao = db.usuarioDao()
    }

    // Função para buscar dados do banco de dados local (Room) e exibir na tela
    fun getAllUsuarios(): Flow<List<Usuario>> {
        return usuarioDao.getAllClientes()
    }

    // Função para inserir um usuário no banco de dados local
    fun inserirUsuario(nome: String, email: String) {
        if (nome.isBlank() || email.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) { // Use Dispatchers.IO para operações de banco
            val usuario = Usuario(nome = nome, email = email)
            usuarioDao.inserir(usuario)
        }
    }

    // Função para buscar usuários da API e salvar no banco local
    // Esta função agora é chamada pela UI para evitar bloqueio na inicialização
    fun fetchUsuariosFromApi() {
        viewModelScope.launch(Dispatchers.IO) { // Use Dispatchers.IO para operações de rede/banco
            try {
                // CORRIGIDO: Sintaxe ajustada para usar o operador firstOrNull()
                val userCount = usuarioDao.getAllClientes().firstOrNull()?.size ?: 0
                if (userCount > 0) {
                    Log.d("UsuarioViewModel", "Banco de dados já populado. Não buscando da API.")
                    return@launch
                }

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
            } catch (e: IOException) {
                Log.e("UsuarioViewModel", "Erro de rede (verifique a conexão e a permissão de INTERNET): ${e.message}")
            } catch (e: HttpException) {
                Log.e("UsuarioViewModel", "Erro HTTP: ${e.message}")
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Erro inesperado: ${e.message}")
            }
        }
    }
}