package edu.ucne.prioridadlh.presentacion.propiedades

import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity

data class PrioridadUiState(
    val prioridadId: Int? = null,
    val descripcion: String? = "",
    val diasCompromiso: Int = 0,
    var errorMessge: String? = null,
    val prioridades: List<PrioridadesEntity> = emptyList(),
    val success: Boolean = false
)