package br.edu.up.cadastrode20clientes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    suspend fun inserir(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    suspend fun buscarTodos(): List<Usuario>

    @Query("SELECT * FROM usuario ORDER BY name ASC")
    suspend fun getAllClientes(): Flow<List<Usuario>>

}
