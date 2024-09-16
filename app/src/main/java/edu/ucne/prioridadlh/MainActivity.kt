package edu.ucne.prioridadlh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
                        prioridadlhNavHost(
                            navController,
                            prioridadesDb = prioridadesDb
                        )

                    }
                }
            }
        }
    }



    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadLHPreview() {
        PrioridadLHTheme {
        }
    }
}

