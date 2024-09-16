package edu.ucne.prioridadlh.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import edu.ucne.prioridadlh.data.local.dao.TicketDao
import edu.ucne.prioridadlh.data.local.entities.TicketEntity
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity

@Database(
    entities = [
        PrioridadesEntity::class,
        TicketEntity::class
    ],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class PrioridadesDb : RoomDatabase(){
    abstract fun prioridadesDao() : PrioridadesDao
    abstract fun ticketDao(): TicketDao
}