package edu.ucne.prioridadlh.presentacion.ticket

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadlh.data.Remote.dto.ClienteDto
import edu.ucne.prioridadlh.data.Remote.dto.TicketsDto
import edu.ucne.prioridadlh.data.Repository.ClienteRepository
import edu.ucne.prioridadlh.data.Repository.PrioridadRepository
import edu.ucne.prioridadlh.data.Repository.SistemaRepository
import edu.ucne.prioridadlh.data.Repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository,
    private val sistemaRepository: SistemaRepository,
    private val clienteRepository: ClienteRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
        getSistemas()
        getClientes()
    }
    fun someFunction() {
        TODO("Not yet implemented")
    }
    fun onEvent(event: TicketUiEvent) {
        when (event) {
            is TicketUiEvent.FechaChange -> onFechaChanged(event.fecha)
            is TicketUiEvent.PrioridadChange -> onPrioridadChanged(event.prioridadId)
            is TicketUiEvent.ClienteChange -> onClienteChanged(event.cliente)
            is TicketUiEvent.AsuntoChange -> onAsuntoChanged(event.asunto)
            is TicketUiEvent.DescripcionChange -> onDescripcionChanged(event.descripcion)
            TicketUiEvent.Save -> saveTicket()
            is TicketUiEvent.Delete -> deleteTicket(event.ticketId)
            TicketUiEvent.New -> newTicket()
            TicketUiEvent.Validation -> validateInput()
            is TicketUiEvent.SelectTicket -> SelectTicket(event.ticketId)
            is TicketUiEvent.SolicitadoPorChangend ->_uiState.update { it.copy( solicitadoPor = event.solicitadoPor,) }
        }
    }

    private fun saveTicket() {
        val errorMessage = validateInput()
        if (errorMessage == null) {
            viewModelScope.launch {
                ticketRepository.saveTicketApi(_uiState.value.toEntity())
                _uiState.update { it.copy(success = true, errorDescripcion = null) }
            }
        } else {
            _uiState.update { it.copy(errorDescripcion = errorMessage.toString()) }
        }
    }

    private fun deleteTicket(ticketId: Int) {
    }

    private fun newTicket() {
    }


    private fun getTickets(){
        viewModelScope.launch {
            try {
                val tickets = ticketRepository.getAll()
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo tickets: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun getPrioridades(){
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val prioridades = prioridadRepository.GetAllApi()
                    _uiState.update { it.copy(prioridades = prioridades) }
                } catch (e: Exception) {
                    Log.e("PrioridadViewModel", "Error fetching prioridades: ${e.message}")
                }
            }
        }
    }

    private fun getSistemas(){
        viewModelScope.launch {
            try {
                val sistema = sistemaRepository.GetSistemas()
                _uiState.update { it.copy(sistemas = sistema) }
            } catch (e: Exception) {
                Log.e("PrioridadViewModel", "Error fetching prioridades: ${e.message}")
            }
        }
    }

    private fun getClientes(){
        viewModelScope.launch {
            try {
                val clientes = clienteRepository.GetAllApi()
                _uiState.update {
                    it.copy(clientes = clientes)
                }
            }catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo cliente: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun onPrioridadChanged(prioridadIdString: String) {
//        val prioridadId = prioridadIdString.toIntOrNull()
//        if (prioridadId != null) {
//            selectedPrioridad(prioridadId)
//        } else {
//            _uiState.update {
//                it.copy(
//                    prioridadId = null,
//                    errorPrioridad = "Prioridad inválida."
//                )
//            }
//        }
    }

    private fun selectedPrioridad(prioridadId: Int) {
//        viewModelScope.launch {
//            val prioridad = prioridadRepository.find(prioridadId)
//            _uiState.update {
//                it.copy(
//                    prioridadId = prioridad?.idPrioridades,
//                    errorPrioridad = if (prioridad == null) "Prioridad no encontrada." else ""
//                )
//            }
//        }
    }
    private fun SelectTicket(ticketId: Int) {
//        viewModelScope.launch {
//            val ticket = ticketRepository.getTicketById(ticketId)
//            _uiState.update {
//                it.copy(
//                    ticketId = ticket?.TicketsId,
//                    fecha = (ticket?.fecha).toString(),
//                    prioridadId = ticket?.prioridadId,
//                    asunto = ticket?.Asunto ?: "",
//                    descripcion = ticket?.Descripcion ?: "",
//                    errorFecha = "",
//                    errorPrioridad = "",
//                    errorCliente = "",
//                    errorAsunto = "",
//                    errorDescripcion = "",
//                    validation = false
//                )
//            }
//        }
    }

    private fun onFechaChanged(fecha: String) {
//        _uiState.update {
//            it.copy(
//                fecha = fecha,
//                errorFecha = if (fecha.isEmpty()) "Fecha obligatoria." else ""
//            )
//        }
    }

    private fun onClienteChanged(cliente: List<ClienteDto>) {
        _uiState.update {
            it.copy(
                clientes = cliente,
                errorCliente = if (_uiState.value.clientes.isEmpty() || !Regex("^[a-zA-Z]+$")
                        .matches(_uiState.value.clientes.toString()))
                    "Cliente obligatorio y solo debe contener letras." else ""
            )
        }
    }

    private fun onAsuntoChanged(asunto: String) {
        _uiState.update {
            it.copy(
                asunto = asunto,
                errorAsunto = if (asunto.isEmpty()) "Asunto obligatorio." else ""
            )
        }
    }

    private fun onDescripcionChanged(descripcion: String) {
        _uiState.update {
            it.copy(
                descripcion = descripcion,
                errorDescripcion = if (descripcion.isEmpty()) "Descripción obligatoria." else ""
            )
        }
    }

    private fun validateInput(): Boolean {
        var valid = true
        valid = validateFecha() && valid
        valid = validateCliente() && valid
        valid = validateAsunto() && valid
        valid = validateDescripcion() && valid
        valid = validatePrioridad() && valid
        return valid
    }

    private fun validateFecha(): Boolean {
//        return if (_uiState.value.fecha.isEmpty()) {
//            _uiState.update { it.copy(errorFecha = "Fecha obligatoria.") }
//            false
//        } else true
        return true
    }

    private fun validateCliente(): Boolean {
        return if (_uiState.value.clientes.isEmpty() || !Regex("^[a-zA-Z]+$").matches(_uiState.value.clientes.toString())) {
            _uiState.update { it.copy(errorCliente = "Cliente obligatorio y solo debe contener letras.") }
            false
        } else true
    }

    private fun validateAsunto(): Boolean {
        return if (_uiState.value.asunto!!.isEmpty()) {
            _uiState.update { it.copy(errorAsunto = "Asunto obligatorio.") }
            false
        } else true
    }

    private fun validateDescripcion(): Boolean {
        return if (_uiState.value.descripcion!!.isEmpty()) {
            _uiState.update { it.copy(errorDescripcion = "Descripción obligatoria.") }
            false
        } else true
    }

    private fun validatePrioridad(): Boolean {
        return if (_uiState.value.ticketId == null) {
            _uiState.update { it.copy(errorPrioridad = "Prioridad obligatoria.") }
            false
        } else true
    }
}


fun TicketUiState.toEntity(): TicketsDto {
    return TicketsDto(
        ticketsId = ticketId,
        clientesId = clienteId,
        sistemasId = sistemaId,
        prioridadesId = prioriodadId,
        solicitadoPor = solicitadoPor,
        fecha = fecha ?: Date(),
        asunto = asunto ?: "",
        descripcion = descripcion ?: ""
    )
}