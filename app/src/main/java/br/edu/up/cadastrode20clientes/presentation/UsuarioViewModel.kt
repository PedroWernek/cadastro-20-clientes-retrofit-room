package br.edu.up.cadastrode20clientes.presentation

import android.util.Log
import br.edu.up.cadastrode20clientes.domain.Usuario
import br.edu.up.cadastrode20clientes.domain.UsuarioDao

class UsuarioViewModel {
    fun buscarUsuarios(usuarioDao: UsuarioDao): List<Usuario> {
        return try {
            usuarioDao.getAllClientes()
        } catch (e: Exception) {
            Log.e("Erro ao buscar", "Erro ao buscar usuários: ${e.message}")
            emptyList()
        }
    }
    // Função para inserir um usuário no banco de dados
    suspend fun inserirUsuario(nome: String, email: String, username: String, usuarioDao: UsuarioDao) {
        try {
            usuarioDao.inserir(
                Usuario(
                    nome = nome, email = email

                )
            )
        } catch (e: Exception) {
            Log.e("Erro ao inserir", "Erro ao inserir usuário: ${e.message}")
        }
    }
}
