package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.ClientesRemoteDataSource
import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import edu.ucne.prioridadlh.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clientesRemote: ClientesRemoteDataSource,
){
    fun GetAllApi() : Flow<Resource<List<ClienteDto>>> = flow {
        try {
            emit(Resource.Loading())
            val cliente = clientesRemote.getSistemas()
            emit(Resource.Success(cliente))
        }catch (e : HttpException){
            emit(Resource.Error("Error HTTP GENERAL ${e.message}"))
        }catch (e: Exception){
            emit(Resource.Error("Error Desconocido ${e.message}"))
        }
    }
    suspend fun saveApi(clienteDto: ClienteDto?) = clientesRemote.addSistemas(clienteDto)
    suspend fun findApi(id: Int) = clientesRemote.getSistemas(id)
    suspend fun deleteApi(clienteDto: ClienteDto?) = clienteDto?.clientesID?.let {
        clientesRemote.deleteSistema(it)
    }

}