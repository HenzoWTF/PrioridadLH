package edu.ucne.prioridadlh.presentacion.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PrioridadesList : Screen()

    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()

    @Serializable
    data object TicketList : Screen()

    @Serializable
    data class Ticket(val ticketId: Int) : Screen()

    @Serializable
    data object SistemaListSc: Screen()

    @Serializable
    data class SistemaScreen(val sistemaId: Int): Screen()

    @Serializable
    data object ClienteListSc: Screen()

    @Serializable
    data class ClienteScreen(val clienteId: Int): Screen()
}