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
import com.numel.abvcalculator.data.calcium
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun Calcium(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    // Ca total sérico en mmol/L (rango normal 2.10–2.55)
    // Si el laboratorio reporta en mg/dL → dividir por 4
    // Si el laboratorio reporta en mEq/L → dividir por 2
    var txCa        by remember { mutableStateOf("") }
    // Albúmina en g/dL: necesaria para corrección de Payne
    // Si se omite se asume 4.0 g/dL (normal)
    var txAlbumina  by remember { mutableStateOf("") }
    // Calcio iónico en mmol/L: rango normal 1.15–1.30
    // Tiene prioridad diagnóstica sobre el calcio total
    // Si el laboratorio reporta en mg/dL → dividir por 4
    var txCaIonico  by remember { mutableStateOf("") }

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
            text = "Calcio sérico",
            color = Color.Black,
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Ingresar Ca total en mmol/L",
            color = Color.Black,
            fontSize = 12.sp
        )
        Text(
            text = "(mg/dL ÷ 4  |  mEq/L ÷ 2)",
            color = Color.DarkGray,
            fontSize = 11.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(8.dp))

        // ── Campo Ca total (obligatorio) ───────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                var isCaError by remember { mutableStateOf(false) }
                val blinkAlphaCa by animateFloatAsState(
                    targetValue = if (isCaError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txCa,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) txCa = sanitized
                        val v = sanitized.toDoubleOrNull()
                        isCaError = v == null || v < 0.5 || v > 5.0
                    },
                    label       = { Text("Ca total",  color = Color.Black, fontSize = 12.sp) },
                    placeholder = { Text("mmol/L",    color = Color.Black, fontSize = 12.sp) },
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
                    isError = isCaError,
                    supportingText = {
                        if (isCaError) Text(
                            text  = "Valor entre 0.5 y 5.0 mmol/L",
                            color = Color.Black.copy(alpha = blinkAlphaCa)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Campos opcionales (mejoran el diagnóstico)",
            color = Color.Black,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))

        // ── Fila: Albúmina y Ca iónico (opcionales) ───────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                // Albúmina
                var isAlbuminaError by remember { mutableStateOf(false) }
                val blinkAlbumina by animateFloatAsState(
                    targetValue = if (isAlbuminaError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txAlbumina,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) txAlbumina = sanitized
                        val v = sanitized.toDoubleOrNull()
                        isAlbuminaError = sanitized.isNotBlank() && (v == null || v < 0.5 || v > 7.0)
                    },
                    label       = { Text("Albúmina g/dL", color = Color.Black, fontSize = 11.sp) },
                    placeholder = { Text("def: 4.0",      color = Color.Black, fontSize = 11.sp) },
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
                    isError = isAlbuminaError,
                    supportingText = {
                        if (isAlbuminaError) Text(
                            text  = "0.5–7.0 g/dL",
                            color = Color.Black.copy(alpha = blinkAlbumina)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(6.dp))

                // Ca iónico
                var isCaIonicoError by remember { mutableStateOf(false) }
                val blinkCaIonico by animateFloatAsState(
                    targetValue = if (isCaIonicoError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txCaIonico,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) txCaIonico = sanitized
                        val v = sanitized.toDoubleOrNull()
                        isCaIonicoError = sanitized.isNotBlank() && (v == null || v < 0.2 || v > 3.0)
                    },
                    label       = { Text("Ca²⁺ iónico", color = Color.Black, fontSize = 11.sp) },
                    placeholder = { Text("mmol/L",      color = Color.Black, fontSize = 11.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(130.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor        = Color.Black
                    ),
                    isError = isCaIonicoError,
                    supportingText = {
                        if (isCaIonicoError) Text(
                            text  = "0.2–3.0 mmol/L",
                            color = Color.Black.copy(alpha = blinkCaIonico)
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
                val caVal = txCa.toDoubleOrNull()

                if (caVal == null) {
                    Toast.makeText(
                        context,
                        "Ingrese un valor numérico válido para el Ca total.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                // Albúmina: default 4.0 si no se ingresa o está fuera de rango
                val albuminaVal = txAlbumina.toDoubleOrNull()
                    ?.takeIf { it in 0.5..7.0 }
                    ?: 4.0

                // Ca iónico: null si no se ingresa (campo opcional)
                val caIonicoVal = txCaIonico.toDoubleOrNull()
                    ?.takeIf { it in 0.2..3.0 }

                // Llamada a calcium() con los 3 parámetros
                val resultado = calcium(
                    ca        = caVal,
                    albumina  = albuminaVal,
                    caIonico  = caIonicoVal
                )

                viewModel.updateTxCa(resultado)
                navController.popBackStack()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Calcular y volver")
        }
    }
}