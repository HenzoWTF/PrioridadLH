package edu.ucne.prioridadlh.presentacion.Clientes

import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto

data class ClientesUiState (
    val ClientesID: Int? = null,
    val NombresClientes: String? = "",
    val CelularClientes: String? = "",
    val TelefonoClientes: String? = "",
    val rnc: String = "",
    val email: String = "",
    val direccion: String = "",
    val errorMessge: String? = null,
    val clientes: List<ClienteDto> = emptyList(),
    val success: Boolean = false
)