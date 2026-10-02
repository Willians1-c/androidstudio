package com.upchiapas.kt_template.CalculadoraCu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sqrt

data class CalculadoraUiState(
    val a: String = "",
    val b: String = "",
    val c: String = "",
    val x1: String = "",
    val x2: String = "",
    val discriminante: Double? = null,
    val mensajeError: String? = null,
    val mostrarProcedimiento: Boolean = false
)

class CalculadoraCuaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculadoraUiState())
    val uiState: StateFlow<CalculadoraUiState> = _uiState.asStateFlow()

    fun onCoeficienteAChanged(v: String) { _uiState.update { calcular(it.copy(a = v)) } }
    fun onCoeficienteBChanged(v: String) { _uiState.update { calcular(it.copy(b = v)) } }
    fun onCoeficienteCChanged(v: String) { _uiState.update { calcular(it.copy(c = v)) } }
    fun onToggleProcedimiento() { _uiState.update { it.copy(mostrarProcedimiento = !it.mostrarProcedimiento) } }
    fun resetearCampos() { _uiState.value = CalculadoraUiState() }

    private fun calcular(state: CalculadoraUiState): CalculadoraUiState {
        val aVal = state.a.toDoubleOrNull()
        val bVal = state.b.toDoubleOrNull()
        val cVal = state.c.toDoubleOrNull()

        if (state.a.isBlank() || state.b.isBlank() || state.c.isBlank()) {
            return state.copy(x1 = "", x2 = "", discriminante = null, mensajeError = null)
        }
        if (aVal == null || bVal == null || cVal == null) {
            return state.copy(x1 = "", x2 = "", discriminante = null, mensajeError = "Ingresa números válidos")
        }
        if (aVal == 0.0) {
            return state.copy(x1 = "", x2 = "", discriminante = null, mensajeError = "El valor 'a' no puede ser 0")
        }

        val disc = bVal * bVal - 4 * aVal * cVal
        return if (disc < 0) {
            state.copy(x1 = "", x2 = "", discriminante = disc, mensajeError = "Sin soluciones reales (Δ < 0)")
        } else {
            val r1 = (-bVal + sqrt(disc)) / (2 * aVal)
            val r2 = (-bVal - sqrt(disc)) / (2 * aVal)
            state.copy(
                x1 = "%.2f".format(r1),
                x2 = "%.2f".format(r2),
                discriminante = disc,
                mensajeError = null
            )
        }
    }
}
