package edu.ucne.prioridadlh.data.Remote.dto

data class TicketDto(
    val TicketId: Int? = 0,
    val Cliente: String,
    val Asunto: String,
    val fecha: String = "",
    val PrioridadId: Int?,
    val Descripcion: String
)