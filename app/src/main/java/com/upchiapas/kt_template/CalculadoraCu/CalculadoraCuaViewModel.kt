package com.upchiapas.kt_template.CalculadoraCu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalculadoraUiState(
    val a: String="",
    val b:String="",
    val c: String="",
    val x1:String="",
    val x2:String="",
    val discriminante: Double? = null,
    val mensajeError:String?=null
)
class CalculadoraCuaViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(CalculadoraUiState())
    val uiState: StateFlow<CalculadoraUiState> = _uiState.asStateFlow()

}
fun onCoeficienteAChanged(nuevovalor: String){

}
fun onCoeficienteBChanged(nuevovalor: String){

}
fun onCoeficienteCChanged(nuevovalor: String) {

}
fun resetearCampos(){

}