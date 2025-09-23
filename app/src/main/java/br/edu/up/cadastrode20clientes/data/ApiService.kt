// pedrowernek/cadastro-20-clientes-retrofit-room/cadastro-20-clientes-retrofit-room-8f95df7f70725c9a1f7be70dbdf82030e49a2970/app/src/main/java/br/edu/up/cadastrode20clientes/data/ApiService.kt
package br.edu.up.cadastrode20clientes.data

import br.edu.up.cadastrode20clientes.domain.Usuario
import retrofit2.http.GET
import retrofit2.Response // Importar Response

interface ApiService {
    @GET("usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>
}