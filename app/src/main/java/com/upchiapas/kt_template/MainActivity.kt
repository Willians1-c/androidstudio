package com.upchiapas.kt_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.kt_template.CalculadoraCu.CalculadoraCuaPage
import com.upchiapas.kt_template.CalculadoraCu.CalculadoraCuaViewModel
import com.upchiapas.kt_template.ui.theme.Kt_templateTheme // Ajusta según el nombre del paquete de tu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kt_templateTheme {
                // Instancia el ViewModel dentro del contexto de Compose
                val viewModel: CalculadoraCuaViewModel = viewModel()

                // Muestra la vista de la calculadora
                CalculadoraCuaPage(viewModel = viewModel)
            }
        }
    }
}