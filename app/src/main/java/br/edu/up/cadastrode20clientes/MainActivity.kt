package br.edu.up.cadastrode20clientes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.edu.up.cadastrode20clientes.presentation.UsuarioViewModel
import br.edu.up.cadastrode20clientes.presentation.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val usuarioViewModel = UsuarioViewModel()
            AppNavGraph(navController = navController, usuarioViewModel = usuarioViewModel)
        }
    }
}
