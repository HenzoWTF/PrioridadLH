package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadDao: PrioridadesDao)
{
    suspend fun save(prioridad: PrioridadesEntity) = prioridadDao.save(prioridad)
    suspend fun delete(prioridad: PrioridadesEntity) = prioridadDao.delete(prioridad)
    fun getAll() = prioridadDao.getAll()
    suspend fun find(id: Int) = prioridadDao.find(id)
}