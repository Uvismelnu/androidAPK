package com.numel.abvcalculator.screens

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
// import androidx.compose.ui.graphics.StrokeCap // Descomentar si se usa para drawPoints
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.numel.abvcalculator.ui.theme.* // Asegúrate de que estos colores estén definidos
import kotlin.math.log10
import kotlin.math.pow

// Define tus colores si no están ya en ui.theme (Ejemplos)
val crema = Color(0xFFF0E68C) // Ejemplo de color crema
val marron = Color(0xFFA52A2A) // Ejemplo de color marrón
val verde = Color(0xFF228B22)  // Ejemplo de color verde (ForestGreen)
// Pink40 y Pink80 deberían estar en tus theme colors (Material 3)

// Data class para las etiquetas de referencia
data class ReferenceLabelInfo(
    val text: String,
    val xData: Float,
    val yData: Float,
    val textSizeDp: Dp,
    val colorInt: Int,
    val typeface: Typeface? = null
)

@Composable
fun CartesianGraphScreen(
    modifier: Modifier = Modifier.fillMaxWidth().height(400.dp),
    maxDataValue: Float = 100f,
    labelInterval: Float = 10f,
    lines: List<Pair<Pair<Float, Float>, Pair<Float, Float>>> = emptyList(),
    phAxisLabelFontSize: Dp = 14.dp,
    currentPH: Float = 7.4f, // Valor de pH para el punto a dibujar
    currentCO2: Float = 40f  // Valor de CO2 para el punto a dibujar
) {
    val localDensity = LocalDensity.current

    Column {
        Canvas(modifier = modifier) {
            val actualCanvasWidth = size.width
            val actualCanvasHeight = size.height

            val scaleFactorX = actualCanvasWidth / maxDataValue
            val scaleFactorY = actualCanvasHeight / maxDataValue

            fun dataToCanvasX(dataX: Float): Float = dataX * scaleFactorX
            fun dataToCanvasY(dataY: Float): Float = actualCanvasHeight - (dataY * scaleFactorY)

            // Ejes
            drawLine(
                color = Color.Black,
                start = Offset(0f, actualCanvasHeight),
                end = Offset(actualCanvasWidth, actualCanvasHeight),
                strokeWidth = 1.dp.toPx(localDensity)
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(0f, actualCanvasHeight),
                strokeWidth = 1.dp.toPx(localDensity)
            )

            val polygonLabelPaint = Paint().apply {
                isAntiAlias = true
                textSize = 11.dp.toPx(localDensity)
                textAlign = Paint.Align.CENTER
            }

            // --- Acidosis Metabólica ---
            val acidosisMetabolicaPath = Path().apply {
                moveTo(dataToCanvasX(17.96651f), dataToCanvasY(100f))
                lineTo(dataToCanvasX(17.96111f), dataToCanvasY(81.04058f))
                lineTo(dataToCanvasX(35f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(35.0f), dataToCanvasY(41.02029f))
                lineTo(dataToCanvasX(28.493114f), dataToCanvasY(41.00869f))
                lineTo(dataToCanvasX(10.942479f), dataToCanvasY(75.23188f))
                lineTo(dataToCanvasX(10.831758f), dataToCanvasY(100f))
                close()
            }
            drawPath(acidosisMetabolicaPath, Color.LightGray, style = Fill)
            drawPath(acidosisMetabolicaPath, Color.Black, style = Stroke(width = 0.5.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Acid. Metab.",
                dataToCanvasX(18f),
                dataToCanvasY(70f),
                polygonLabelPaint.apply { color = android.graphics.Color.BLACK }
            )

            // --- Acidosis Respiratoria Aguda ---
            val acidRespAgudaPath = Path().apply {
                moveTo(dataToCanvasX(100f), dataToCanvasY(91.47f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(43f))
                lineTo(dataToCanvasX(55.831017f), dataToCanvasY(47.65491f))
                lineTo(dataToCanvasX(100f), dataToCanvasY(82.46376f))
                close()
            }
            drawPath(acidRespAgudaPath, crema, style = Fill)
            drawPath(acidRespAgudaPath, Color.Black, style = Stroke(width = 0.5.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Acid. Resp. Aguda",
                dataToCanvasX(70f),
                dataToCanvasY(65f),
                polygonLabelPaint.apply { color = android.graphics.Color.DKGRAY }
            )

            // --- Acidosis Respiratoria Crónica ---
            val acidRespCronicaPath = Path().apply {
                moveTo(dataToCanvasX(100f), dataToCanvasY(66.63768f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(43f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(37f))
                lineTo(dataToCanvasX(100f), dataToCanvasY(52.927536f))
                close()
            }
            drawPath(acidRespCronicaPath, marron, style = Fill)
            drawPath(acidRespCronicaPath, Color.Black, style = Stroke(width = 0.5.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Acid. Resp. Crón.",
                dataToCanvasX(70f),
                dataToCanvasY(50f),
                polygonLabelPaint.apply { color = android.graphics.Color.WHITE }
            )

            // --- Alcalosis Metabólica ---
            val alcalosisMetabolicaPath = Path().apply {
                moveTo(dataToCanvasX(62.39542f), dataToCanvasY(19.82497f))
                lineTo(dataToCanvasX(60.21334f), dataToCanvasY(35.24637f))
                lineTo(dataToCanvasX(51.24459f), dataToCanvasY(38.80838f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(37f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(35.48134f))
                lineTo(dataToCanvasX(35.2f), dataToCanvasY(35.48134f))
                lineTo(dataToCanvasX(36.52984f), dataToCanvasY(30.95652f))
                lineTo(dataToCanvasX(51.12936f), dataToCanvasY(16.24539f))
                close()
            }
            drawPath(alcalosisMetabolicaPath, Pink40, style = Fill)
            drawPath(alcalosisMetabolicaPath, Color.Black, style = Stroke(width = 0.5.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Alc. Metab.",
                dataToCanvasX(48f),
                dataToCanvasY(25f),
                polygonLabelPaint.apply { color = android.graphics.Color.BLACK }
            )

            // --- Alcalosis Respiratoria Aguda ---
            val alcalosisRespAgudaPath = Path().apply {
                moveTo(dataToCanvasX(35f), dataToCanvasY(35.48134f))
                lineTo(dataToCanvasX(11.766135f), dataToCanvasY(16.37681f))
                lineTo(dataToCanvasX(10.847961f), dataToCanvasY(24.31884f))
                lineTo(dataToCanvasX(35f), dataToCanvasY(40.550724f))
                close()
            }
            drawPath(alcalosisRespAgudaPath, Color.Blue.copy(alpha = 0.7f), style = Fill)
            drawPath(alcalosisRespAgudaPath, Color.Black, style = Stroke(width = 0.5.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Alc. Resp. Aguda",
                dataToCanvasX(22f),
                dataToCanvasY(28f),
                polygonLabelPaint.apply { color = android.graphics.Color.WHITE }
            )

            // --- Alcalosis Respiratoria Crónica ---
            val alcalosisRespCronicaPath = Path().apply {
                moveTo(dataToCanvasX(35f), dataToCanvasY(40.86956f))
                lineTo(dataToCanvasX(11.064002f), dataToCanvasY(27.2463f))
                lineTo(dataToCanvasX(11.415068f), dataToCanvasY(37.855072f)) // Ajuste para cerrar el triángulo
                close()
            }
            drawPath(alcalosisRespCronicaPath, Color.Magenta.copy(alpha = 0.7f), style = Fill)
            drawPath(alcalosisRespCronicaPath, Color.Black, style = Stroke(width = 0.5.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Alc. Resp. Crón.",
                dataToCanvasX(20f),
                dataToCanvasY(36f),
                polygonLabelPaint.apply { color = android.graphics.Color.WHITE }
            )

            // --- Zona Normal ---
            val normalPath = Path().apply {
                moveTo(dataToCanvasX(35f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(35.48134f))
                lineTo(dataToCanvasX(35f), dataToCanvasY(35.48134f))
                close()
            }
            drawPath(normalPath, Color.Red.copy(alpha = 0.8f), style = Fill)
            drawPath(normalPath, Color.Black, style = Stroke(width = 1.dp.toPx(localDensity))) // Borde más grueso para "Normal"
            drawContext.canvas.nativeCanvas.drawText(
                "Normal",
                dataToCanvasX(40f), // Centro del cuadrado
                dataToCanvasY(40f), // Centro del cuadrado
                polygonLabelPaint.apply {
                    color = android.graphics.Color.WHITE
                    textSize = 10.dp.toPx(localDensity) // Un poco más pequeño para "Normal"
                }
            )

            // ========== LÍNEAS VERDES (de la lista "lines") ============
            lines.forEach { line ->
                val start = Offset(dataToCanvasX(line.first.first), dataToCanvasY(line.first.second))
                val end = Offset(dataToCanvasX(line.second.first), dataToCanvasY(line.second.second))
                drawLine(
                    color = verde,
                    start = start,
                    end = end,
                    strokeWidth = 0.7.dp.toPx(localDensity)
                )
            }

            // ========== GRID y ETIQUETAS =============
            val gridLabelPaint = Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.DKGRAY
                textSize = 8.dp.toPx(localDensity)
                textAlign = Paint.Align.CENTER
            }
            if (labelInterval > 0) {
                val numGridLinesX = (maxDataValue / labelInterval).toInt()
                for (i in 0..numGridLinesX) {
                    val xData = i * labelInterval
                    val xCanvas = dataToCanvasX(xData)
                    drawLine( // Líneas verticales
                        color = Color.LightGray.copy(alpha = 0.5f),
                        start = Offset(xCanvas, 0f),
                        end = Offset(xCanvas, actualCanvasHeight),
                        strokeWidth = 0.5.dp.toPx(localDensity)
                    )
                    if (i > 0) {
                        drawContext.canvas.nativeCanvas.drawText(
                            xData.toInt().toString(),
                            xCanvas,
                            actualCanvasHeight + 12.dp.toPx(localDensity),
                            gridLabelPaint
                        )
                    }
                }

                val numGridLinesY = (maxDataValue / labelInterval).toInt()
                for (i in 0..numGridLinesY) {
                    val yData = i * labelInterval
                    val yCanvas = dataToCanvasY(yData)
                    drawLine( // Líneas horizontales
                        color = Color.LightGray.copy(alpha = 0.5f),
                        start = Offset(0f, yCanvas),
                        end = Offset(actualCanvasWidth, yCanvas),
                        strokeWidth = 0.5.dp.toPx(localDensity)
                    )
                    if (i > 0) {
                        drawContext.canvas.nativeCanvas.drawText(
                            yData.toInt().toString(),
                            -10.dp.toPx(localDensity),
                            yCanvas + gridLabelPaint.textSize / 3,
                            gridLabelPaint.apply { textAlign = Paint.Align.RIGHT }
                        )
                    }
                }
                drawContext.canvas.nativeCanvas.drawText(
                    "0",
                    -10.dp.toPx(localDensity), // Origen X
                    actualCanvasHeight + 12.dp.toPx(localDensity), // Origen Y
                    gridLabelPaint.apply { textAlign = Paint.Align.RIGHT }
                )
            }

            // ========== TEXTO DE EJES Y MARCAS ===========
            val axisLabelPaint = Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.BLACK
                textSize = 10.dp.toPx(localDensity)
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            // Bicarbonato (HCO3)
            drawIntoCanvas { canvas ->
                val hco3x = dataToCanvasX(3f)
                val hco3y = dataToCanvasY(15f)
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.rotate(-40f, hco3x, hco3y)
                canvas.nativeCanvas.drawText("HCO₃⁻ (mEq/L)", hco3x, hco3y, axisLabelPaint)
                canvas.nativeCanvas.restore()
            }

            // CO2
            drawContext.canvas.nativeCanvas.drawText(
                "pCO₂ (mmHg)",
                dataToCanvasX(maxDataValue / 2),
                actualCanvasHeight + 25.dp.toPx(localDensity),
                axisLabelPaint
            )

            // pH eje vertical (lado derecho)
            drawIntoCanvas { canvas ->
                val phLabelX = actualCanvasWidth + 20.dp.toPx(localDensity)
                val phLabelY = actualCanvasHeight / 2
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.translate(phLabelX, phLabelY)
                canvas.nativeCanvas.rotate(90f)
                canvas.nativeCanvas.drawText(
                    "pH",
                    0f,
                    0f + axisLabelPaint.textSize / 3, // Centrar texto después de rotar
                    axisLabelPaint.apply {
                        color = android.graphics.Color.MAGENTA
                        textSize = phAxisLabelFontSize.toPx(localDensity)
                        textAlign = Paint.Align.CENTER
                    }
                )
                canvas.nativeCanvas.restore()
            }

            // [H+] (lado izquierdo)
            drawIntoCanvas { canvas ->
                val hPlusLabelX = -20.dp.toPx(localDensity)
                val hPlusLabelY = actualCanvasHeight / 2
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.translate(hPlusLabelX, hPlusLabelY)
                canvas.nativeCanvas.rotate(-90f)
                canvas.nativeCanvas.drawText(
                    "[H⁺] (nEq/L)",
                    0f,
                    0f + axisLabelPaint.textSize / 3, // Centrar texto después de rotar
                    axisLabelPaint.apply { textAlign = Paint.Align.CENTER }
                )
                canvas.nativeCanvas.restore()
            }

            // ========== MARCAS Y ETIQUETAS DE REFERENCIA ==========
            val referenceLabelPaint = Paint().apply {
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
            }
            val labelData: List<ReferenceLabelInfo> = listOf(
                ReferenceLabelInfo("9", 35.87937f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("33", 95f, 68.601074f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("45", 95f, 50.30745f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("75", 95f, 30.18447f, 7.dp, android.graphics.Color.WHITE, Typeface.create(Typeface.DEFAULT, Typeface.BOLD)),
                ReferenceLabelInfo("72", 95f, 31.44216f, 7.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("69", 95f, 32.80921f, 7.dp, android.graphics.Color.BLACK),
                // ... Añade más aquí según sea necesario
            )

            labelData.forEach { labelInfo ->
                drawContext.canvas.nativeCanvas.drawText(
                    labelInfo.text,
                    dataToCanvasX(labelInfo.xData),
                    dataToCanvasY(labelInfo.yData),
                    referenceLabelPaint.apply {
                        textSize = labelInfo.textSizeDp.toPx(localDensity)
                        color = labelInfo.colorInt
                        typeface = labelInfo.typeface ?: Typeface.DEFAULT
                    }
                )
            }

            // ========== PUNTOS DE pH (en el eje Y derecho) ==========
            val phReferencePoints = listOf(
                5.71f, 9.97f, 17.83f, 21.94f, 26.84f, 38.46f, 45.36f,
                51.22f, 57.04f, 64.32f, 81.3f, 100f
            )
            val phPointRadius = 2.dp.toPx(localDensity)
            val phPointPaint = Paint().apply { color = android.graphics.Color.RED }
            val phPointLabelPaint = Paint().apply {
                color = android.graphics.Color.MAGENTA
                textSize = 8.dp.toPx(localDensity)
                textAlign = Paint.Align.LEFT
            }

            phReferencePoints.forEach { yDataValue ->
                if (yDataValue in 0f..maxDataValue) {
                    val yCanvasPosition = dataToCanvasY(yDataValue)
                    drawContext.canvas.nativeCanvas.drawCircle(
                        actualCanvasWidth - phPointRadius - 1.dp.toPx(localDensity),
                        yCanvasPosition,
                        phPointRadius,
                        phPointPaint
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "%.2f".format(9 - log10(yDataValue.toDouble())),
                        actualCanvasWidth + 4.dp.toPx(localDensity),
                        yCanvasPosition + phPointLabelPaint.textSize / 3,
                        phPointLabelPaint
                    )
                }
            }

            // ========== PUNTO DE DATOS (CO2 y pH) ==========
            if (currentPH > 0 && currentCO2 > 0) {
                val hPlusData = 10.0.pow((9.0 - currentPH).toDouble()) // Corrección aquí
                val xDataPoint = currentCO2
                val yDataPoint = hPlusData.toFloat()

                if (xDataPoint in 0f..maxDataValue && yDataPoint in 0f..maxDataValue) {
                    val pointCanvasX = dataToCanvasX(xDataPoint)
                    val pointCanvasY = dataToCanvasY(yDataPoint)

                    drawPoints(
                        points = listOf(Offset(pointCanvasX, pointCanvasY)),
                        pointMode = PointMode.Points,
                        color = Pink80,
                        strokeWidth = 6.dp.toPx(localDensity),
                        // cap = StrokeCap.Round // Para hacerlo redondo
                    )
                    drawCircle(
                        color = Pink80.copy(alpha = 0.5f),
                        radius = 8.dp.toPx(localDensity),
                        center = Offset(pointCanvasX, pointCanvasY),
                        style = Stroke(width = 1.dp.toPx(localDensity))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp)) // Espacio debajo del gráfico
    }
}

// Extensión para facilitar la conversión de Dp a Px dentro de DrawScope o Composable
fun Dp.toPx(density: Density): Float = with(density) { this@toPx.toPx() }
