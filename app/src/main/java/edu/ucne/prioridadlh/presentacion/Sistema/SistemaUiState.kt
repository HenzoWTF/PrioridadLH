package edu.ucne.prioridadlh.presentacion.Sistema

import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto

data class SistemaUiState (
    val sistemaId: Int = 0,
    val sistemaNombre: String = "",
    val sistemas: List<SistemasDto> = emptyList(),
    val errorMessge: String? = null,
    val success: Boolean = false
)