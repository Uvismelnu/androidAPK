package com.numel.abvcalculator.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.viewModel.MainViewModel

@Composable
fun Screen2(navController: NavController, screenData: ScreenData, viewModel: MainViewModel) {
    var newText by remember { mutableStateOf(screenData.text) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = newText, onValueChange = { newText = it }, label = { Text("Modificar texto") })
        Button(onClick = {
            viewModel.updateText(newText)
            navController.popBackStack()
        }) {
            Text("Actualizar y volver")
        }
    }
}
