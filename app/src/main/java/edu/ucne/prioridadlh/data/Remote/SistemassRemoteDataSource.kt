package edu.ucne.prioridadlh.data.Remote

import edu.ucne.prioridadlh.data.Remote.API.SistemasApi
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import javax.inject.Inject

class SistemassRemoteDataSource @Inject constructor(
    private val SistemasApi: SistemasApi
) {
    suspend fun getSistemas() = SistemasApi.GetSistemas()

    suspend fun addSistemas(sistemas: SistemasDto?) = SistemasApi.PostSistemas(sistemas)

    suspend fun getSistemas(id: Int) = SistemasApi.GetSistema(id)

    suspend fun updateSistemas(id: Int, sistemas: SistemasDto) = SistemasApi.PutSistemas(sistemas)

    suspend fun deleteSistema(id: Int) = SistemasApi.DeleteSistemas(id)
}