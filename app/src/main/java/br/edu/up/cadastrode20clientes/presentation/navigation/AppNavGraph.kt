package br.edu.up.cadastrode20clientes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.edu.up.cadastrode20clientes.presentation.CadastroUsuarioScreen
import br.edu.up.cadastrode20clientes.presentation.ListaUsuariosScreen
import br.edu.up.cadastrode20clientes.presentation.UsuarioViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Cadastro.route
    ) {
        composable(Screen.Cadastro.route) {
            // REFORMULADO: Busca os dados da API de forma segura aqui
            LaunchedEffect(key1 = Unit) {
                usuarioViewModel.fetchUsuariosFromApi()
            }
            CadastroUsuarioScreen(
                usuarioViewModel = usuarioViewModel,
                navController = navController,
            )
        }
        composable(Screen.Listar.route) {
            ListaUsuariosScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel
            )
        }
    }
}