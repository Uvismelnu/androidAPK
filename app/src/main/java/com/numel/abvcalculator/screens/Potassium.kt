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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.numel.abvcalculator.data.potassium
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun Potassium(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txK   by remember { mutableStateOf(viewModel.sharedK.value) }
    // pH: permite calcular la corrección transcellular de K por acidosis/alcalosis.
    // Por cada 0.1 de descenso del pH, el K sérico sube ~0.4–0.6 mEq/L (acidosis metabólica).
    // Ref: Adrogue HJ, Madias NE. Changes in plasma potassium concentration during
    //      acute acid-base disturbances. Am J Med 1981; 71(3):456-67.
    var txPH  by remember { mutableStateOf("") }

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
            text = "Potasio sérico",
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

        // ── Campo K ────────────────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                var isKError by remember { mutableStateOf(false) }
                val blinkAlphaK by animateFloatAsState(
                    targetValue = if (isKError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txK,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) {
                            txK = sanitized
                            viewModel.updateSharedK(txK)
                        }
                        val v = sanitized.toDoubleOrNull()
                        isKError = v == null || v < 0.5 || v > 10.0
                    },
                    label       = { Text("K sérico",  color = Color.Black, fontSize = 12.sp) },
                    placeholder = { Text("mEq/L",     color = Color.Black, fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(140.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor        = Color.Black
                    ),
                    isError = isKError,
                    supportingText = {
                        if (isKError) Text(
                            text  = "Valor entre 0.5 y 10.0",
                            color = Color.Black.copy(alpha = blinkAlphaK)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Campo opcional (mejora el análisis)",
            color = Color.Black,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))

        // ── Campo pH (opcional) ────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                var isPHError by remember { mutableStateOf(false) }
                val blinkAlphaPH by animateFloatAsState(
                    targetValue = if (isPHError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txPH,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) txPH = sanitized
                        val v = sanitized.toDoubleOrNull()
                        isPHError = sanitized.isNotBlank() && (v == null || v < 6.7 || v > 8.2)
                    },
                    label       = { Text("pH arterial",  color = Color.DarkGray, fontSize = 11.sp) },
                    placeholder = { Text("Opcional",     color = Color.DarkGray, fontSize = 11.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(160.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor        = Color.Black
                    ),
                    isError = isPHError,
                    supportingText = {
                        if (isPHError) Text(
                            text  = "pH entre 6.7 y 8.2",
                            color = Color.Black.copy(alpha = blinkAlphaPH)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Botón calcular ─────────────────────────────────────────────────
        Button(
            onClick = {
                val kVal  = txK.toDoubleOrNull()
                val phVal = txPH.toDoubleOrNull()  // puede ser null

                if (kVal == null) {
                    Toast.makeText(
                        context,
                        "Ingrese un valor numérico válido para el K.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                // ── Corrección transcellular de K por pH ──────────────────
                //
                // En acidosis metabólica: por cada 0.1 de descenso del pH,
                // el K sérico sube ~0.4–0.6 mEq/L (promedio 0.5).
                // El K "real" (si el pH fuera normal) sería más bajo.
                //
                // En alcalosis metabólica: por cada 0.1 de ascenso del pH,
                // el K sérico baja ~0.3–0.5 mEq/L.
                // El K "real" sería más alto que el medido.
                //
                // IMPORTANTE: esta corrección aplica principalmente a trastornos
                // METABÓLICOS. Los trastornos respiratorios tienen efecto menor
                // y más variable sobre el K.
                //
                // Ref: Adrogue HJ, Madias NE. Am J Med 1981; 71(3):456-67.
                //      Mount DB. Harrison's, 20th ed. Cap 49.

                val pH_NORMAL = 7.40
                val correccionPH: String? = phVal?.let { ph ->
                    val deltaph = ph - pH_NORMAL          // negativo en acidosis
                    // Por cada 0.1 de cambio en pH, K cambia ~0.5 en dirección opuesta
                    val deltaK  = -(deltaph / 0.1) * 0.5
                    val kCorregido = kVal - deltaK        // K que habría si pH = 7.40

                    buildString {
                        appendLine()
                        appendLine("── Corrección de K por pH (Adrogué & Madias) ──")
                        appendLine("   pH medido: $ph")
                        when {
                            ph < 7.35 -> {
                                appendLine("   Acidosis: el pH bajo eleva el K sérico artificialmente.")
                                appendLine("   K corregido a pH 7.40 ≈ ${String.format("%.2f", kCorregido)} mEq/L")
                                appendLine("   (El K real puede ser MENOR — riesgo de hipopotasemia")
                                appendLine("    al corregir la acidosis).")
                            }
                            ph > 7.45 -> {
                                appendLine("   Alcalosis: el pH alto enmascara hipopotasemia.")
                                appendLine("   K corregido a pH 7.40 ≈ ${String.format("%.2f", kCorregido)} mEq/L")
                                appendLine("   (El K real puede ser MAYOR de lo que aparenta.)")
                            }
                            else -> {
                                appendLine("   pH en rango normal: corrección transcellular mínima.")
                            }
                        }
                        appendLine("   NOTA: corrección orientativa solo para trastornos")
                        appendLine("   metabólicos. Confirmar con ionograma repetido.")
                    }
                }

                // ── Construir resultado final ─────────────────────────────
                val resultado = buildString {
                    append(potassium(kVal))
                    correccionPH?.let { append(it) }
                }

                viewModel.updateTxK(resultado)
                navController.popBackStack()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Calcular y volver")
        }
    }
}