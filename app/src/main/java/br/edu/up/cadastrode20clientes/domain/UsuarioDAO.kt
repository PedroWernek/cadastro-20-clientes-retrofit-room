package br.edu.up.cadastrode20clientes.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    suspend fun inserir(usuario: Usuario)

    @Query("SELECT * FROM usuarios ORDER BY nome ASC")
    fun getAllClientes(): Flow<List<Usuario>>
}