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
import com.numel.abvcalculator.data.chlorine
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun Chlorine(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txCl by remember { mutableStateOf(viewModel.sharedCl.value) }
    // Na: campo opcional. Permite calcular el Cl corregido por Na.
    // Cl_corregido = Cl_medido × (140 / Na_real)
    // Si el Cl corregido es normal, la discloremia es proporcional a la natremia,
    // no un trastorno primario del Cl.
    // Ref: Berend K et al. Eur J Intern Med 2012; Nagami GT. Nefrología 2016.
    var txNa by remember { mutableStateOf(viewModel.sharedNa.value) }

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
            text = "Cloro sérico",
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

        // ── Campo Cl ───────────────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                var isClError by remember { mutableStateOf(false) }
                val blinkAlphaCl by animateFloatAsState(
                    targetValue = if (isClError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txCl,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) {
                            txCl = sanitized
                            viewModel.updateSharedCl(txCl)
                        }
                        val v = sanitized.toDoubleOrNull()
                        isClError = v == null || v < 60.0 || v > 250.0
                    },
                    label       = { Text("Cl sérico",  color = Color.White, fontSize = 12.sp) },
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
                    isError = isClError,
                    supportingText = {
                        if (isClError) Text(
                            text  = "Valor entre 60.0 y 250.0",
                            color = Color.White.copy(alpha = blinkAlphaCl)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Campo opcional (permite calcular Cl corregido por Na)",
            color = Color.Black,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))

        // ── Campo Na (opcional) ────────────────────────────────────────────
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
                        isNaError = sanitized.isNotBlank() && (v == null || v < 90.0 || v > 220.0)
                    },
                    label       = { Text("Na sérico",  color = Color.DarkGray, fontSize = 11.sp) },
                    placeholder = { Text("Opcional / mEq/L", color = Color.DarkGray, fontSize = 10.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(180.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor        = Color.Black
                    ),
                    isError = isNaError,
                    supportingText = {
                        if (isNaError) Text(
                            text  = "Valor entre 90.0 y 220.0",
                            color = Color.Black.copy(alpha = blinkAlphaNa)
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
                val clVal = txCl.toDoubleOrNull()
                // Na es opcional: si está vacío o inválido no se pasa
                val naVal = txNa.toDoubleOrNull()?.takeIf { it in 90.0..220.0 }

                if (clVal == null) {
                    Toast.makeText(
                        context,
                        "Ingrese un valor numérico válido para el Cl.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                // chlorine() acepta na como parámetro opcional.
                // Si naVal es null se omite y no se calcula el Cl corregido.
                val resultado = chlorine(cl = clVal, na = naVal)

                viewModel.updateTxCl(resultado)
                navController.popBackStack()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Calcular y volver")
        }
    }
}