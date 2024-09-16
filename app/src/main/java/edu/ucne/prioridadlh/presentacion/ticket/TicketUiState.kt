package edu.ucne.prioridadlh.presentacion.ticket

import edu.ucne.prioridadlh.data.local.entities.TicketEntity
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import java.util.Date

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: String = "",
    val prioridadId: Int? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val errorFecha: String = "",
    val errorPrioridad: String = "",
    val errorCliente: String = "",
    val errorAsunto: String = "",
    val errorDescripcion: String = "",
    var validation: Boolean = false,
    val prioridades: List<PrioridadesEntity> = emptyList(),
    val tickets : List<TicketEntity> = emptyList(),
    val success: Boolean = false
)