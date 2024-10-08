package edu.ucne.prioridadlh.presentacion.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridadlh.R
import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import edu.ucne.prioridadlh.data.Remote.dto.TicketsDto
import edu.ucne.prioridadlh.data.local.entities.TicketEntity
import edu.ucne.prioridadlh.ui.theme.PrioridadLHTheme
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable

fun TicketListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: TicketViewModel = hiltViewModel(),
    onTicketClick: (Int) -> Unit,
    onAddTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TicketListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onTicketClick = onTicketClick,
        onAddTicket = onAddTicket,
        onDeleteTicket = { ticketId ->
            viewModel.onEvent(TicketUiEvent.SelectTicket(ticketId))
            viewModel.onEvent(TicketUiEvent.Delete(ticketId))
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: TicketUiState,
    onTicketClick: (Int) -> Unit,
    onAddTicket: () -> Unit,
    onDeleteTicket: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tickets",
                        style = MaterialTheme.typography.titleLarge,
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
            FloatingActionButton(onClick = onAddTicket) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nuevo ticket"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (uiState.tickets.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Lista vacía",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    items(uiState.tickets) { ticket ->
                        TicketRow(
                            ticket = ticket,
                            prioridades = uiState.prioridades,
                            sistemas = uiState.sistemas,
                            clientes = uiState.clientes,
                            onTicketClick = onTicketClick,
                            onDeleteTicket = onDeleteTicket
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun TicketRow(
    ticket: TicketsDto,
    prioridades: List<PrioridadesDto>,
    sistemas: List<SistemasDto>,
    clientes: List<ClienteDto>,
    onTicketClick: (Int) -> Unit,
    onDeleteTicket: (Int) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    val descripcionPrioridad = prioridades.find { it.idPrioridades == ticket.prioridadesId }
        ?.descripcion ?: "Sin Prioridad"

    val descripcionSistema = sistemas.find { sistema -> sistema.sistemasId == ticket.sistemasId
    }?.sistemasNombres ?: ""

    val descripcionCliente = getClienteName(ticket.clientesId, clientes)

    Card(
        onClick = { onTicketClick(ticket.ticketsId ?: 0) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB0BEC5)),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .heightIn(min = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ticket),
                contentDescription = "Imagen Ticket",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cliente: $descripcionCliente",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Fecha: " + ticket.fecha?.let { dateFormat.format(it) }.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Sistema: $descripcionSistema",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Prioridad: $descripcionPrioridad",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Solicitado Por: ${ticket.solicitadoPor}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Asunto: ${ticket.asunto}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Descripción: ${ticket.descripcion}",
                    style = MaterialTheme.typography.bodyLarge
                )

            }
            IconButton(
                onClick = { showDeleteConfirmation = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar este Ticket?") },
                confirmButton = {
                    Button(
                        onClick = {
                            onDeleteTicket(ticket.ticketsId ?: 0)
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteConfirmation = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

private fun getPrioridadDescription(prioridadId: Int?, prioridades: List<PrioridadesDto>): String {
    return prioridades.find { it.idPrioridades == prioridadId }?.descripcion ?: "Sin Prioridad"
}

private fun getClienteName(clienteId: Int?, clientes: List<ClienteDto>): String {
    return clientes.find { it.clientesID == clienteId }?.nombresClientes ?: "Cliente desconocido"
}
