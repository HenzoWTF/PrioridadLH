package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.API.ClienteApi
import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: ClienteApi,
){
    suspend fun GetAllApi() = clienteApi.getClientes()
    suspend fun saveApi(clienteDto: ClienteDto?) = clienteApi.postCliente(clienteDto)
    suspend fun updateApi(clienteDto: ClienteDto?) = clienteApi.putCliente(clienteDto?.clientesID!!, clienteDto)
    suspend fun findApi(id: Int) = clienteApi.getCliente(id)
    suspend fun deleteApi(clienteDto: ClienteDto?) = clienteDto?.clientesID?.let {
        clienteApi.deleteCliente(it)
    }

}