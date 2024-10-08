package edu.ucne.prioridadlh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlh.presentacion.navigation.prioridadlhNavHost
import edu.ucne.prioridadlh.ui.theme.PrioridadLHTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private  lateinit var prioridadesDb: PrioridadesDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadesDb = Room.databaseBuilder(
            applicationContext,
            PrioridadesDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            PrioridadLHTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){
                        val navController = rememberNavController()
                        val items = BuildNavigationItems(0)
                        prioridadlhNavHost(
                            navController,
                            prioridadesDb = prioridadesDb,
                            items = items
                        )

                    }
                }
            }
        }
    }


    fun BuildNavigationItems(taskCount: Int): List<NavigationItem>{
        return listOf(
            NavigationItem(
                title = "Prioridades",
                selectedIcon = Icons.Filled.Warning,
                unselectedIcon = Icons.Outlined.Warning,
                route = Route.PRIORIDAD
            ),
            NavigationItem(
                title = "Tickets",
                selectedIcon = Icons.Filled.Star,
                unselectedIcon = Icons.Outlined.Star,
                route = Route.TICKET
            ),
            NavigationItem(
                title = "Sistemas",
                selectedIcon = Icons.Filled.Warning,
                unselectedIcon = Icons.Outlined.Warning,
                route = Route.SISTEMA
            ),
            NavigationItem(
                title = "Clientes",
                selectedIcon = Icons.Filled.Star,
                unselectedIcon = Icons.Outlined.Star,
                route = Route.CLIENTE
            )
        )
    }

    enum class Route{
        PRIORIDAD,
        TICKET,
        SISTEMA,
        CLIENTE
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadLHPreview() {
        PrioridadLHTheme {
        }
    }
}

