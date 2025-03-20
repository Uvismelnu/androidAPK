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
import com.numel.abvcalculator.data.circulationMetabolism
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun CirculationMetabolism(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txPO2arterial by remember { mutableStateOf(viewModel.sharedPO2a.value) }
    var txCO2venoso by remember { mutableStateOf(viewModel.sharedCO2v.value) }
    var txCO2arterial by remember { mutableStateOf(viewModel.sharedCO2a.value) }
    var txSaO2 by remember { mutableStateOf(viewModel.sharedSO2a.value) }
    var txSvO2 by remember { mutableStateOf(viewModel.sharedSO2v.value) }
    var txHb by remember { mutableStateOf(viewModel.sharedHb.value) }
    val maxLength = 6
    val context = LocalContext.current // Obtiene el contexto actual

    Column(
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize()
            .padding(
                horizontal = 2.dp, vertical = 2.dp
            ), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Text(
            text = "Ingrese todos los datos",
            color = Color.Black,
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic
        )
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .align(Alignment.CenterHorizontally)

        ) {
            item {
                var isPO2arterialError by remember { mutableStateOf(false) }
                val blinkAlphaPO2arterial by animateFloatAsState(
                    targetValue = if (isPO2arterialError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txPO2arterial,
                    onValueChange = { newValue ->
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txPO2arterial = sanitizedValue
                            viewModel.updateSharedPO2a(txPO2arterial)

                        }
                        val pO2arterialValue = sanitizedValue.toDoubleOrNull()
                        isPO2arterialError =
                            pO2arterialValue == null || pO2arterialValue < 20.0 || pO2arterialValue > 500.0

                    },
                    label = {
                        Text(
                            text = "PO2",
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
                    isError = isPO2arterialError, // Show error state
                    supportingText = {
                        if (isPO2arterialError) {
                            Text(
                                text = "Valor entre 20.0 y 500.0",
                                color = Color.Black.copy(alpha = blinkAlphaPO2arterial) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )
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
                            viewModel.updateSharedCO2a(txCO2arterial)

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
            item {var isSaO2Error by remember { mutableStateOf(false) }
                val blinkAlphaSaO2 by animateFloatAsState(
                    targetValue = if (isSaO2Error) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txSaO2,
                    onValueChange = { newValue ->
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txSaO2 = sanitizedValue
                            viewModel.updateSharedSO2a(txSaO2)
                        }
                        val sao2Value = sanitizedValue.toDoubleOrNull()
                        isSaO2Error = sao2Value == null || sao2Value < 10 || sao2Value > 100.0
                    },
                    label = {
                        Text(
                            text = "SO2",
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
                    isError = isSaO2Error, // Show error state
                    supportingText = {
                        if (isSaO2Error) {
                            Text(
                                text = "Valor entre 10.0 y 100.0",
                                color = Color.Black.copy(alpha = blinkAlphaSaO2) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )
                var isCO2venosoError by remember { mutableStateOf(false) }
                val blinkAlphaCO2venoso by animateFloatAsState(
                    targetValue = if (isCO2venosoError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txCO2venoso,
                    onValueChange = { newValue ->
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txCO2venoso = sanitizedValue
                            viewModel.updateSharedCO2v(txCO2venoso)

                        }
                        val co2VenosoValue = sanitizedValue.toDoubleOrNull()
                        isCO2venosoError =
                            co2VenosoValue == null || co2VenosoValue < 0.1 || co2VenosoValue > 200.0
                    },
                    label = {
                        Text(
                            text = "CO2",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    placeholder = {
                        Text(
                            text = "venoso",
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
                    isError = isCO2venosoError, // Show error state
                    supportingText = {
                        if (isCO2venosoError) {
                            Text(
                                text = "Valor entre 0.1 y 200.0",
                                color = Color.White.copy(alpha = blinkAlphaCO2venoso) // Apply alpha animation
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
                var isSvO2Error by remember { mutableStateOf(false) }
                val blinkAlphaSvO2 by animateFloatAsState(
                    targetValue = if (isSvO2Error) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txSvO2,
                    onValueChange = { newValue ->
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txSvO2 = sanitizedValue
                            viewModel.updateSharedSO2v(txSvO2)
                        }
                        val svO2Value = sanitizedValue.toDoubleOrNull()
                        isSvO2Error = svO2Value == null || svO2Value < 10.0 || svO2Value > 100.0
                    },
                    label = {
                        Text(
                            text = "SO2",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    placeholder = {
                        Text(
                            text = "venosa",
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
                    isError = isSvO2Error, // Show error state
                    supportingText = {
                        if (isSvO2Error) {
                            Text(
                                text = "Valor entre 10.0 y 100.0",
                                color = Color.White.copy(alpha = blinkAlphaSvO2) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )
                Spacer(modifier = Modifier.size(5.dp))

                var isHbError by remember { mutableStateOf(false) }
                val blinkAlphaHb by animateFloatAsState(
                    targetValue = if (isHbError) 1f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                OutlinedTextField(
                    value = txHb,
                    onValueChange = { newValue ->
                        val sanitizedValue = newValue.replace(',', '.') // Replace comma with dot
                        if (newValue.length <= maxLength) {
                            txHb = sanitizedValue
                            viewModel.updateSharedHb(txHb)

                        }
                        val hbValue = sanitizedValue.toDoubleOrNull()
                        isHbError = hbValue == null || hbValue < 1.0 || hbValue > 25.0
                    },
                    label = {
                        Text(
                            text = "HB",
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
                    isError = isHbError, // Show error state
                    supportingText = {
                        if (isHbError) {
                            Text(
                                text = "Valor entre 1.0 y 25.0",
                                color = Color.Black.copy(alpha = blinkAlphaHb) // Apply alpha animation
                            )
                        }
                    },
                    shape = RoundedCornerShape(20.dp)


                )
            }
        }
        Button(
            onClick = {
                val pO2arterialValue = txPO2arterial.toDoubleOrNull()
                val co2ArterialValue = txCO2arterial.toDoubleOrNull()
                val co2VenosoValue = txCO2venoso.toDoubleOrNull()
                val hbValue = txHb.toDoubleOrNull()
                val sao2Value = txSaO2.toDoubleOrNull()
                val svO2Value = txSvO2.toDoubleOrNull()
                if (pO2arterialValue != null && co2ArterialValue != null && co2VenosoValue != null && hbValue != null && sao2Value != null && svO2Value != null) {
                    viewModel.updateCirMetab(
                        circulationMetabolism(
                            pO2arterialValue,
                            co2ArterialValue,
                            co2VenosoValue,
                            hbValue,
                            sao2Value,
                            svO2Value
                        )
                    ) // Update the ViewModel with the first TextField's value

                    navController.popBackStack()
                }
                else{
                    Toast.makeText(context, "Ingrese valores numéricos válidos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Guardar y volver")
        }
    }


}