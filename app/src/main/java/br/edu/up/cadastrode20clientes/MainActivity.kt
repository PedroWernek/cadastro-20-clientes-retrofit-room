package br.edu.up.cadastrode20clientes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.edu.up.cadastrode20clientes.presentation.navigation.AppNavGraph
import br.edu.up.cadastrode20clientes.ui.theme.CadastroDe20ClientesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroDe20ClientesTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}