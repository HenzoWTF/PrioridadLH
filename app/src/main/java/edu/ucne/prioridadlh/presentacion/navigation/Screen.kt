package edu.ucne.prioridadlh.presentacion.navigation

import androidx.room.Delete
import kotlinx.serialization.Serializable

//sealed class Screen{
//    @Serializable
//    data object  PrioridadesList : Screen()
//    @Serializable
//    data class Prioridad(val PrioridadId: Int?) : Screen()
//}

sealed class Screen(val route: String) {
    object PrioridadesList : Screen("prioridades_list")

    data class Prioridad(val id: Int) : Screen("prioridad/{prioridadId}") {
        companion object {
            fun createRoute(id: Int) = "prioridad/$id"
            fun Delete(id: Int) = "prioridad/$id"
        }
    }
}
