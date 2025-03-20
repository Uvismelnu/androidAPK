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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.numel.abvcalculator.data.classifyPvCO2
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun ClassifyPvCO2(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txCO2venoso by remember { mutableStateOf(viewModel.sharedCO2v.value) }
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
            item {var isCO2venosoError by remember { mutableStateOf(false) }
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
        Button(
            onClick = {
                val pvco2Value = txCO2venoso.toDoubleOrNull()
                if (pvco2Value != null) {
                viewModel.updateTxCO2venoso(classifyPvCO2(pvco2Value)) // Update the ViewModel with the first TextField's value

                navController.popBackStack()}
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