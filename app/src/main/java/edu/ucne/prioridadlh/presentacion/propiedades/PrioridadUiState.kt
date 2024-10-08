package edu.ucne.prioridadlh.presentacion.propiedades

import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto

data class PrioridadUiState(
    val prioridadId: Int? = null,
    val descripcion: String? = "",
    val diasCompromiso: Int = 0,
    var errorMessge: String? = null,
    val prioridades: List<PrioridadesDto> = emptyList(),
    val success: Boolean = false
)