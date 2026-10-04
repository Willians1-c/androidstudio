package com.upchiapas.kt_template.CalculadoraCu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CalculadoraCuaPage(
    viewModel: CalculadoraCuaViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    // Valida que a, b y c tengan al menos un carácter escrito
    val camposCompletos = uiState.a.isNotBlank() && uiState.b.isNotBlank() && uiState.c.isNotBlank()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Calculadora Cuadrática", style = MaterialTheme.typography.bodyMedium)

            // Ecuación
            Text("Ecuación: ${uiState.a.ifBlank { "a" }}x² + ${uiState.b.ifBlank { "b" }}x + ${uiState.c.ifBlank { "c" }} = 0")

            // Inputs
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = uiState.a,
                    onValueChange = { viewModel.onCoeficienteAChanged(it) },
                    label = { Text("a") },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.b,
                    onValueChange = { viewModel.onCoeficienteBChanged(it) },
                    label = { Text("b") },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.c,
                    onValueChange = { viewModel.onCoeficienteCChanged(it) },
                    label = { Text("c") },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            // Resultados
            uiState.mensajeError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            } ?: run {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("x1: ${uiState.x1.ifEmpty { "-" }}", fontWeight = FontWeight.Bold)
                    }
                    Box(modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("x2: ${uiState.x2.ifEmpty { "-" }}", fontWeight = FontWeight.Bold)
                    }
                }
                uiState.discriminante?.let {
                    Text("Discriminante (Δ): ${"%.2f".format(it)}")
                }
            }

            // Solo se dibuja el Checkbox y el paso a paso si los 3 campos están llenos
            if (camposCompletos) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = uiState.mostrarProcedimiento,
                        onCheckedChange = { viewModel.onToggleProcedimiento() }
                    )
                    Text("Mostrar procedimiento")
                }

                if (uiState.mostrarProcedimiento) {
                    Surface(
                        color = Color(0xFF1E1E1E),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val mathStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFFE8E8E8)
                            )
                            Text("Procedimiento :", style = mathStyle.copy(fontWeight = FontWeight.Bold, color = Color(0xFF81D4FA)))
                            Text("x = (-b ± √(b² - 4ac)) / 2a", style = mathStyle, fontWeight = FontWeight.Bold)
                            Text("x = (-(${uiState.b}) ± √((${uiState.b})² - 4(${uiState.a})(${uiState.c}))) / 2(${uiState.a})", style = mathStyle)
                            Text("Δ = b² - 4ac = ${uiState.discriminante ?: "-"}", style = mathStyle)
                            Text("x₁ = ${uiState.x1.ifEmpty { "-" }}  |  x₂ = ${uiState.x2.ifEmpty { "-" }}", style = mathStyle, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Button(onClick = { viewModel.resetearCampos() }) {
                Text("Limpiar")
            }
        }
    }
}
