package edu.ucne.prioridadlh.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import edu.ucne.prioridadlh.data.local.dao.TicketDao
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity

@Database(
    entities = [
        PrioridadesEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class PrioridadesDb : RoomDatabase(){
    abstract fun prioridadesDao() : PrioridadesDao
    abstract fun ticketDao(): TicketDao
}