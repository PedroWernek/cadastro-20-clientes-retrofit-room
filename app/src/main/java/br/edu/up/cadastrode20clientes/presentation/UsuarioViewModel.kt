package br.edu.up.cadastrode20clientes.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.cadastrode20clientes.data.AppDatabase
import br.edu.up.cadastrode20clientes.data.RetrofitInstance
import br.edu.up.cadastrode20clientes.domain.Usuario
import br.edu.up.cadastrode20clientes.domain.UsuarioDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao: UsuarioDao

    init {
        val db = AppDatabase.getDatabase(application)
        usuarioDao = db.usuarioDao()
        // Chama a função para buscar dados da API assim que o ViewModel for criado
        fetchUsuariosFromApi()
    }

    // Função para buscar dados do banco de dados local (Room) e exibir na tela
    fun getAllUsuarios(): Flow<List<Usuario>> {
        return usuarioDao.getAllClientes()
    }

    // Função para inserir um usuário no banco de dados local
    fun inserirUsuario(nome: String, email: String) { // Removido o 'username' que não era usado
        viewModelScope.launch {
            val usuario = Usuario(nome = nome, email = email)
            usuarioDao.inserir(usuario)
        }
    }

    // --- NOVA FUNÇÃO PARA USAR O RETROFIT ---
    // Esta função busca os usuários da API e os insere no banco de dados local.
    // pedrowernek/cadastro-20-clientes-retrofit-room/cadastro-20-clientes-retrofit-room-8f95df7f70725c9a1f7be70dbdf82030e49a2970/app/src/main/java/br/edu/up/cadastrode20clientes/presentation/UsuarioViewModel.kt
    private fun fetchUsuariosFromApi() {
        viewModelScope.launch {
            try {
                // Chama o método suspend diretamente
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
                Log.e("UsuarioViewModel", "Erro de rede: ${e.message}")
            } catch (e: HttpException) {
                Log.e("UsuarioViewModel", "Erro HTTP: ${e.message}")
            }
        }
    }
}