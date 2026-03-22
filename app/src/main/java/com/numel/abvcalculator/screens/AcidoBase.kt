package com.numel.abvcalculator.screens

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.numel.abvcalculator.data.acidobase
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

// Valor de albúmina de referencia (se usa si el usuario no ingresa el campo)
private const val ALBUMINA_DEFAULT = 4.0

@Composable
fun AcidoBase(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var newText1        by remember { mutableStateOf(screenData.text) }
    var txPharterial    by remember { mutableStateOf(viewModel.sharedPHa.value) }
    var txCO2arterial   by remember { mutableStateOf(viewModel.sharedCO2a.value) }
    var txHCO3          by remember { mutableStateOf(viewModel.sharedHCO3.value) }
    var txNa            by remember { mutableStateOf(viewModel.sharedNa.value) }
    var txK             by remember { mutableStateOf(viewModel.sharedK.value) }
    var txCl            by remember { mutableStateOf(viewModel.sharedCl.value) }
    // Albúmina: campo opcional. Si se deja vacío se usa 4.0 g/dL (normal)
    var txAlbumina      by remember { mutableStateOf("") }

    val maxLength = 6
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = newText1,
            onValueChange = { newText1 = it },
            label = { Text("Editar texto 1") },
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = "Ingrese todos los datos",
            color = Color.White,
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic
        )

        // ── Fila 1: pH y pCO2 ──────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                // pH
                var isPharterialError by remember { mutableStateOf(false) }
                val blinkAlpha by animateFloatAsState(
                    targetValue = if (isPharterialError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txPharterial,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) {
                            txPharterial = sanitized
                            viewModel.updateSharedPHa(txPharterial)
                        }
                        val v = sanitized.toDoubleOrNull()
                        isPharterialError = v == null || v < 6.7 || v > 8.2
                    },
                    label       = { Text("pH",       color = Color.White, fontSize = 12.sp) },
                    placeholder = { Text("arterial", color = Color.White, fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor        = Color.White
                    ),
                    isError = isPharterialError,
                    supportingText = {
                        if (isPharterialError) Text(
                            text  = "Valor entre 6.7 y 8.2",
                            color = Color.White.copy(alpha = blinkAlpha)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.size(5.dp))

                // pCO2
                var isCO2arterialError by remember { mutableStateOf(false) }
                val blinkAlphaCO2 by animateFloatAsState(
                    targetValue = if (isCO2arterialError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txCO2arterial,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) {
                            txCO2arterial = sanitized
                            viewModel.updateSharedCO2a(txCO2arterial)
                        }
                        val v = sanitized.toDoubleOrNull()
                        isCO2arterialError = v == null || v < 2.0 || v > 300.0
                    },
                    label       = { Text("pCO2",    color = Color.Black, fontSize = 12.sp) },
                    placeholder = { Text("arterial", color = Color.Black, fontSize = 12.sp) },
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
                    isError = isCO2arterialError,
                    supportingText = {
                        if (isCO2arterialError) Text(
                            text  = "Valor entre 2.0 y 300.0",
                            color = Color.Black.copy(alpha = blinkAlphaCO2)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        // ── Fila 2: HCO3 y Na ──────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                // HCO3
                var isHCO3Error by remember { mutableStateOf(false) }
                val blinkAlphaHCO3 by animateFloatAsState(
                    targetValue = if (isHCO3Error) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txHCO3,
                    onValueChange = { newValue ->
                        val sanitized = newValue.replace(',', '.')
                        if (newValue.length <= maxLength) {
                            txHCO3 = sanitized
                            viewModel.updateSharedHCO3(txHCO3)
                        }
                        val v = sanitized.toDoubleOrNull()
                        isHCO3Error = v == null || v < 1.0 || v > 50.0
                    },
                    label = { Text("HCO3", color = Color.White, fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor        = Color.White
                    ),
                    isError = isHCO3Error,
                    supportingText = {
                        if (isHCO3Error) Text(
                            text  = "Valor entre 1.0 y 50.0",
                            color = Color.White.copy(alpha = blinkAlphaHCO3)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.size(5.dp))

                // Na
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
                    label = { Text("Na", color = Color.White, fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor   = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor        = Color.White
                    ),
                    isError = isNaError,
                    supportingText = {
                        if (isNaError) Text(
                            text  = "Valor entre 90.0 y 220.0",
                            color = Color.White.copy(alpha = blinkAlphaNa)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        // ── Fila 3: K y Cl ────────────────────────────────────────────────
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                // K
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
                    label = { Text("K", color = Color.Black, fontSize = 12.sp) },
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
                    isError = isKError,
                    supportingText = {
                        if (isKError) Text(
                            text  = "Valor entre 0.5 y 10.0",
                            color = Color.Black.copy(alpha = blinkAlphaK)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.size(5.dp))

                // Cl
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
                    label = { Text("Cl", color = Color.White, fontSize = 12.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
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

        // ── Fila 4: Albúmina (opcional) ────────────────────────────────────
        // Si se deja vacío se asume 4.0 g/dL (normal).
        // Es importante para la corrección del Anion Gap en pacientes desnutridos,
        // cirróticos, nefróticos o en UCI.
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            item {
                var isAlbuminaError by remember { mutableStateOf(false) }
                val blinkAlphaAlb by animateFloatAsState(
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
                        // Solo error si el campo tiene algo y está fuera de rango
                        isAlbuminaError = sanitized.isNotBlank() && (v == null || v < 0.5 || v > 7.0)
                    },
                    label       = { Text("Albúmina g/dL", color = Color.Black, fontSize = 12.sp) },
                    placeholder = { Text("Opcional (def: 4.0)", color = Color.Black, fontSize = 10.sp) },
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
                    isError = isAlbuminaError,
                    supportingText = {
                        if (isAlbuminaError) Text(
                            text  = "Valor entre 0.5 y 7.0",
                            color = Color.Black.copy(alpha = blinkAlphaAlb)
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        // ── Botón calcular ──────────────────────────────────────────────────
        Button(
            onClick = {
                val phVal      = txPharterial.toDoubleOrNull()
                val co2Val     = txCO2arterial.toDoubleOrNull()
                val hco3Val    = txHCO3.toDoubleOrNull()
                val naVal      = txNa.toDoubleOrNull()
                val kVal       = txK.toDoubleOrNull()
                val clVal      = txCl.toDoubleOrNull()
                // Albúmina: si está vacío o inválido usa 4.0 por defecto
                val albuminaVal = txAlbumina.toDoubleOrNull()
                    ?.takeIf { it in 0.5..7.0 }
                    ?: ALBUMINA_DEFAULT

                if (phVal != null && co2Val != null && hco3Val != null
                    && naVal != null && kVal != null && clVal != null
                ) {
                    // ── Llamada correcta a la nueva firma de acidobase() ──
                    // Los argumentos son nombrados para evitar errores de orden.
                    // .textoCompleto() convierte AcidoBaseResultado → String.
                    val resultadoTexto = acidobase(
                        pH       = phVal,
                        pco2     = co2Val,
                        hco3     = hco3Val,
                        na       = naVal,
                        cl       = clVal,
                        k        = kVal,
                        albumina = albuminaVal
                    ).textoCompleto()

                    viewModel.updateText1(resultadoTexto)
                    viewModel.updateTxPharterial(txPharterial)
                    viewModel.updateTxCO2arterial(txCO2arterial)

                    // Lógica de navegación sin cambios
                    when {
                        co2Val < 100.0 && phVal in 7.0..8.24 -> {
                            viewModel.isScreen1Visible                  = true
                            viewModel.isScreenCO2100_160_H_0_100Visible = false
                            viewModel.isScreenCO2100_160_H_100_160Visible = false
                            viewModel.isScreenCO2_0_100_h100_160Visible  = false
                        }
                        co2Val in 100.0..160.0 && phVal in 7.0..8.24 -> {
                            viewModel.isScreenCO2100_160_H_0_100Visible   = true
                            viewModel.isScreenCO2100_160_H_100_160Visible = false
                            viewModel.isScreenCO2_0_100_h100_160Visible   = false
                            viewModel.isScreen1Visible                     = false
                        }
                        co2Val in 100.0..160.0 && phVal in 6.8..7.0 -> {
                            viewModel.isScreenCO2100_160_H_100_160Visible = true
                            viewModel.isScreen1Visible                     = false
                            viewModel.isScreenCO2_0_100_h100_160Visible   = false
                            viewModel.isScreenCO2100_160_H_0_100Visible   = false
                        }
                        co2Val in 0.0..100.0 && phVal in 6.8..7.0 -> {
                            viewModel.isScreenCO2_0_100_h100_160Visible   = true
                            viewModel.isScreen1Visible                     = false
                            viewModel.isScreenCO2100_160_H_0_100Visible   = false
                            viewModel.isScreenCO2100_160_H_100_160Visible = false
                        }
                    }

                    navController.popBackStack()

                } else {
                    Toast.makeText(
                        context,
                        "Ingrese valores numéricos válidos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text("Guardar y volver")
        }
    }
}