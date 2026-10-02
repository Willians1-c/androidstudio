package com.upchiapas.kt_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.upchiapas.kt_template.CalculadoraCu.CalculadoraCuaPage
import com.upchiapas.kt_template.ui.theme.Kt_templateTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    // Inyectamos o instanciamos el ViewModel
    private val viewModel: CalculadoraCuaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kt_templateTheme {
                // Mandamos a llamar directamente tu pantalla pasando el ViewModel
                CalculadoraCuaPage(viewModel = viewModel)
            }
        }
    }
}
