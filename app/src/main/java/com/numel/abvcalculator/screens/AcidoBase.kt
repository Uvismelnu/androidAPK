package com.numel.abvcalculator.screens

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


@Composable
fun AcidoBase(navController: NavController, screenData: ScreenData, screenData1: ScreenData1, viewModel: MainViewModel) {
    var newText1 by remember { mutableStateOf(screenData.text) }
    var txPharterial by rememberSaveable { mutableStateOf("") }
    var txCO2arterial by rememberSaveable { mutableStateOf("") }
    var txHCO3 by rememberSaveable { mutableStateOf("") }
    var txNa by rememberSaveable { mutableStateOf("") }
    var txK by rememberSaveable { mutableStateOf("") }
    var txCl by rememberSaveable { mutableStateOf("") }
    val maxLength = 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add padding for better spacing
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
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)

        ) {
            item {
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
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txPharterial = sanitizedValue
                        }
                        val pharterialValue = sanitizedValue.toDoubleOrNull()
                        isPharterialError =
                            pharterialValue == null || pharterialValue < 6.7 || pharterialValue > 8.2
                    },
                    label = { Text(text = "Ph", color = Color.White, fontSize = 12.sp) },
                    placeholder = {
                        Text(
                            text = "arterial",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .weight(2F)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = Color.White
                    ),
                    isError = isPharterialError, // Show error state
                    supportingText = {
                        if (isPharterialError) {
                            Text(
                                text = "Valor entre 6.7 y 8.2",
                                color = Color.White.copy(alpha = blinkAlpha) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)
                )


                Spacer(modifier = Modifier.size(5.dp))
                var isCO2arterialError by remember { mutableStateOf(false) }
                val blinkAlphaCO2arterial by animateFloatAsState(
                    targetValue = if (isCO2arterialError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txCO2arterial,
                    onValueChange = { newValue ->
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txCO2arterial = sanitizedValue
                        }
                        val co2ArterialValue = sanitizedValue.toDoubleOrNull()
                        isCO2arterialError =
                            co2ArterialValue == null || co2ArterialValue < 2.0 || co2ArterialValue > 300.0
                    },
                    label = {
                        Text(
                            text = "CO2",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    },
                    placeholder = {
                        Text(
                            text = "arterial",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.LightGray)
                        .weight(2F)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor = Color.Black
                    ),
                    isError = isCO2arterialError, // Show error state
                    supportingText = {
                        if (isCO2arterialError) {
                            Text(
                                text = "Valor entre 2.0 y 300.0",
                                color = Color.Black.copy(alpha = blinkAlphaCO2arterial) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )

            }
        }
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)

        ) {
            item {
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
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txHCO3 = sanitizedValue
                        }
                        val hco3Value = sanitizedValue.toDoubleOrNull()
                        isHCO3Error = hco3Value == null || hco3Value < 1.0 || hco3Value > 50.0
                    },
                    label = {
                        Text(
                            text = "HCO3",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .weight(2F)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = Color.White
                    ),
                    isError = isHCO3Error, // Show error state
                    supportingText = {
                        if (isHCO3Error) {
                            Text(
                                text = "Valor entre 1.0 y 50.0",
                                color = Color.White.copy(alpha = blinkAlphaHCO3) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )
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
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txNa = sanitizedValue

                        }
                        val naValue = sanitizedValue.toDoubleOrNull()
                        isNaError = naValue == null || naValue < 90.0 || naValue > 220.0
                    },
                    label = {
                        Text(
                            text = "Na",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .weight(2F)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = Color.White
                    ),
                    isError = isNaError, // Show error state
                    supportingText = {
                        if (isNaError) {
                            Text(
                                text = "Valor entre 90.0 y 220.0",
                                color = Color.White.copy(alpha = blinkAlphaNa) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)

                )

            }
        }
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
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txK = sanitizedValue
                        }
                        val kValue = sanitizedValue.toDoubleOrNull()
                        isKError = kValue == null || kValue < 0.5 || kValue > 10.0
                    },
                    label = { Text(text = "K", color = Color.Black, fontSize = 12.sp) },


                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                    modifier = Modifier
                        .background(Color.LightGray)
                        .weight(2F)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        cursorColor = Color.Black
                    ),
                    isError = isKError, // Show error state
                    supportingText = {
                        if (isKError) {
                            Text(
                                text = "Valor entre 0.5 y 10.0",
                                color = Color.Black.copy(alpha = blinkAlphaK) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )
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
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txCl = sanitizedValue
                        }
                        val clValue = sanitizedValue.toDoubleOrNull()
                        isClError = clValue == null || clValue < 60.0 || clValue > 250.0
                    },
                    label = {
                        Text(
                            text = "Cl",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .weight(2F)
                        .padding(horizontal = 2.dp, vertical = 2.dp)
                        .width(120.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = Color.White
                    ),
                    isError = isClError, // Show error state
                    supportingText = {
                        if (isClError) {
                            Text(
                                text = "Valor entre 60.0 y 250.0",
                                color = Color.White.copy(alpha = blinkAlphaCl) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)

                )

            }
        }


        Button(
            onClick = {
                // Validar que todos los campos tengan valores numéricos
                val phVal = txPharterial.toDoubleOrNull()
                val co2Val = txCO2arterial.toDoubleOrNull()
                val hco3Val = txHCO3.toDoubleOrNull()
                val naVal = txNa.toDoubleOrNull()
                val kVal = txK.toDoubleOrNull()
                val clVal = txCl.toDoubleOrNull()

                if (phVal != null && co2Val != null && hco3Val != null && naVal != null && kVal != null && clVal != null) {
                    viewModel.updateText1(acidobase(hco3Val, co2Val, phVal, naVal, kVal, clVal))
                    viewModel.updateTxPharterial(txPharterial)
                    viewModel.updateTxCO2arterial(txCO2arterial)
                    viewModel.isScreen1Visible = true
                    navController.popBackStack()
                } else {
                    // Mostrar error al usuario (puedes agregar un Toast o Snackbar)
                    //Toast.makeText("Por favor, ingrese valores numéricos válidos en todos los campos.", Toast.LENGTH_SHORT).show()
                }
            }) {
            Text("Guardar y volver")
        }
    }
}
