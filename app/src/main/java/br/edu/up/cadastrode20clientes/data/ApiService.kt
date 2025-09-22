package br.edu.up.cadastrode20clientes.data

import br.edu.up.cadastrode20clientes.domain.Usuario
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("usuarios")
    fun getUsuarios(): Call<List<Usuario>>
}