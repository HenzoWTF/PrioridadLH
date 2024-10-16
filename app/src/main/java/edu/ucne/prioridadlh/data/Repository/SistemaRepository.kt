package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.SistemassRemoteDataSource
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import edu.ucne.prioridadlh.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemasRemote: SistemassRemoteDataSource
) {
    fun GetSistemas() : Flow<Resource<List<SistemasDto>>> = flow {
        try {
            emit(Resource.Loading())
            val sistemas = sistemasRemote.getSistemas()
            emit(Resource.Success(sistemas))
        }catch (e : HttpException){
            emit(Resource.Error("Error HTTP GENERAL ${e.message}"))
        }catch (e: Exception){
            emit(Resource.Error("Error Desconocido ${e.message}"))
        }
    }

    suspend fun saveSistemaApi(sistemasDto: SistemasDto?) = sistemasRemote.addSistemas(sistemasDto)
    suspend fun findSistemaApi(id: Int) = sistemasRemote.getSistemas(id)
    suspend fun DeleteSistemas(sistemasDto: Int) = sistemasRemote.deleteSistema(sistemasDto)

}