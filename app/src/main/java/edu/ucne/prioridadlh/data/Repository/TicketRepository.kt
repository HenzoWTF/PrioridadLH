package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.API.TicketsApi
import edu.ucne.prioridadlh.data.Remote.dto.TicketsDto
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketApi: TicketsApi
) {
    suspend fun getTicketById(ticketId: Int) = ticketApi.getTicket(ticketId)

    suspend fun saveTicketApi(ticketDto: TicketsDto?) = ticketApi.postTicket(ticketDto)

    suspend fun getAll() = ticketApi.getTickets()

}