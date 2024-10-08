package edu.ucne.prioridadlh.data.Remote.API

import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import retrofit2.http.*

interface PrioridadesApi {
    @GET("api/Prioridades")
    suspend fun getPrioridades(): List<PrioridadesDto>
    @GET("api/Prioridades/{id}")
    suspend fun getPrioridad(@Path("id") id: Int): PrioridadesDto
    @POST("api/Prioridades")
    suspend fun postPrioridad(@Body prioridadDto: PrioridadesDto?): PrioridadesDto
    @PUT("api/Prioridades/{id}")
    suspend fun putPrioridad(@Path("id") id: Int, @Body prioridad: PrioridadesDto): PrioridadesDto
    @DELETE("api/Prioridades/{id}")
    suspend fun deletePrioridad(@Path("id") id: Int)

}