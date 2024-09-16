package edu.ucne.prioridadlh.presentacion.propiedades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
        getPrioridades()
    }
    fun someFunction() {
        TODO("Not yet implemented")
    }
    fun onEvent(event: PrioridadUiEvent) {
        viewModelScope.launch {
            when (event) {
                PrioridadUiEvent.Delete -> prioridadRepository.delete(_uiState.value.toEntity())
                is PrioridadUiEvent.DescripcionChanged -> _uiState.update { it.copy(descripcion = event.descripcion) }
                is PrioridadUiEvent.DiasCompromisoChanged -> _uiState.update { it.copy(diasCompromiso = event.diasCompromiso.toIntOrNull() ?: 0) }
                is PrioridadUiEvent.PrioridadIdChanged -> {
                    _uiState.update {
                        it.copy(prioridadId = event.prioridadId)
                    }
                }
                PrioridadUiEvent.Save -> {
                    _uiState.value.errorMessge = validateInput()
                    if (_uiState.value.errorMessge == null) {
                        prioridadRepository.save(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true) }
                    }
                }
                is PrioridadUiEvent.SelectedPrioridad -> {
                    if (event.prioridadId > 0) {
                        val prioridad = prioridadRepository.find(event.prioridadId)
                        _uiState.update {
                            it.copy(
                                prioridadId = prioridad?.PrioridadId,
                                descripcion = prioridad?.Descripcion,
                                diasCompromiso = prioridad?.DiasCompromiso ?: 0
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            prioridadRepository.getAll().collect { prioridad ->
                _uiState.update {
                    it.copy(prioridades = prioridad)
                }
            }
        }
    }

    private fun validateInput(): String? {
        return when {
            _uiState.value.descripcion.isNullOrBlank() -> "La descripción no puede estar vacía."
            _uiState.value.diasCompromiso <= 0 -> "Los días de compromiso deben ser mayores que 0."
            else -> null
        }
    }

    private fun PrioridadUiState.toEntity(): PrioridadesEntity {
        return PrioridadesEntity(
            PrioridadId = prioridadId,
            Descripcion = descripcion ?: "",
            DiasCompromiso = diasCompromiso
        )
    }
}


