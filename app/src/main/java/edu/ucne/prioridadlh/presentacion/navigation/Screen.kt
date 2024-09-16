package edu.ucne.prioridadlh.presentacion.navigation

import androidx.room.Delete
import kotlinx.serialization.Serializable

sealed class Screen(val route: String) {
    object PrioridadesList : Screen("prioridades_list")

    data class Prioridad(val id: Int) : Screen("prioridad/{prioridadId}") {
        companion object {
            fun createRoute(id: Int) = "prioridad/$id"
        }
    }


    object TicketList : Screen("ticket_list")

    data class Ticket(val id: Int) : Screen("ticket/{ticketId}") {
        companion object {
            fun createRoute(id: Int) = "ticket/$id"
        }
    }
}
