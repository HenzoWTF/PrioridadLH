package edu.ucne.prioridadlh.data.Remote

import edu.ucne.prioridadlh.data.Remote.API.ClienteApi
import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import javax.inject.Inject

class ClientesRemoteDataSource @Inject constructor(
    private val cliente: ClienteApi
) {
    suspend fun getSistemas() = cliente.getClientes()

    suspend fun addSistemas(sistemas: ClienteDto?) = cliente.postCliente(sistemas)

    suspend fun getSistemas(id: Int) = cliente.getCliente(id)

    suspend fun deleteSistema(id: Int) = cliente.deleteCliente(id)
}