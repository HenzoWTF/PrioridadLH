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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
    fun PrioridadScreen() {
        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf("") }
        var error by remember { mutableStateOf<String?>(null) }

        val scope = rememberCoroutineScope()

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)

            ) {
                Text(
                    text = "Registro Prioridad",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)

                    ) {
                        OutlinedTextField(
                            label = { Text("Descripción") },
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = error != null
                        )
                        OutlinedTextField(
                            label = { Text("Días Compromiso") },
                            value = diasCompromiso,
                            onValueChange = { diasCompromiso = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = error != null
                        )
                        if (error != null) {
                            Text(
                                text = error!!,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedButton(onClick = {  }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Nuevo"
                                )
                                Spacer(modifier = Modifier.width(0.4.dp))
                                Text(text = "Nuevo")
                            }

                            OutlinedButton(
                                onClick = {
                                    if (descripcion.isBlank()) {
                                        error = "Coloque la Descripción"
                                    } else {
                                        scope.launch {
                                            savePrioridad(
                                                PrioridadEntity(
                                                    Descripcion = descripcion,
                                                    DiasCompromiso = diasCompromiso
                                                )
                                            )
                                            descripcion = ""
                                            diasCompromiso = ""
                                            error = null
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Guardar"
                                )
                                Spacer(modifier = Modifier.width(0.4.dp))
                                Text(text = "Guardar")
                            }

                        }
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)

                        ){
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        deleteAllPrioridades()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Borrar Todo"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Borrar")
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
    fun PrioridadListSc(prioridadList: List<PrioridadEntity>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Listado",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "#",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(0.2f)
                )
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    text = "Días",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(0.2f)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(prioridadList) { item ->
                    PrioridadRow(item)
                }
            }
        }
    }

    @Composable
    private fun PrioridadRow(it: PrioridadEntity){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(modifier = Modifier.weight(0.2f), text = it.PrioridadId.toString())
            Text(
                modifier = Modifier.weight(0.5f),
                text = it.Descripcion,
            )
            Text(modifier = Modifier.weight(0.2f), text = it.DiasCompromiso)
        }
        HorizontalDivider()
    }



    private suspend fun savePrioridad(prioridad: PrioridadEntity){
        prioridadDb.prioridadDao().save(prioridad)
    }

    private suspend fun deleteAllPrioridades() {
        prioridadDb.prioridadDao().deleteAll()
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        PrioridadLHTheme {
            PrioridadScreen()
        }
    }
}

