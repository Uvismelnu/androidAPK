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
import com.numel.abvcalculator.data.classifyPaO2
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun ClassifyPaO2(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {
    var txPO2arterial by remember { mutableStateOf(viewModel.sharedPO2a.value) }
    val context = LocalContext.current // Obtiene el contexto actual

    val maxLength = 6

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
            }
        }
        Button(
            onClick = {
                val po2Value = txPO2arterial.toDoubleOrNull()
                if (po2Value != null) {
                viewModel.updateTxPO2arterial(classifyPaO2(po2Value)) // Update the ViewModel with the first TextField's value

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