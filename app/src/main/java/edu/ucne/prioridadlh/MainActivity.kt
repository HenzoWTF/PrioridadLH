package edu.ucne.prioridadlh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.prioridadlh.data.local.database.PrioridadDb
import edu.ucne.prioridadlh.ui.theme.PrioridadLHTheme
import edu.ucne.prioridadlt.data.local.entities.PrioridadEntity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private  lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
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
                        PrioridadScreen()

                    }
                }
            }
        }
    }


    @Composable
    fun PrioridadScreen(){
        var Descripcion by remember { mutableStateOf("") }
        var DiasCompromiso by remember { mutableStateOf("") }
        var Error: String? by remember { mutableStateOf(null) }

        Scaffold{ innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            label = { Text(text = "Descripción") },
                            value = Descripcion,
                            onValueChange = { Descripcion = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Días Compromiso") },
                            value = DiasCompromiso,
                            onValueChange = { DiasCompromiso = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
//                    Error.let {
//                        Text(text = it, color = Color.Red)
//                    }
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            OutlinedButton(
                                onClick = { }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Boton nuevo"
                                )
                                Text(text = "Nuevo")
                            }

                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if(Descripcion.isBlank())
                                        Error = "Coloque el Nombre"

                                    scope.launch{
                                        savePrioridad(
                                            PrioridadEntity(
                                                Descripcion = Descripcion,
                                                DiasCompromiso = DiasCompromiso
                                            )
                                        )
                                        Descripcion = ""
                                        DiasCompromiso = ""
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Boton Guardar"
                                )
                                Text(text = "Guardar")
                            }
                        }

                    }
                }

                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                PrioridadListSc(prioridadList)
            }
        }
    }

    @Composable
    fun PrioridadListSc(priodadList: List<PrioridadEntity>){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Listado")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(priodadList){
                    PrioridadRow(it)
                }
            }
        }
    }

    @Composable
    private fun PrioridadRow(it: PrioridadEntity){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(modifier = Modifier.weight(1f), text = it.PrioridadId.toString())
            Text(
                modifier = Modifier.weight(1f),
                text = it.Descripcion,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(modifier = Modifier.weight(1f), text = it.DiasCompromiso)
        }
        HorizontalDivider()
    }

    private suspend fun savePrioridad(prioridad: PrioridadEntity){
        prioridadDb.prioridadDao().save(prioridad)
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        PrioridadLHTheme {
            PrioridadScreen()
        }
    }
}

