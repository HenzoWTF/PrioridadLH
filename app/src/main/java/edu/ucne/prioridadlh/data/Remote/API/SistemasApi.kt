package edu.ucne.prioridadlh.data.Remote.API

import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import retrofit2.http.*


interface SistemasApi {
    @GET("api/Sistemas")
suspend fun GetSistemas(): List<SistemasDto>

@GET("api/Sistemas/{SistemasId}")
suspend fun GetSistema(@Path("SistemasId") sistemasId: Int): SistemasDto

@POST("api/Sistemas")
suspend fun PostSistemas(@Body sistemasDto: SistemasDto?): SistemasDto?

@PUT("api/Sistemas")
suspend fun PutSistemas(@Body sistemasDto: SistemasDto): SistemasDto

@DELETE("api/Sistemas/{SistemasId}")
suspend fun DeleteSistemas(@Path("SistemasId") sistemasId: Int)
}