package edu.ucne.prioridadlh.presentacion.ticket

import java.util.Date

sealed interface TicketUiEvent {
    data class TicketIdChanged(val ticketId: Int): TicketUiEvent
    data class FechaChanged(val Fecha: Date?) : TicketUiEvent
    data class ClienteChanged(val Cliente: String): TicketUiEvent
    data class AsuntoChanged(val Asunto: String) : TicketUiEvent
    data class DescripcionChanged(val Descripcion: String) : TicketUiEvent
    data class PrioridadIdChanged(val PrioridadId: String) : TicketUiEvent
    data class TicketSelected(val TicketId: Int) : TicketUiEvent
    object  Save : TicketUiEvent
    object  Delete : TicketUiEvent
}