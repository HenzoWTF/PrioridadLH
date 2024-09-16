package edu.ucne.prioridadlh.presentacion.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadlh.data.Repository.PrioridadRepository
import edu.ucne.prioridadlh.data.Repository.TicketRepository
import edu.ucne.prioridadlh.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
    }

    private fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getTicket().collect { tickets ->
                _uiState.update { it.copy(Tickets = tickets) }
            }
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            prioridadRepository.getAll().collect { prioridades ->
                _uiState.update { it.copy(Prioridades = prioridades) }
            }
        }
    }

    fun onEvent(event: TicketUiEvent) {
        viewModelScope.launch {
            when (event) {
                is TicketUiEvent.TicketIdChanged -> _uiState.update { it.copy(TicketId = event.ticketId) }
                is TicketUiEvent.FechaChanged -> _uiState.update { it.copy(Fecha = event.Fecha) }
                is TicketUiEvent.PrioridadIdChanged -> _uiState.update { it.copy(PrioridadId = event.PrioridadId.toInt()) }
                is TicketUiEvent.ClienteChanged -> _uiState.update { it.copy(Cliente = event.Cliente) }
                is TicketUiEvent.AsuntoChanged -> _uiState.update { it.copy(Asunto = event.Asunto) }
                is TicketUiEvent.DescripcionChanged -> _uiState.update { it.copy(Descripcion = event.Descripcion) }
                is TicketUiEvent.TicketSelected -> {
                    if (event.TicketId > 0) {
                        val ticket = ticketRepository.finId(event.TicketId)
                        _uiState.update {
                            it.copy(
                                TicketId = ticket?.TicketId,
                                Fecha = ticket?.Fecha,
                                PrioridadId = ticket?.PrioridadId ?: 0,
                                Cliente = ticket?.Cliente,
                                Asunto = ticket?.Asunto,
                                Descripcion = ticket?.Descripcion
                            )
                        }
                    }
                }
                TicketUiEvent.Save -> {
                    val errorMessage = validateInput()
                    if (errorMessage == null) {
                        ticketRepository.save(uiState.value.toEntity())
                        _uiState.update { it.copy(success = true) }
                    } else {
                        _uiState.update { it.copy(errorMessage = errorMessage) }
                    }
                }
                TicketUiEvent.Delete -> {
                    ticketRepository.delete(uiState.value.toEntity())
                }
            }
        }
    }

    private fun validateInput(): String? {
        return when {
            _uiState.value.Cliente.isNullOrBlank() -> "El campo cliente no puede estar vacío."
            _uiState.value.Asunto.isNullOrBlank() -> "El campo asunto no puede estar vacío."
            _uiState.value.Fecha == null -> "El campo fecha no puede estar vacío."
            _uiState.value.PrioridadId <= 0 -> "El campo prioridad no puede estar vacío."
            _uiState.value.Descripcion.isNullOrBlank() -> "El campo descripción no puede estar vacío."
            else -> null
        }
    }

    private fun TicketUiState.toEntity() = TicketEntity(
        TicketId = TicketId,
        Fecha = Fecha,
        PrioridadId = PrioridadId,
        Cliente = Cliente ?: "",
        Asunto = Asunto ?: "",
        Descripcion = Descripcion ?: ""
    )
}
