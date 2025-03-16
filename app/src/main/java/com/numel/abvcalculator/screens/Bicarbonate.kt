package com.numel.abvcalculator.screens

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
import com.numel.abvcalculator.data.classifyBicarbonate
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun Bicarbonate(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txHCO3 by rememberSaveable { mutableStateOf("") }

    val maxLength = 6

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
            item {var isHCO3Error by remember { mutableStateOf(false) }
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
                    //.navigationBarsWithImePadding(),
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
            }
        }
        Button(
            onClick = {
                viewModel.updateTxHCO3(classifyBicarbonate(txHCO3.toDouble())) // Update the ViewModel with the first TextField's value

                navController.popBackStack()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Guardar y volver")
        }
    }

}