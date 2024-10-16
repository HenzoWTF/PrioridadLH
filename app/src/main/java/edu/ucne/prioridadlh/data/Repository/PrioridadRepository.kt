package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.PrioridadRemoteDataSource
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import edu.ucne.prioridadlh.utils.Resource
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadRemote: PrioridadRemoteDataSource
)
{
    suspend fun findApi(id: Int) = prioridadRemote.getPrioridad(id)

    fun GetAllApi(): Flow<Resource<List<PrioridadesDto>>> = flow {
        try {
            emit(Resource.Loading())
            val prioridades = prioridadRemote.getPrioridades()
            emit(Resource.Success(prioridades))
        }catch (e : HttpException){
            emit(Resource.Error("Error HTTP GENERAL ${e.message}"))
        }catch (e: Exception){
            emit(Resource.Error("Error Desconocido ${e.message}"))
        }
    }

    suspend fun saveApi(prioridadDto: PrioridadesDto) = prioridadRemote.addPrioridades(prioridadDto)

    suspend fun deleteApi(prioridadDto: PrioridadesDto?) = prioridadDto?.idPrioridades?.let {
        prioridadRemote.deletePrioridad(it)
    }
}