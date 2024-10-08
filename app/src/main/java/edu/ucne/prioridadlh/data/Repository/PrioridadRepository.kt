package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.API.PrioridadesApi
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadesApi: PrioridadesApi,
    private val prioridadDao: PrioridadesDao)
{
    suspend fun save(prioridad: PrioridadesEntity) = prioridadDao.save(prioridad)
    suspend fun delete(prioridad: PrioridadesEntity) = prioridadDao.delete(prioridad)
    fun getAll() = prioridadDao.getAll()
    suspend fun find(id: Int) = prioridadDao.find(id)

    suspend fun findApi(id: Int) = prioridadesApi.getPrioridad(id)

    suspend fun GetAllApi(): List<PrioridadesDto> {
        return prioridadesApi.getPrioridades()
    }

    suspend fun saveApi(prioridadDto: PrioridadesDto?) = prioridadesApi.postPrioridad(prioridadDto)

    suspend fun deleteApi(prioridadDto: PrioridadesDto?) = prioridadDto?.idPrioridades?.let {
        prioridadesApi.deletePrioridad(it)
    }
}