package com.upchiapas.kt_template.CalculadoraCu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CalculadoraCuaPage(
    viewModel: CalculadoraCuaViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Calculadora Cuadrática", style = MaterialTheme.typography.headlineMedium)

            // Ecuación
            Text("Ecuación: ${uiState.a.ifBlank { "a" }}x² + ${uiState.b.ifBlank { "b" }}x + ${uiState.c.ifBlank { "c" }} = 0")

            // Inputs
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = uiState.a,
                    onValueChange = { viewModel.onCoeficienteAChanged(it) },
                    label = { Text("a") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.b,
                    onValueChange = { viewModel.onCoeficienteBChanged(it) },
                    label = { Text("b") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.c,
                    onValueChange = { viewModel.onCoeficienteCChanged(it) },
                    label = { Text("c") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            // Resultados
            uiState.mensajeError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            } ?: run {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("X1: ${uiState.x1.ifEmpty { "-" }}", fontWeight = FontWeight.Bold)
                    Text("X2: ${uiState.x2.ifEmpty { "-" }}", fontWeight = FontWeight.Bold)
                }
                uiState.discriminante?.let {
                    Text("Discriminante (Δ): ${"%.2f".format(it)}")
                }
            }

            // Procedimiento
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.mostrarProcedimiento,
                    onCheckedChange = { viewModel.onToggleProcedimiento() }
                )
                Text("Mostrar procedimiento")
            }

            if (uiState.mostrarProcedimiento) {
                Column {
                    Text("Fórmula: x = (-b ± √(b² - 4ac)) / (2a)")
                    Text("Sustitución: x = (-(${uiState.b}) ± √((${uiState.b})² - 4(${uiState.a})(${uiState.c}))) / (2(${uiState.a}))")
                    Text("Δ = ${uiState.discriminante ?: "-"}")
                }
            }

            Button(onClick = { viewModel.resetearCampos() }) {
                Text("Limpiar")
            }
        }
    }
}
