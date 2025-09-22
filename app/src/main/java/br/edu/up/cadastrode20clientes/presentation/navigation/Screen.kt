package br.edu.up.cadastrode20clientes.presentation.navigation


sealed class Screen(val route: String) {
    object Cadastro : Screen("cadastrar")
    object Listar : Screen("listar")

}
