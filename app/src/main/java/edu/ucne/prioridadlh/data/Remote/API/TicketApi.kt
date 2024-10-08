package edu.ucne.prioridadlh.data.Remote.API

import edu.ucne.prioridadlh.data.Remote.dto.TicketDto
import retrofit2.http.*

interface TicketApi {
    @GET("api/Ticket")
    suspend fun getTickets() : List<TicketDto>
    @GET("api/Ticket/{id}")
    suspend fun getTicket(@Path("id") id: Int) : TicketDto
    @POST("api/Ticket")
    suspend fun postTicket(@Body ticket: TicketDto?): TicketDto?
    @PUT("api/Ticket/{id}")
    suspend fun putTicket(@Path("id") id: Int, @Body ticket: TicketDto?): TicketDto?
    @DELETE("api/Ticket/{id}")
    suspend fun deleteTicket(@Path("id") id: Int)
}