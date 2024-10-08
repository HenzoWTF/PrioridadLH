package edu.ucne.prioridadlh.data.Remote.dto

import java.util.Date

data class TicketsDto(
    val ticketsId: Int?,
    val fecha: Date,
    val clientesId: Int,
    val sistemasId: Int?,
    val prioridadesId: Int,
    val solicitadoPor: String?,
    val asunto: String?,
    val descripcion: String?
)