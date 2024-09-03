package edu.ucne.prioridadlh.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.prioridadlt.data.local.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao{
    @Upsert()
    suspend fun save(prioridad: PrioridadEntity)

    @Query(
        """
            SELECT *
            FROM Prioridad
            WHERE PrioridadId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadEntity?

    @Delete
    suspend fun delete(Prioridad: PrioridadEntity)


    @Query("DELETE FROM Prioridad")
    suspend fun deleteAll()

    @Query("SELECT * FROM Prioridad")
    fun getAll(): Flow<List<PrioridadEntity>>
}