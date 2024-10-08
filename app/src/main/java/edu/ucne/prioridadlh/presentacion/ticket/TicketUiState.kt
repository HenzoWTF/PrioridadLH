package edu.ucne.prioridadlh.presentacion.ticket

import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import edu.ucne.prioridadlh.data.Remote.dto.TicketsDto
import retrofit2.Call
import java.util.Date

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: Date? = null,
    val clienteId: Int = 0,
    val sistemaId: Int = 0,
    val prioriodadId: Int = 0,
    val solicitadoPor: String? = "",
    val asunto: String? = "",
    val descripcion: String? = "",
    val tickets: List<TicketsDto> = emptyList(),
    val prioridades: List<PrioridadesDto> = emptyList(),
    val sistemas: List<SistemasDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList(),
    val errorFecha: String? = "",
    val errorCliente: String? = "",
    val errorSistema: String? = "",
    val errorSolicitadoPor: String? = "",
    val errorAsunto: String? = "",
    val errorPrioridad: String? = "",
    val errorDescripcion: String? = "",
    val success: Boolean = false
)