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

        val phLabelFontSizeDp = 14.dp
        val canvasDp = 300.dp
        val currentPhValue = viewModel.txPharterial.value.toFloatOrNull() ?: 7.2f
        val currentCo2Value = viewModel.txCO2arterial.value.toFloatOrNull() ?: 20f

        val phsize by remember { mutableFloatStateOf(34f) }

        val pH = viewModel.txPharterial.value.toFloatOrNull() ?: 7.2f
        val co2 = viewModel.txCO2arterial.value.toFloatOrNull() ?: 20f

        // Mostrar gráficos según la visibilidad
        if (viewModel.isScreen1Visible) {
            Spacer(modifier = Modifier.height(20.dp))
            CartesianGraphScreen(
                modifier = Modifier.size(canvasDp),
                maxDataValue = maximoValor,
                labelInterval = 10f,
                lines = listOf(
                    Pair(Pair(4.73744f, 18.815413f), Pair(25.17851f, 100f)),
                    Pair(Pair(6.466086f, 17.12065f), Pair(37.76776f, 100f)),
                    Pair(Pair(7.909054f, 15.705963f), Pair(50.35702f, 100f)),
                    Pair(Pair(9.131757f, 14.507225f), Pair(62.94627f, 100f)),
                    Pair(Pair(10.181052f, 13.478496f), Pair(75.53552f, 100f)),
                    Pair(Pair(11.091388f, 12.586003f), Pair(88.12478f, 100f)),
                    Pair(Pair(11.888652f, 11.804365f), Pair(100f, 99.29103f)),
                    Pair(Pair(12.592681f, 11.114135f), Pair(100f, 88.25869f)),
                    Pair(Pair(13.218925f, 10.500165f), Pair(100f, 79.43282f)),
                    Pair(Pair(13.779602f, 9.950478f), Pair(100f, 72.21166f)),
                    Pair(Pair(14.284495f, 9.455481f), Pair(100f, 66.19402f)),
                    Pair(Pair(14.741535f, 9.007398f), Pair(100f, 61.10217f)),
                    Pair(Pair(15.157219f, 8.599862f), Pair(100f, 56.73773f)),
                    Pair(Pair(15.536916f, 8.227607f), Pair(100f, 52.95521f)),
                    Pair(Pair(15.885105f, 7.8862424f), Pair(100f, 49.64551f)),
                    Pair(Pair(16.205553f, 7.572075f), Pair(100f, 46.72519f)),
                    Pair(Pair(16.50145f, 7.281981f), Pair(100f, 44.12935f)),
                    Pair(Pair(16.77551f, 7.013294f), Pair(100f, 41.80675f)),
                    Pair(Pair(17.03006f, 6.763729f), Pair(100f, 39.71641f)),
                    Pair(Pair(17.2671f, 6.531315f), Pair(100f, 37.825153f)),
                    Pair(Pair(17.48843f, 6.314343f), Pair(100f, 36.10583f)),
                    Pair(Pair(17.695509f, 6.111323f), Pair(100f, 34.53601f)),
                    Pair(Pair(17.88968f, 5.920951f), Pair(100f, 33.097f)),
                    Pair(Pair(18.07213f, 5.742082f), Pair(100f, 31.773129f))
                ),
                phAxisLabelFontSize = phLabelFontSizeDp,
                currentPH = currentPhValue,
                currentCO2 = currentCo2Value
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
                        lines = listOf(
                            Pair(Pair(100f, 61.10f), Pair(160f, 97.76f)),
                            Pair(Pair(100f, 56.70f), Pair(160f, 90.72f)),
                            Pair(Pair(100f, 53.00f), Pair(160f, 84.80f)),
                            Pair(Pair(100f, 49.70f), Pair(160f, 79.52f)),
                            Pair(Pair(100f, 46.70f), Pair(160f, 74.72f)),
                            Pair(Pair(100f, 44.10f), Pair(160f, 70.56f)),
                            Pair(Pair(100f, 41.80f), Pair(160f, 66.88f)),
                            Pair(Pair(100f, 39.70f), Pair(160f, 63.52f)),
                            Pair(Pair(100f, 37.80f), Pair(160f, 60.48f)),
                            Pair(Pair(100f, 36.10f), Pair(160f, 57.76f)),
                            Pair(Pair(100f, 34.50f), Pair(160f, 55.20f)),
                            Pair(Pair(100f, 33.10f), Pair(160f, 52.96f)),
                            Pair(Pair(100f, 31.80f), Pair(160f, 50.88f)),
                            Pair(Pair(100f, 99.30f), Pair(100.70f, 100f)),
                            Pair(Pair(100f, 88.30f), Pair(113.23f, 100f)),
                            Pair(Pair(100f, 79.40f), Pair(125.94f, 100f)),
                            Pair(Pair(100f, 72.20f), Pair(138.50f, 100f)),
                            Pair(Pair(100f, 66.20f), Pair(151.05f, 100f))
                        ),
                        currentPH = pH,
                        currentCO2 = co2,
                        phAxisLabelFontSize = 14.dp,
                        referenceLabels = listOf(
                            ReferenceLabelInfo("21", 155f, 155.30f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("24", 155f, 154.56f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("27", 155f, 152.89f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("30", 155f, 150.51f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("33", 155f, 147.62f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("36", 155f, 144.41f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("39", 155f, 141.00f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("42", 155f, 137.47f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("45", 155f, 133.86f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("48", 155f, 130.21f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("51", 155f, 126.55f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("54", 155f, 122.91f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("57", 155f, 119.32f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("60", 155f, 115.78f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("63", 155f, 112.31f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("66", 155f, 108.92f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("69", 155f, 105.62f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("72", 155f, 102.40f, 9.dp, android.graphics.Color.BLACK)
                        )
                    )
                }
                viewModel.isScreenCO2100_160_H_100_160Visible -> {
                    CartesianGraph100_160all(
                        modifier = Modifier.size(canvasDp),
                        labelInterval = 10f,
                        lines = listOf(
                            Pair(Pair(100f, 158.90f), Pair(100.70f, 160f)),
                            Pair(Pair(100f, 132.40f), Pair(120.86f, 160f)),
                            Pair(Pair(100f, 113.50f), Pair(140.96f, 160f)),
                            Pair(Pair(100.70f, 100f), Pair(160f, 158.88f)),
                            Pair(Pair(113.23f, 100f), Pair(160f, 141.28f)),
                            Pair(Pair(125.94f, 100f), Pair(160f, 127.04f)),
                            Pair(Pair(138.50f, 100f), Pair(160f, 115.52f)),
                            Pair(Pair(151.05f, 100f), Pair(160f, 105.92f))
                        ),
                        currentPH = pH,
                        currentCO2 = co2,
                        phAxisLabelFontSize = 14.dp,
                        referenceLabels = listOf(
                            ReferenceLabelInfo("15", 97.55f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("18", 117.03f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("21", 136.63f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("24", 155f, 154.10f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("27", 155f, 136.79f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("30", 155f, 123.01f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("33", 155.0f, 111.80f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("36", 155.0f, 102.73f, 9.dp, android.graphics.Color.BLACK)
                        )
                    )
                }
                viewModel.isScreenCO2_0_100_h100_160Visible -> {
                    CartesianGraphCO2_0_100_h100_160(
                        modifier = Modifier.size(canvasDp),
                        labelInterval = 10f,
                        lines = listOf(
                            Pair(Pair(25.1780f, 100f), Pair(40.2925f, 160f)),
                            Pair(Pair(37.7512f, 100f), Pair(60.41f, 160f)),
                            Pair(Pair(50.3335f, 100f), Pair(80.54f, 160f)),
                            Pair(Pair(62.9445f, 100f), Pair(100f, 158.8775f)),
                            Pair(Pair(75.5442f, 100f), Pair(100f, 132.3772f)),
                            Pair(Pair(88.1162f, 100f), Pair(100f, 113.4869f))
                        ),
                        currentPH = pH,
                        currentCO2 = co2,
                        phAxisLabelFontSize = 14.dp,
                        referenceLabels = listOf(
                            ReferenceLabelInfo("6", 39.02669f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("9", 58.54f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("12", 78.05f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("15", 97.55f, 155f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("18", 95f, 150.92f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("21", 95f, 125.7686f, 9.dp, android.graphics.Color.BLACK),
                            ReferenceLabelInfo("24", 95f, 107.8f, 9.dp, android.graphics.Color.BLACK)
                        )
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