package edu.ucne.prioridadlh.presentacion.Clientes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import edu.ucne.prioridadlh.data.Repository.ClienteRepository
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ClientesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        GetClientes()
    }
        fun someFunction() {
        TODO("Not yet implemented")
    }

    fun onEvent(event: ClientesUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ClientesUiEvent.CelularChanged -> _uiState.update { it.copy(CelularClientes = event.celular) }
                ClientesUiEvent.Delete -> clienteRepository.deleteApi(_uiState.value.toEntity())
                is ClientesUiEvent.DireccionChanged -> _uiState.update { it.copy(direccion = event.direccion) }
                is ClientesUiEvent.EmailChanged -> _uiState.update { it.copy(email = event.email) }
                is ClientesUiEvent.NombreChanged -> _uiState.update { it.copy(NombresClientes = event.nombre) }
                is ClientesUiEvent.RncChanged -> _uiState.update { it.copy(rnc = event.rnc) }

                ClientesUiEvent.Save -> {
                    val errorMessage = validateInput()
                    if (errorMessage == null) {
                        clienteRepository.saveApi(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true) }
                    } else {
                        _uiState.update { it.copy(errorMessge = errorMessage) }
                    }
                }
                is ClientesUiEvent.TelefonoChanged -> _uiState.update { it.copy(TelefonoClientes = event.telefono) }
                is ClientesUiEvent.SelectedCliente -> {
                    viewModelScope.launch {
                        if(event.clienteId > 0){
                            val cliente = clienteRepository.findApi(event.clienteId)
                            _uiState.update {
                                it.copy(
                                    ClientesID = cliente.clientesID,
                                    NombresClientes = cliente.nombresClientes,
                                    rnc = cliente.rnc,
                                    email = cliente.emailClientes,
                                    direccion = cliente.direccionClientes,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateInput(): String? {
        with(_uiState.value) {
            return when {
                NombresClientes.isNullOrBlank() -> "El nombre no puede estar vacío."
                rnc.isBlank() -> "El RNC no puede estar vacío."
                email.isBlank() || !email.contains("@") -> "Email inválido."
                CelularClientes.isNullOrBlank() -> "El celular no puede estar vacío."
                CelularClientes.length != 8 -> "El celular debe tener 8 dígitos."
                TelefonoClientes.isNullOrBlank() -> "El teléfono no puede estar vacío."
                TelefonoClientes.length != 8 -> "El teléfono debe tener 8 dígitos."
                direccion.isBlank() -> "La dirección no puede estar vacía."

                else -> null
            }
        }
    }

    private fun GetClientes() {
        viewModelScope.launch {
            try {
                val clientes = clienteRepository.GetAllApi()
                _uiState.update { it.copy(clientes = clientes) }
            } catch (e: Exception) {
                Log.e("ClienteViewModel", "Error fetching Cliente: ${e.message}")
            }
        }
    }



    private fun ClientesUiState.toEntity() = ClienteDto(
        clientesID = ClientesID,
        nombresClientes = NombresClientes ?: "",
        celularClientes = CelularClientes ?: "",
        telefonoClientes = TelefonoClientes ?: "",
        rnc = rnc,
        emailClientes = email,
        direccionClientes = direccion
    )
}