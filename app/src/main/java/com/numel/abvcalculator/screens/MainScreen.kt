package com.numel.abvcalculator.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.numel.abvcalculator.navigation.ScreenData
import com.numel.abvcalculator.ui.theme.crami
import com.numel.abvcalculator.viewModel.MainViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel,
    toScreen1: () -> Unit,
    toScreen2: () -> Unit,
    toAcidoBase: () -> Unit,
    toCalcium: () -> Unit,
    toCirculationMetabolism: () -> Unit,
    toBicarbonate: () -> Unit,
    toHemoglobin: () -> Unit,
    toPO2arterial: () -> Unit,
    toPO2venous: () -> Unit,
    toSatArterialO2: () -> Unit,
    toChlorine: () -> Unit,
    toSatVenousO2: () -> Unit,
    toPotassium: () -> Unit,
    toSodium: () -> Unit,
    toOxygenConsump: () -> Unit,
    toClassifyPaCO2: () -> Unit
) {
    val text = viewModel.resultText
    val screenData = ScreenData(text.value)
    val scrollerState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollerState)
            .background(crami)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Configuración de gráficos
        val maximoValor by remember { mutableFloatStateOf(100f) }
        val phPosicion by remember { mutableFloatStateOf(105f) }
        val phsize by remember { mutableFloatStateOf(34f) }
        val canvasDp = 300.dp
        val pH = viewModel.txPharterial.value.toFloatOrNull() ?: 7.2f
        val co2 = viewModel.txCO2arterial.value.toFloatOrNull() ?: 20f

        // Mostrar gráficos según la visibilidad
        if (viewModel.isScreen1Visible) {
            Spacer(modifier = Modifier.height(20.dp))
            CartesianGraph(
                modifier = Modifier.size(canvasDp),
                maxDataValue = maximoValor,
                labelInterval = 10f,
                lineStart = Pair(4.73744f, 18.815413f),
                lineEnd = Pair(25.17851f, 100f),
                lineStart1 = Pair(6.466086f, 17.12065f),
                lineEnd1 = Pair(37.76776f, 100f),
                lineStart2 = Pair(7.909054f, 15.705963f),
                lineEnd2 = Pair(50.35702f, 100f),
                lineStart3 = Pair(9.131757f, 14.507225f),
                lineEnd3 = Pair(62.94627f, 100f),
                lineStart4 = Pair(10.181052f, 13.478496f),
                lineEnd4 = Pair(75.53552f, 100f),
                lineStart5 = Pair(11.091388f, 12.586003f),
                lineEnd5 = Pair(88.12478f, 100f),
                lineStart6 = Pair(11.888652f, 11.804365f),
                lineEnd6 = Pair(100f, 99.29103f),
                lineStart7 = Pair(12.592681f, 11.114135f),
                lineEnd7 = Pair(100f, 88.25869f),
                lineStart8 = Pair(13.218925f, 10.500165f),
                lineEnd8 = Pair(100f, 79.43282f),
                lineStart9 = Pair(13.779602f, 9.950478f),
                lineEnd9 = Pair(100f, 72.21166f),
                lineStart10 = Pair(14.284495f, 9.455481f),
                lineEnd10 = Pair(100f, 66.19402f),
                lineStart11 = Pair(14.741535f, 9.007398f),
                lineEnd11 = Pair(100f, 61.10217f),
                lineStart12 = Pair(15.157219f, 8.599862f),
                lineEnd12 = Pair(100f, 56.73773f),
                lineStart13 = Pair(15.536916f, 8.227607f),
                lineEnd13 = Pair(100f, 52.95521f),
                lineStart14 = Pair(15.885105f, 7.8862424f),
                lineEnd14 = Pair(100f, 49.64551f),
                lineStart15 = Pair(16.205553f, 7.572075f),
                lineEnd15 = Pair(100f, 46.72519f),
                lineStart16 = Pair(16.50145f, 7.281981f),
                lineEnd16 = Pair(100f, 44.12935f),
                lineStart17 = Pair(16.77551f, 7.013294f),
                lineEnd17 = Pair(100f, 41.80675f),
                lineStart18 = Pair(17.03006f, 6.763729f),
                lineEnd18 = Pair(100f, 39.71641f),
                lineStart19 = Pair(17.2671f, 6.531315f),
                lineEnd19 = Pair(100f, 37.825153f),
                lineStart20 = Pair(17.48843f, 6.314343f),
                lineEnd20 = Pair(100f, 36.10583f),
                lineStart21 = Pair(17.695509f, 6.111323f),
                lineEnd21 = Pair(100f, 34.53601f),
                lineStart22 = Pair(17.88968f, 5.920951f),
                lineEnd22 = Pair(100f, 33.097f),
                lineStart23 = Pair(18.07213f, 5.742082f),
                lineEnd23 = Pair(100f, 31.773129f),
                phPosicion = phPosicion,
                phsize = phsize,
                pH = pH,
                co2Posicion = co2
            )
        }

        // Mostrar otros gráficos según su visibilidad
        if (viewModel.isScreenCO2100_160_H_0_100Visible || viewModel.isScreenCO2100_160_H_100_160Visible || viewModel.isScreenCO2_0_100_h100_160Visible) {
            Spacer(modifier = Modifier.height(20.dp))
            when {
                viewModel.isScreenCO2100_160_H_0_100Visible -> {
                    CartesianGraghCO2100_160_H0_100(
                        modifier = Modifier.size(canvasDp),
                        labelInterval = 10f,
                        lineStart = Pair(100f, 61.10f),
                        lineEnd = Pair(160f, 97.76f),
                        lineStart1 = Pair(100f, 56.70f),
                        lineEnd1 = Pair(160f, 90.72f),
                        lineStart2 = Pair(100f, 53.00f),
                        lineEnd2 = Pair(160f, 84.80f),
                        lineStart3 = Pair(100f, 49.70f),
                        lineEnd3 = Pair(160f, 79.52f),
                        lineStart4 = Pair(100f, 46.70f),
                        lineEnd4 = Pair(160f, 74.72f),
                        lineStart5 = Pair(100f, 44.10f),
                        lineEnd5 = Pair(160f, 70.56f),
                        lineStart6 = Pair(100f, 41.80f),
                        lineEnd6 = Pair(160f, 66.88f),
                        lineStart7 = Pair(100f, 39.70f),
                        lineEnd7 = Pair(160f, 63.52f),
                        lineStart8 = Pair(100f, 37.80f),
                        lineEnd8 = Pair(160f, 60.48f),
                        lineStart9 = Pair(100f, 36.10f),
                        lineEnd9 = Pair(160f, 57.76f),
                        lineStart10 = Pair(100f, 34.50f),
                        lineEnd10 = Pair(160f, 55.20f),
                        lineStart11 = Pair(100f, 33.10f),
                        lineEnd11 = Pair(160f, 52.96f),
                        lineStart12 = Pair(100f, 31.80f),
                        lineEnd12 = Pair(160f, 50.88f),
                        lineStart13 = Pair(100f, 99.30f),
                        lineEnd13 = Pair(100.70f, 100f),
                        lineStart14 = Pair(100f, 88.30f),
                        lineEnd14 = Pair(113.23f, 100f),
                        lineStart15 = Pair(100f, 79.40f),
                        lineEnd15 = Pair(125.94f, 100f),
                        lineStart16 = Pair(100f, 72.20f),
                        lineEnd16 = Pair(138.50f, 100f),
                        lineStart17 = Pair(100f, 66.20f),
                        lineEnd17 = Pair(151.05f, 100f),
                        phsize = phsize,
                        pH = pH,
                        co2Posicion = co2
                    )
                }
                viewModel.isScreenCO2100_160_H_100_160Visible -> {
                    CartesianGraph100_160all(
                        modifier = Modifier.size(canvasDp),
                        maxDataValue = maximoValor,
                        labelInterval = 10f,
                        lineStart = Pair(100f, 158.90f),
                        lineEnd = Pair(100.70f, 160f),
                        lineStart1 = Pair(100f, 132.40f),
                        lineEnd1 = Pair(120.86f, 160f),
                        lineStart2 = Pair(100f, 113.50f),
                        lineEnd2 = Pair(140.96f, 160f),
                        lineStart3 = Pair(100.70f, 100f),
                        lineEnd3 = Pair(160f, 158.88f),
                        lineStart4 = Pair(113.23f, 100f),
                        lineEnd4 = Pair(160f, 141.28f),
                        lineStart5 = Pair(125.94f, 100f),
                        lineEnd5 = Pair(160f, 127.04f),
                        lineStart6 = Pair(138.50f, 100f),
                        lineEnd6 = Pair(160f, 115.52f),
                        lineStart7 = Pair(151.05f, 100f),
                        lineEnd7 = Pair(160f, 105.92f),
                        phsize = phsize,
                        pH = pH,
                        co2Posicion = co2
                    )
                }
                viewModel.isScreenCO2_0_100_h100_160Visible -> {
                    CartesianGraphCO2_0_100_h100_160(
                        modifier = Modifier.size(canvasDp),
                        maxDataValue = maximoValor,
                        labelInterval = 10f,
                        lineStart = Pair(25.1780f, 100f),
                        lineEnd = Pair(40.2925f, 160f),
                        lineStart1 = Pair(37.7512f, 100f),
                        lineEnd1 = Pair(60.41f, 160f),
                        lineStart2 = Pair(50.3335f, 100f),
                        lineEnd2 = Pair(80.54f, 160f),
                        lineStart3 = Pair(62.9445f, 100f),
                        lineEnd3 = Pair(100f, 158.8775f),
                        lineStart4 = Pair(75.5442f, 100f),
                        lineEnd4 = Pair(100f, 132.3772f),
                        lineStart5 = Pair(88.1162f, 100f),
                        lineEnd5 = Pair(100f, 113.4869f),
                        phsize = phsize,
                        pH = pH,
                        co2Posicion = co2
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = screenData.text,
            color = Color.Blue,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Organizar botones en secciones lógicas
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Navegación Principal", fontSize = 18.sp, color = Color.Black)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { toScreen1() }) {
                Text("Pantalla 1")
            }
            OutlinedButton(onClick = { toScreen2() }) {
                Text("Pantalla 2")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Análisis Clínicos", fontSize = 18.sp, color = Color.Black)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(onClick = { toAcidoBase() }) {
                Text("Ácido Base")
            }
            OutlinedButton(onClick = { toCalcium() }) {
                Text("Calcio")
            }
            OutlinedButton(onClick = { toCirculationMetabolism() }) {
                Text("Circulación y Metabolismo")
            }
            OutlinedButton(onClick = { toBicarbonate() }) {
                Text("Bicarbonato")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Parámetros de Oxígeno", fontSize = 18.sp, color = Color.Black)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(onClick = { toPO2arterial() }) {
                Text("PO2 Arterial")
            }
            OutlinedButton(onClick = { toPO2venous() }) {
                Text("PO2 Venoso")
            }
            OutlinedButton(onClick = { toSatArterialO2() }) {
                Text("Sat. Arterial O2")
            }
            OutlinedButton(onClick = { toSatVenousO2() }) {
                Text("Sat. Venosa O2")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Electrolitos", fontSize = 18.sp, color = Color.Black)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { toChlorine() }) {
                Text("Cloruro")
            }
            OutlinedButton(onClick = { toPotassium() }) {
                Text("Potasio")
            }
            OutlinedButton(onClick = { toSodium() }) {
                Text("Sodio")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Otros", fontSize = 18.sp, color = Color.Black)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { toOxygenConsump() }) {
                Text("Consumo de Oxígeno")
            }
            OutlinedButton(onClick = { toClassifyPaCO2() }) {
                Text("CO2 Arterial")
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}
