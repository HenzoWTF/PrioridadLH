package edu.ucne.prioridadlh.presentacion.Clientes

sealed class ClientesUiEvent {
    data class NombreChanged(val nombre: String) : ClientesUiEvent()
    data class CelularChanged(val celular: String) : ClientesUiEvent()
    data class TelefonoChanged(val telefono: String) : ClientesUiEvent()
    data class RncChanged(val rnc: String) : ClientesUiEvent()
    data class EmailChanged(val email: String) : ClientesUiEvent()
    data class DireccionChanged(val direccion: String) : ClientesUiEvent()
    data class SelectedCliente(val clienteId: Int): ClientesUiEvent()
    object Save : ClientesUiEvent()
    object Delete : ClientesUiEvent()
}