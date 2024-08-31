package edu.ucne.prioridadlt.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prioridad")
data class PrioridadEntity(
    @PrimaryKey
    val PrioridadId: Int? = null,
    val Descripcion: String = "",
    val DiasCompromiso: String = ""
)