package edu.ucne.prioridadlh.presentacion.propiedades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadScreen(
    goPrioridadesList: () -> Unit,
    prioridadesDb: PrioridadesDb
) {
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Prioridades")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { goPrioridadesList() }) {
                Icon(Icons.Filled.ArrowBack, "Atras")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
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
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                diasCompromiso = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = error != null,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
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
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    nuevasPrioridades(prioridadesDb)
                                }
                                goPrioridadesList()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Spacer(modifier = Modifier.width(0.4.dp))
                            Text(text = "Nuevo")
                        }

                        OutlinedButton(
                            onClick = {
                                when {
                                    descripcion.isBlank() -> error = "Coloque la Descripción"
                                    diasCompromiso.isBlank() -> error = "Coloque los Días Compromiso"
                                    diasCompromiso.toIntOrNull() == 0 -> error = "Los Días Compromiso no pueden ser 0"
                                    else -> {
                                        scope.launch {
                                            savePrioridad(
                                                PrioridadesEntity(
                                                    Descripcion = descripcion,
                                                    DiasCompromiso = diasCompromiso
                                                ),
                                                prioridadesDb
                                            )
                                            descripcion = ""
                                            diasCompromiso = ""
                                            error = null
                                        }
                                        goPrioridadesList()
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
                }
            }
        }
    }
}

private suspend fun savePrioridad(prioridad: PrioridadesEntity, prioridadesDb: PrioridadesDb) {
    prioridadesDb.prioridadesDao().save(prioridad)
}

private suspend fun nuevasPrioridades(prioridadesDb: PrioridadesDb) {
    prioridadesDb.prioridadesDao().deleteAll()
}
