package edu.ucne.prioridadlt.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Prioridades")
data class PrioridadesEntity(
    @PrimaryKey
    val PrioridadId: Int? = null,
    val Descripcion: String = "",
    val DiasCompromiso: Int
)