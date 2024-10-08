package edu.ucne.prioridadlh.presentacion.Sistema

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import edu.ucne.prioridadlh.ui.theme.PrioridadLHTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SistemasListSc(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: SistemaViewModel = hiltViewModel(),
    onClickSistema: (Int) -> Unit,
    onAddSistema: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onClickSistema = onClickSistema,
        onAddSistema = onAddSistema
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: SistemaUiState,
    onClickSistema: (Int) -> Unit,
    onAddSistema: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Sistemas",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
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
                onClick = onAddSistema
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Crear Nuevo Sistema"
                )
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.sistemas) {
                    SistemasRow(
                        it = it,
                        onClickSistema = onClickSistema
                    )
                }
            }
        }
    }
}


@Composable
fun SistemasRow(
    it: SistemasDto,
    onClickSistema: (Int) -> Unit
) {
    Card(
        onClick = {
            onClickSistema(it.sistemasId ?: 0)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB0BEC5)
        ),
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Nombre: ",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = it.sistemasNombres.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SistemaListScreenPreview(){
    PrioridadLHTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        SistemasListSc(
            drawerState = drawerState,
            scope = scope,
            onClickSistema = {},
            onAddSistema = {}
        )
    }
}