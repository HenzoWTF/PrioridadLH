package edu.ucne.prioridadlh.presentacion.propiedades

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrioridadListSc(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: PrioridadViewModel = hiltViewModel(),
    onPrioridadClick: (Int) -> Unit,
    onAddPrioridad: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onPrioridadClick = onPrioridadClick,
        onAddPrioridad = onAddPrioridad,
        onDeletePrioridad = { prioridadId ->
            viewModel.onEvent(
                PrioridadUiEvent.PrioridadIdChanged(prioridadId)
            )
            viewModel.onEvent(
                PrioridadUiEvent.Delete
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListBodyScreen(
    uiState: PrioridadUiState,
    onPrioridadClick: (Int) -> Unit,
    onAddPrioridad: () -> Unit,
    onDeletePrioridad: (Int) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Prioridades",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPrioridad
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nueva prioridad"
                )
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        ){
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                if(uiState.prioridades.isEmpty()){
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "Lista vacía",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }else{
                    item{
                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                        ){
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
                            Text(
                                text = "",
                                modifier = Modifier.weight(0.3f),
                            )
                        }
                    }

                    items(uiState.prioridades){
                        PrioridadRow(
                            it = it,
                            onPrioridadClick = onPrioridadClick,
                            onDeletePrioridad = onDeletePrioridad
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrioridadRow(
    it: PrioridadesDto,
    onPrioridadClick: (Int) -> Unit,
    onDeletePrioridad: (Int) -> Unit
){
    var ShowDeleteC by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    onPrioridadClick(it.idPrioridades ?: 0)
                }
            )
    ){
        Text(
            text = it.descripcion,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = it.diasCompromiso.toString(),
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                onDeletePrioridad(it.idPrioridades ?: 0)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Eliminar",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { ShowDeleteC = true }
            )
        }
    }
    if (ShowDeleteC) {
            AlertDialog(
                onDismissRequest = { ShowDeleteC = false },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar esta prioridad?") },
                confirmButton = {
                    Button(
                        onClick = {
                            onDeletePrioridad(it.idPrioridades ?: 0)
                            ShowDeleteC = false
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    Button(onClick = { ShowDeleteC = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    HorizontalDivider()
}
