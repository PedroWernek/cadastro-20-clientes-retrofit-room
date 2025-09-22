package br.edu.up.cadastrode20clientes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val nome: String,
    val username: String,
    val email: String
)
