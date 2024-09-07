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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.prioridadlh.ui.theme.PrioridadLHTheme
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListSc(
    prioridadList: List<PrioridadesEntity>,
    goToPrioridad: (Int) -> Unit,
    onAddPrioridad: () -> Unit
) {
    Scaffold (
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
    ){innerPadding ->
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
                    PrioridadRow(item)
                }
            }
        }
    }
}

@Composable
private fun PrioridadRow(it: PrioridadesEntity){
    Row(
        modifier = Modifier.clickable {

        },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrioridadLHPreview() {
    PrioridadLHTheme {
        val examplePrioridadesList = listOf(
            PrioridadesEntity( 1, "Prioridad Alta", "5"),
            PrioridadesEntity(2,"Prioridad Media","10"),
            PrioridadesEntity(3,"Prioridad Baja","15")
        )
        PrioridadListSc(
            prioridadList = examplePrioridadesList,
            goToPrioridad = { },
            onAddPrioridad = { }
        )
    }
}

