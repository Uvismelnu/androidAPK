package com.numel.abvcalculator.screens

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.numel.abvcalculator.data.sodium
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun Sodium(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txNa       by remember { mutableStateOf(viewModel.sharedNa.value) }
    // Glucemia: necesaria para corrección de Na en hiperglucemia
    // Na corregido = Na medido + 1.6 × [(glucosa mg/dL − 100) / 100]
    var txGlucosa  by remember { mutableStateOf("") }
    // Peso: necesario para calcular déficit de agua libre en hipernatremia
    var txPeso     by remember { mutableStateOf("") }
    // Sexo: afecta el agua corporal total (ACT = 0.6 × peso ♂; 0.5 × peso ♀)
    var esMujer    by remember { mutableStateOf(false) }

    val maxLength = 6
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sodio sérico",
            color = Color.Black,
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ingrese los datos disponibles",
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        // ── Campo Na ──────────────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                var isNaError by remember { mutableStateOf(false) }
                val blinkAlphaNa by animateFloatAsState(
                    targetValue = if (isNaError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txNa,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) {
                            txNa = sanitized
                            viewModel.updateSharedNa(txNa)
                        }
                        val v = sanitized.toDoubleOrNull()
                        isNaError = v == null || v < 90.0 || v > 220.0
                    },
                    label       = { Text("Na sérico",  color = Color.White, fontSize = 12.sp) },
                    placeholder = { Text("mEq/L",      color = Color.White, fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(140.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor        = Color.White
                    ),
                    isError = isNaError,
                    supportingText = {
                        if (isNaError) Text(
                            text  = "Valor entre 90 y 220",
                            color = Color.White.copy(alpha = blinkAlphaNa)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Campos opcionales (mejoran el cálculo)",
            color = Color.Black,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))

        // ── Glucemia y Peso en la misma fila ──────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                // Glucemia
                var isGlucosaError by remember { mutableStateOf(false) }
                val blinkGlucosa by animateFloatAsState(
                    targetValue = if (isGlucosaError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txGlucosa,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) txGlucosa = sanitized
                        val v = sanitized.toDoubleOrNull()
                        isGlucosaError = sanitized.isNotBlank() && (v == null || v < 10.0 || v > 2000.0)
                    },
                    label       = { Text("Glucosa",    color = Color.Black, fontSize = 11.sp) },
                    placeholder = { Text("mg/dL",      color = Color.Black, fontSize = 11.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor        = Color.Black
                    ),
                    isError = isGlucosaError,
                    supportingText = {
                        if (isGlucosaError) Text(
                            text  = "10–2000 mg/dL",
                            color = Color.Black.copy(alpha = blinkGlucosa)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(6.dp))

                // Peso
                var isPesoError by remember { mutableStateOf(false) }
                val blinkPeso by animateFloatAsState(
                    targetValue = if (isPesoError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txPeso,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) txPeso = sanitized
                        val v = sanitized.toDoubleOrNull()
                        isPesoError = sanitized.isNotBlank() && (v == null || v < 1.0 || v > 300.0)
                    },
                    label       = { Text("Peso",  color = Color.Black, fontSize = 11.sp) },
                    placeholder = { Text("kg",    color = Color.Black, fontSize = 11.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(100.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor        = Color.Black
                    ),
                    isError = isPesoError,
                    supportingText = {
                        if (isPesoError) Text(
                            text  = "1–300 kg",
                            color = Color.Black.copy(alpha = blinkPeso)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        // ── Selector de sexo (afecta ACT) ─────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sexo biológico:", color = Color.Black, fontSize = 13.sp)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { esMujer = false },
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (!esMujer) Color.DarkGray else Color.LightGray
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("♂ Hombre", color = if (!esMujer) Color.White else Color.Black, fontSize = 12.sp)
            }
            Button(
                onClick = { esMujer = true },
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (esMujer) Color.DarkGray else Color.LightGray
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("♀ Mujer", color = if (esMujer) Color.White else Color.Black, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Botón calcular ─────────────────────────────────────────────────
        Button(
            onClick = {
                val naVal      = txNa.toDoubleOrNull()
                val glucosaVal = txGlucosa.toDoubleOrNull()  // puede ser null
                val pesoVal    = txPeso.toDoubleOrNull()     // puede ser null

                if (naVal == null) {
                    Toast.makeText(
                        context,
                        "Ingrese un valor numérico válido para el Na.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                // ── Corrección de Na por hiperglucemia (Katz / Hillier) ──
                // Fórmula de Hillier (más precisa):
                //   Na corr = Na medido + 1.6 × [(glucosa − 100) / 100]   para glucosa 100–400
                //   Na corr = Na medido + 2.4 × [(glucosa − 100) / 100]   para glucosa > 400
                // Ref: Hillier TA et al. Am J Med 1999; 106:399-403.
                val naCorregido: Double? = glucosaVal?.let { glu ->
                    if (glu > 100.0) {
                        val factor = if (glu <= 400.0) 1.6 else 2.4
                        naVal + factor * ((glu - 100.0) / 100.0)
                    } else null  // glucosa normal: Na medido = Na real
                }

                // ── Déficit de agua libre en hipernatremia ────────────────
                // DAL (L) = ACT × (Na / 140 − 1)
                // ACT = 0.6 × peso (♂) ; 0.5 × peso (♀) ; 0.45 en ancianos
                // Ref: Adrogué & Madias NEJM 2000.
                val deficitAguaLibre: Double? = if (naVal > 145.0 && pesoVal != null) {
                    val act = pesoVal * if (esMujer) 0.5 else 0.6
                    act * (naVal / 140.0 - 1.0)
                } else null

                // ── Construir texto resultado ─────────────────────────────
                val resultado = buildString {
                    append(sodium(naVal))   // clasificación principal

                    naCorregido?.let {
                        appendLine()
                        appendLine("── Corrección por hiperglucemia (Hillier) ──")
                        appendLine("   Glucosa: $glucosaVal mg/dL")
                        appendLine("   Na corregido: ${String.format("%.1f", it)} mEq/L")
                        if (it > naVal + 1.5) {
                            appendLine("   ⚠ El Na real es mayor que el medido.")
                            appendLine("     La hiponatremia puede ser artifactual.")
                        }
                    }

                    deficitAguaLibre?.let {
                        appendLine()
                        appendLine("── Déficit de agua libre ──")
                        appendLine("   Peso: $pesoVal kg  |  Sexo: ${if (esMujer) "♀" else "♂"}")
                        appendLine("   Déficit ≈ ${String.format("%.2f", it)} L")
                        appendLine("   Reponer en 48–72 h (máx 10–12 mEq/L/24 h).")
                        val volGluc5_24h = (it / 2.0) * 1000.0  // mitad en 24 h aprox
                        appendLine("   Orientativo: ~${volGluc5_24h.toInt()} mL de glucosado 5% en 24 h.")
                        appendLine("   Ajustar según Na de control cada 4–6 h.")
                    }
                }

                viewModel.updateTxNa(resultado)
                navController.popBackStack()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Calcular y volver")
        }
    }
}