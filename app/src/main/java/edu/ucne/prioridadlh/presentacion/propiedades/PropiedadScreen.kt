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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PrioridadScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    prioridadId: Int?,
    goPrioridadList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PrioridadBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goPrioridadList = goPrioridadList,
        prioridadId = prioridadId ?: 0
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadBody(
    uiState: PrioridadUiState,
    onEvent: (PrioridadUiEvent) -> Unit,
    goPrioridadList: () -> Unit,
    prioridadId: Int
) {
    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(PrioridadUiEvent.SelectedPrioridad(prioridadId))

        if (uiState.success) {
            goPrioridadList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Prioridades")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { goPrioridadList() }) {
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
                        value = uiState.descripcion ?: "",
                        onValueChange = {
                            onEvent(PrioridadUiEvent.DescripcionChanged(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        label = { Text("Días Compromiso") },
                        value = if (uiState.diasCompromiso == 0) "" else uiState.diasCompromiso.toString(),
                        onValueChange = {
                            val diasCompromiso = it.toIntOrNull()
                            onEvent(PrioridadUiEvent.DiasCompromisoChanged(diasCompromiso?.toString() ?: ""))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    uiState.errorMessge?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = {
                                onEvent(PrioridadUiEvent.Save)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Guardar"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }
}
