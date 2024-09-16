package edu.ucne.prioridadlh.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import java.util.Date


@Entity(
    tableName = "Tickets",
    foreignKeys = [
        ForeignKey(
            entity = PrioridadesEntity::class,
            parentColumns = ["prioridadId"],
            childColumns = ["prioridadId"]
        )
    ],
    indices = [Index("prioridadId")]
)
data class TicketEntity(
    @PrimaryKey
    val TicketId: Int? = null,
    val Fecha: Date? = null,
    val Cliente: String = "",
    val Asunto: String = "",
    val Descripcion: String = "",
    val PrioridadId: Int = 0
)