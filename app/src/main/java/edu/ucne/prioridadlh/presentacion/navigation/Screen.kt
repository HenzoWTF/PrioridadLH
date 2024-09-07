package edu.ucne.prioridadlh.presentacion.navigation

import kotlinx.serialization.Serializable

sealed class Screen{
    @Serializable
    data object  PrioridadesList : Screen()
    @Serializable
    data class Prioridad(val PrioridadId: Int) : Screen()
}