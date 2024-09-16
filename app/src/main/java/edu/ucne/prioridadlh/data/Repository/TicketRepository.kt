package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.local.dao.TicketDao
import edu.ucne.prioridadlh.data.local.entities.TicketEntity
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao
) {
    suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)
    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)
    suspend fun finId(ticketId: Int) = ticketDao.find(ticketId)
    suspend fun findCliente(cliente: String) = ticketDao.findCliente(cliente)
    suspend fun findAsunto(asunto: String) = ticketDao.findAsunto(asunto)
    suspend fun findDescripcion(descripcion: String) = ticketDao.findDescripcion(descripcion)
    fun getTicket() =ticketDao.getAll()

}