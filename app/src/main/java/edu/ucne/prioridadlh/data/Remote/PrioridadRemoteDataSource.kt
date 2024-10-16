package edu.ucne.prioridadlh.data.Remote

import edu.ucne.prioridadlh.data.Remote.API.PrioridadesApi
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import javax.inject.Inject

class PrioridadRemoteDataSource @Inject constructor(
    private val prioridadesApi: PrioridadesApi
){
    suspend fun getPrioridades() = prioridadesApi.getPrioridades()

    suspend fun addPrioridades(prioridad: PrioridadesDto) = prioridadesApi.postPrioridad(prioridad)

    suspend fun getPrioridad(id: Int) = prioridadesApi.getPrioridad(id)

    suspend fun deletePrioridad(id: Int) = prioridadesApi.deletePrioridad(id)
}