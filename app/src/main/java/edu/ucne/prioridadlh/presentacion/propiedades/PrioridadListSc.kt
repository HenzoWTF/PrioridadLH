package edu.ucne.prioridadlh.presentacion.propiedades

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListSc(
    prioridadList: List<PrioridadesEntity>,
    onAddPrioridad: () -> Unit,
    onEditPrioridad: (Int) -> Unit,
    prioridadesDb: PrioridadesDb
) {
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
                    ) {
                        Text(text = "Lista de Prioridades")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPrioridad) {
                Icon(Icons.Filled.Add, "Agregar nueva entidad")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
                    PrioridadRow(
                        item,
                        onEdit = onEditPrioridad,
                        prioridadesDb = prioridadesDb
                    )
                }
            }
        }
    }
}

@Composable
private fun PrioridadRow(
    item: PrioridadesEntity,
    onEdit: (Int) -> Unit,
    prioridadesDb: PrioridadesDb,
) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .padding(15.dp)
            .clickable { item.PrioridadId?.let { onEdit(it) } },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(0.2f), text = item.PrioridadId.toString())
        Text(
            modifier = Modifier.weight(0.5f),
            text = item.Descripcion
        )
        Text(modifier = Modifier.weight(0.2f), text = item.DiasCompromiso)


    }

    HorizontalDivider()
}

private suspend fun EliminarPrioridad(prioridad: PrioridadesEntity, prioridadesDb: PrioridadesDb) {
    prioridadesDb.prioridadesDao().delete(prioridad)
}

