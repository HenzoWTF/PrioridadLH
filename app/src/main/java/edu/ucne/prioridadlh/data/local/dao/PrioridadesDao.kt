package edu.ucne.prioridadlh.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadesDao{
    @Upsert()
    suspend fun save(prioridad: PrioridadesEntity)

    @Query(
        """
            SELECT *
            FROM Prioridades
            WHERE PrioridadId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadesEntity?

    @Delete
    suspend fun delete(Prioridad: PrioridadesEntity)


    @Query("DELETE FROM Prioridades")
    suspend fun deleteAll()

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<PrioridadesEntity>>
}