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
import com.numel.abvcalculator.data.classifySaO2
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel


@Composable
fun ClassifySaO2(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txSaO2 by remember { mutableStateOf(viewModel.sharedSO2a.value) }
    val maxLength = 6
    val context = LocalContext.current // Obtiene el contexto actual

    Column(
        modifier = Modifier
            .background(Color.Cyan)
            //.weight(2F)

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
            }
        }
        Button(
            onClick = {
                val sao2Value = txSaO2.toDoubleOrNull()
                if (sao2Value != null) {
                viewModel.updateTxSaO2(classifySaO2(sao2Value)) // Update the ViewModel with the first TextField's value

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

