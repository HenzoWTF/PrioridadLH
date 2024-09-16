package edu.ucne.prioridadlh.presentacion.ticket

import edu.ucne.prioridadlh.data.local.entities.TicketEntity
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import java.util.Date

data class TicketUiState(
    val TicketId: Int? = null,
    val Fecha: Date? = null,
    val Cliente: String? = "",
    val Asunto: String? = "",
    val Descripcion: String? = "",
    val PrioridadId: Int = 0,
    val errorMessage: String? = null,
    val Tickets: List<TicketEntity> = emptyList(),
    val Prioridades: List<PrioridadesEntity> = emptyList(),
    val success: Boolean = false
)