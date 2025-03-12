package com.numel.abvcalculator.screens

import MainViewModel
import ScreenData
import ScreenData1
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.ln
import kotlin.math.pow

@Composable
fun OxygenConsump(navController: NavController,
                  screenData: ScreenData,
                  screenData1: ScreenData1,
                  viewModel: MainViewModel) {

    val weight = remember { mutableStateOf("") }
    val height = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val heartRate = remember { mutableStateOf("") }
    val vO2 = remember { mutableStateOf("") }


    val bSA = remember { mutableStateOf("") }
    val selectedGender = remember { mutableStateOf(Gender.MALE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Row {
            RadioButton(
                selected = selectedGender.value == Gender.MALE,
                onClick = { selectedGender.value = Gender.MALE }
            )
            Text("Masculino")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedGender.value == Gender.FEMALE,
                onClick = { selectedGender.value = Gender.FEMALE }
            )
            Text("Femenino")
        }
        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = weight.value,
            onValueChange = { weight.value = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = height.value,
            onValueChange = { height.value = it },
            label = { Text("Talla (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = age.value,
            onValueChange = { age.value = it },
            label = { Text("Edad (años)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = heartRate.value,
            onValueChange = { heartRate.value = it },
            label = { Text("FC (lpm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        OutlinedButton({
            val k = if (selectedGender.value == Gender.MALE) 11.49 else 17.04
            val weightValue = weight.value.toDoubleOrNull() ?: 0.0
            val heightValue = height.value.toDoubleOrNull() ?: 0.0
            val ageValue = age.value.toDoubleOrNull() ?: 0
            val fc = heartRate.value.toIntOrNull() ?: 0
            bSA.value= ((0.007184* heightValue.pow(0.725)) * (weightValue.pow(0.425))).toString()
            vO2.value=(bSA.value.toDouble() * (138.1-(k * ln(ageValue.toDouble()))+(0.378 * fc.toDouble()))).toString()

            viewModel.updateOxygenConsump("BSA: ${"%.2f".format(bSA.value.toDouble())}" + " m2." + " \nVO2: ${"%.2f".format(vO2.value.toDouble())}" + " mL/min.")
            navController.popBackStack()

        }) {
            Text("Calcular y volver")
        }

    }
}
enum class Gender { MALE, FEMALE }

