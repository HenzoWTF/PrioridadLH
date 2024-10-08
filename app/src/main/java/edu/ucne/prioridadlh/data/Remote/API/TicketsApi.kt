package edu.ucne.prioridadlh.data.Remote.API

import edu.ucne.prioridadlh.data.Remote.dto.TicketsDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketsApi {
    @GET("api/Tickets")
    suspend fun getTickets(): List<TicketsDto>
    @GET("api/Tickets/{id}")
    suspend fun getTicket(@Path("id") id: Int) : TicketsDto
    @POST("api/Tickets")
    suspend fun postTicket(@Body ticket: TicketsDto?): TicketsDto?
    @PUT("api/Tickets/{id}")
    suspend fun putTicket(@Path("id") id: Int, @Body ticket: TicketsDto?): TicketsDto?
    @DELETE("api/Tickets/{id}")
    suspend fun deleteTicket(@Path("id") id: Int)
}