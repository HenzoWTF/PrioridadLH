package edu.ucne.prioridadlh.data.Remote.API

import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteApi {
    @GET("api/Clientes")
    suspend fun getClientes(): List<ClienteDto>
    @GET("api/Clientes/{id}")
    suspend fun getCliente(@Path("id") id: Int): ClienteDto
    @POST("api/Clientes")
    suspend fun postCliente(@Body cliente: ClienteDto?): ClienteDto?
    @PUT("api/Clientes/{id}")
    suspend fun putCliente(@Path("id") id: Int, @Body cliente: ClienteDto): ClienteDto
    @DELETE("api/Clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Int)


}