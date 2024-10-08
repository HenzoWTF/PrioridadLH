package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.API.SistemasApi
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemasApi: SistemasApi
) {
    suspend fun GetSistemas() = sistemasApi.GetSistemas()
    suspend fun saveSistemaApi(sistemasDto: SistemasDto?) = sistemasApi.PostSistemas(sistemasDto)
    suspend fun findSistemaApi(id: Int) = sistemasApi.GetSistema(id)
    suspend fun PutSistemas(sistemasDto: SistemasDto) = sistemasApi.PutSistemas(sistemasDto)
    suspend fun DeleteSistemasApi(sistemasId: Int) = sistemasApi.DeleteSistemas(sistemasId)

}