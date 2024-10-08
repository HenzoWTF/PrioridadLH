package edu.ucne.prioridadlh.presentacion.propiedades

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import edu.ucne.prioridadlh.data.Repository.PrioridadRepository
import edu.ucne.prioridadlt.data.local.entities.PrioridadesEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PrioridadUiState())
    val uiState = _uiState.asStateFlow()

    init {
        GetallApi()
    }
    fun someFunction() {
        TODO("Not yet implemented")
    }
    fun onEvent(event: PrioridadUiEvent) {
        viewModelScope.launch {
            when (event) {
                PrioridadUiEvent.Delete -> prioridadRepository.deleteApi(_uiState.value.toEntity())
                is PrioridadUiEvent.DescripcionChanged -> _uiState.update { it.copy(descripcion = event.descripcion) }
                is PrioridadUiEvent.DiasCompromisoChanged -> _uiState.update { it.copy(diasCompromiso = event.diasCompromiso.toIntOrNull() ?: 0) }

                is PrioridadUiEvent.PrioridadIdChanged -> {
                    _uiState.update {
                        it.copy(prioridadId = event.prioridadId)
                    }
                }

                PrioridadUiEvent.Save -> {
                    val errorMessage = validateInput()
                    if (errorMessage == null) {
                        prioridadRepository.saveApi(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true, errorMessge = null) }
                    } else {
                        _uiState.update { it.copy(errorMessge = errorMessage) }
                    }
                }

                is PrioridadUiEvent.SelectedPrioridad -> {
                    viewModelScope.launch {
                        if(event.prioridadId > 0){
                            val prioridad = prioridadRepository.findApi(event.prioridadId)
                            _uiState.update {
                                it.copy(
                                    prioridadId = prioridad.idPrioridades,
                                    descripcion = prioridad.descripcion,
                                    diasCompromiso = prioridad.diasCompromiso
                                )
                            }
                        }
                    }
                }
            }
        }
    }

        private fun GetallApi() {
            viewModelScope.launch {
                try {
                    val prioridades = prioridadRepository.GetAllApi()
                    _uiState.update { it.copy(prioridades = prioridades) }
                } catch (e: Exception) {
                    Log.e("PrioridadViewModel", "Error fetching prioridades: ${e.message}")
                }
            }
        }

    private fun validateInput(): String? {
        return when {
            _uiState.value.descripcion.isNullOrBlank() -> "La descripción no puede estar vacía."
            _uiState.value.diasCompromiso < 1 || _uiState.value.diasCompromiso > 31 -> "Los días de compromiso deben estar entre 1 y 31."
            else -> null
        }
    }

    private fun PrioridadUiState.toEntity() = PrioridadesDto(
        idPrioridades = prioridadId,
        descripcion = descripcion ?: "",
        diasCompromiso = diasCompromiso
    )
}