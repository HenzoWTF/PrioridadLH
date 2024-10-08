package edu.ucne.prioridadlh.presentacion.Sistema

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadlh.data.Remote.dto.SistemasDto
import edu.ucne.prioridadlh.data.Repository.SistemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SistemaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
    }

    fun someFunction() {
        TODO("Not yet implemented")
    }

    fun onEvent(event: SistemaUiEvent) {
        viewModelScope.launch {
            when (event) {
                SistemaUiEvent.Delete -> sistemaRepository.DeleteSistemasApi(_uiState.value.sistemaId)
                is SistemaUiEvent.NombreChanged -> _uiState.update { it.copy(sistemaNombre = event.nombre) }
                SistemaUiEvent.Save -> {
                    val errorMessage = validateInput()
                    if (errorMessage == null) {
                        sistemaRepository.saveSistemaApi(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true, errorMessge = null) }
                    } else {
                        _uiState.update { it.copy(errorMessge = errorMessage) }
                    }
                }
                is SistemaUiEvent.SelectedSistema -> _uiState.update { it.copy(sistemaId = event.sistemaId) }
                is SistemaUiEvent.SistemaIdChanged -> _uiState.update { it.copy(sistemaId = event.sistemaId) }
            }
        }
    }

    private fun validateInput(): String? {
        return when {
            _uiState.value.sistemaNombre.isNullOrBlank() -> "El nombre del sistema no puede estar vacío."
            else -> null
        }
    }

    private fun getSistemas() {
        viewModelScope.launch {
            try {
                val sistema = sistemaRepository.GetSistemas()
                _uiState.update { it.copy(sistemas = sistema) }
            } catch (e: Exception) {
                Log.e("PrioridadViewModel", "Error fetching prioridades: ${e.message}")
            }
        }
    }
}

private fun SistemaUiState.toEntity() = SistemasDto(
    sistemasId = sistemaId,
    sistemasNombres = sistemaNombre

)