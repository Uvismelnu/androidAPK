package com.numel.abvcalculator.screens

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.navigation.ScreenData1
import com.numel.abvcalculator.viewModel.Gender
import com.numel.abvcalculator.viewModel.MainViewModel
import kotlin.math.ln
import kotlin.math.pow

@Composable
fun OxygenConsump(
    navController: NavController,
    screenData: ScreenData,
    screenData1: ScreenData1,
    viewModel: MainViewModel
) {

    val weight = remember { mutableStateOf(viewModel.sharedWeight.value) }
    val height = remember { mutableStateOf(viewModel.sharedHeight.value) }
    val age = remember { mutableStateOf(viewModel.sharedAge.value) }
    val heartRate = remember { mutableStateOf(viewModel.sharedHeartRate.value) }
    val vO2 = remember { mutableStateOf(viewModel.sharedVO2.value) }


    val bSA = remember { mutableStateOf(viewModel.sharedBSA.value) }
    val selectedGender = remember { mutableStateOf(viewModel.selectedGender.value) }
    val context = LocalContext.current // Obtiene el contexto actual

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
                onClick = { selectedGender.value = Gender.MALE
                viewModel.updateSelectedGender(Gender.MALE) }
            )
            Text("Masculino")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedGender.value == Gender.FEMALE,
                onClick = { selectedGender.value = Gender.FEMALE
                viewModel.updateSelectedGender(Gender.FEMALE) }
            )
            Text("Femenino")
        }
        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = weight.value,
            onValueChange = { weight.value = it
                            viewModel.updateSharedWeight(it)},
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = height.value,
            onValueChange = { height.value = it
                            viewModel.updateSharedHeight(it)},
            label = { Text("Talla (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = age.value,
            onValueChange = { age.value = it
                            viewModel.updateSharedAge(it)},
            label = { Text("Edad (años)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = heartRate.value,
            onValueChange = { heartRate.value = it
                            viewModel.updateSharedHeartRate(it)},
            label = { Text("FC (lpm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        OutlinedButton({
            val k = if (selectedGender.value == Gender.MALE) 11.49 else 17.04
            val weightValue = weight.value.toDoubleOrNull() ?: 0.0
            val heightValue = height.value.toDoubleOrNull() ?: 0.0
            val ageValue = age.value.toDoubleOrNull() ?: 0.0
            val fc = heartRate.value.toIntOrNull() ?: 0

            if(selectedGender.value==null){
                Toast.makeText(context, "Seleccione un género.", Toast.LENGTH_SHORT).show()

            }

            else if (weightValue > 0 && heightValue > 0 && ageValue > 0 && fc > 0 && selectedGender.value != null) {
                // Calculate BSA
                val bsa = 0.007184 * heightValue.pow(0.725) * weightValue.pow(0.425)
                viewModel.updateSharedBSA(bsa.toString())

                // Calculate VO2
                val vo2 = bsa * (138.1 - (k * ln(ageValue)) + (0.378 * fc))
                viewModel.updateSharedVO2(vo2.toString())

                // Update LiveData or State with formatted values
                bSA.value = "%.2f".format(bsa)
                vO2.value = "%.2f".format(vo2)

                viewModel.updateOxygenConsump(
                    "BSA: ${"%.2f".format(bsa)} m².\nVO2: ${"%.2f".format(vo2)} mL/min."
                )
                navController.popBackStack()
            } else {
                Toast.makeText(context, "Ingrese valores numéricos válidos.", Toast.LENGTH_SHORT)
                    .show()
            }


        }) {
            Text("Calcular y volver")
        }

    }
}

//enum class Gender { MALE, FEMALE }

