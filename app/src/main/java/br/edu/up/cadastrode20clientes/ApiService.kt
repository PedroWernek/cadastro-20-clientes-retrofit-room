package br.edu.up.cadastrode20clientes

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsuarios(): Call<List<Usuario>>
}