package com.numel.abvcalculator.screens

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.numel.abvcalculator.ui.theme.*
import kotlin.math.log10
import kotlin.math.pow
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween

// Define tus colores si no están ya en ui.theme
val crema = Color(0xFFF0E68C)
val marron = Color(0xFFA52A2A)
val verde = Color(0xFF228B22)

// Data class para las etiquetas de referencia
data class ReferenceLabelInfo(
    val text: String,
    val xData: Float,
    val yData: Float,
    val textSizeDp: Dp,
    val colorInt: Int,
    val typeface: Typeface? = null
)

// Extensión para facilitar la conversión de Dp a Px
fun Dp.toPx(density: Density): Float = with(density) { this@toPx.toPx() }

@Composable
fun CartesianGraphScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(400.dp),
    maxDataValue: Float = 100f,
    labelInterval: Float = 10f,
    lines: List<Pair<Pair<Float, Float>, Pair<Float, Float>>> = emptyList(),
    phAxisLabelFontSize: Dp = 14.dp,
    currentPH: Float = 7.4f,
    currentCO2: Float = 40f
) {
    val localDensity = LocalDensity.current

    // Blink animation
    val infiniteTransition = rememberInfiniteTransition(label = "blink")
    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkAlpha"
    )

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
            drawPath(
                acidosisMetabolicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
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
            drawPath(
                acidRespAgudaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
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
            drawPath(
                acidRespCronicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
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
            drawPath(
                alcalosisMetabolicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
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
            drawPath(
                alcalosisRespAgudaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
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
                lineTo(dataToCanvasX(11.415068f), dataToCanvasY(37.855072f))
                close()
            }
            drawPath(alcalosisRespCronicaPath, Color.Magenta.copy(alpha = 0.7f), style = Fill)
            drawPath(
                alcalosisRespCronicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
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
            drawPath(normalPath, Color.Black, style = Stroke(width = 1.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Normal",
                dataToCanvasX(40f),
                dataToCanvasY(40f),
                polygonLabelPaint.apply {
                    color = android.graphics.Color.WHITE
                    textSize = 10.dp.toPx(localDensity)
                }
            )

            // ========== LÍNEAS VERDES CON ETIQUETAS ============
            lines.forEach { line ->
                val start =
                    Offset(dataToCanvasX(line.first.first), dataToCanvasY(line.first.second))
                val end =
                    Offset(dataToCanvasX(line.second.first), dataToCanvasY(line.second.second))
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
                    drawLine(
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
                    drawLine(
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
                    -10.dp.toPx(localDensity),
                    actualCanvasHeight + 12.dp.toPx(localDensity),
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
            val pA = Offset(dataToCanvasX(4.73744f), dataToCanvasY(18.815413f))
            val pB = Offset(dataToCanvasX(18.07213f), dataToCanvasY(5.742082f))
            val angleRad = kotlin.math.atan2(pB.y - pA.y, pB.x - pA.x)
            val angleDeg = Math.toDegrees(angleRad.toDouble()).toFloat()

            val anchorX = dataToCanvasX(3f)
            val anchorY = dataToCanvasY(18f)

            val nudgePerp = 4.dp.toPx(localDensity)
            val nx = kotlin.math.cos(angleRad + Math.PI / 2).toFloat()
            val ny = kotlin.math.sin(angleRad + Math.PI / 2).toFloat()
            val x = anchorX + nx * nudgePerp
            val y = anchorY + ny * nudgePerp

            val hco3Paint = Paint(axisLabelPaint).apply {
                textAlign = Paint.Align.LEFT
            }

            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.rotate(angleDeg, x, y)
                canvas.nativeCanvas.drawText("HCO₃⁻ (mEq/L)", x, y, hco3Paint)
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
                val phLabelX = actualCanvasWidth - 10.dp.toPx(localDensity)
                val phLabelY = actualCanvasHeight - 10.dp.toPx(localDensity)
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.translate(phLabelX, phLabelY)
                canvas.nativeCanvas.rotate(-90f)
                canvas.nativeCanvas.drawText(
                    "pH",
                    0f,
                    0f + axisLabelPaint.textSize / 3,
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
                val hPlusLabelX = 10.dp.toPx(localDensity)
                val hPlusLabelY = actualCanvasHeight / 2
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.translate(hPlusLabelX, hPlusLabelY)
                canvas.nativeCanvas.rotate(-90f)
                canvas.nativeCanvas.drawText(
                    "[H⁺] (nEq/L)",
                    0f,
                    0f + axisLabelPaint.textSize / 3,
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
                ReferenceLabelInfo("6", 23.919584f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("9", 35.87937f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("12", 47.83916f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("15", 59.79895f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("18", 71.75874f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("21", 83.71854f, 95f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("24", 95f, 94.32647f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("27", 95f, 83.84575f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("30", 95f, 75.46117f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("33", 95f, 68.60107f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("36", 95f, 62.88432f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("39", 95f, 58.04706f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("42", 95f, 53.90084f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("45", 95f, 50.30745f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("48", 95f, 47.16323f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("51", 95f, 44.38893f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("54", 95f, 41.92288f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("57", 95f, 39.71641f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("60", 95f, 37.73059f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("63", 95f, 35.93389f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("66", 95f, 34.30054f, 9.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo(
                    "75",
                    95f,
                    30.18447f,
                    7.dp,
                    android.graphics.Color.WHITE,
                    Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                ),
                ReferenceLabelInfo("72", 95f, 31.44216f, 7.dp, android.graphics.Color.BLACK),
                ReferenceLabelInfo("69", 95f, 32.80921f, 7.dp, android.graphics.Color.BLACK),
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

            // ========== PUNTO DE DATOS CON EFECTO BLINK ==========
            if (currentPH > 0 && currentCO2 > 0) {
                val hPlusData = 10.0.pow((9.0 - currentPH).toDouble())
                val xDataPoint = currentCO2
                val yDataPoint = hPlusData.toFloat()

                if (xDataPoint in 0f..maxDataValue && yDataPoint in 0f..maxDataValue) {
                    val pointCanvasX = dataToCanvasX(xDataPoint)
                    val pointCanvasY = dataToCanvasY(yDataPoint)

                    drawPoints(
                        points = listOf(Offset(pointCanvasX, pointCanvasY)),
                        pointMode = PointMode.Points,
                        color = Pink80.copy(alpha = blinkAlpha),
                        strokeWidth = 6.dp.toPx(localDensity),
                    )
                    drawCircle(
                        color = Pink80.copy(alpha = 0.5f * blinkAlpha),
                        radius = 8.dp.toPx(localDensity),
                        center = Offset(pointCanvasX, pointCanvasY),
                        style = Stroke(width = 1.dp.toPx(localDensity))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun CartesianGraphBase(
    modifier: Modifier,
    maxX: Float,
    maxY: Float,
    labelInterval: Float = 10f,
    lines: List<Pair<Pair<Float, Float>, Pair<Float, Float>>> = emptyList(),
    currentPH: Float,
    currentCO2: Float,
    phAxisLabelFontSize: Dp = 14.dp,
    referenceLabels: List<ReferenceLabelInfo> = emptyList()
) {
    val localDensity = LocalDensity.current

    // Blink animation
    val infiniteTransition = rememberInfiniteTransition(label = "blink")
    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkAlpha"
    )

    Column {
        Canvas(modifier = modifier) {
            val actualCanvasWidth = size.width
            val actualCanvasHeight = size.height

            // Escala fija por variante
            val scaleFactorX = actualCanvasWidth / maxX
            val scaleFactorY = actualCanvasHeight / maxY

            fun dataToCanvasX(dataX: Float): Float = dataX * scaleFactorX
            fun dataToCanvasY(dataY: Float): Float = actualCanvasHeight - (dataY * scaleFactorY)

            // Ejes
            drawLine(
                Color.Black,
                Offset(0f, actualCanvasHeight),
                Offset(actualCanvasWidth, actualCanvasHeight),
                1.dp.toPx(localDensity)
            )
            drawLine(
                Color.Black,
                Offset(0f, 0f),
                Offset(0f, actualCanvasHeight),
                1.dp.toPx(localDensity)
            )

            val polygonLabelPaint = Paint().apply {
                isAntiAlias = true
                textSize = 11.dp.toPx(localDensity)
                textAlign = Paint.Align.CENTER
            }

            // --- Polígonos ---
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
            drawPath(
                acidosisMetabolicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Acid. Metab.",
                dataToCanvasX(18f), dataToCanvasY(70f),
                polygonLabelPaint.apply { color = android.graphics.Color.BLACK }
            )

            val acidRespAgudaPath = Path().apply {
                moveTo(dataToCanvasX(100f), dataToCanvasY(91.47f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(43f))
                lineTo(dataToCanvasX(55.831017f), dataToCanvasY(47.65491f))
                lineTo(dataToCanvasX(100f), dataToCanvasY(82.46376f))
                close()
            }
            drawPath(acidRespAgudaPath, crema, style = Fill)
            drawPath(
                acidRespAgudaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Acid. Resp. Aguda",
                dataToCanvasX(70f), dataToCanvasY(65f),
                polygonLabelPaint.apply { color = android.graphics.Color.DKGRAY }
            )

            val acidRespCronicaPath = Path().apply {
                moveTo(dataToCanvasX(100f), dataToCanvasY(66.63768f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(43f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(37f))
                lineTo(dataToCanvasX(100f), dataToCanvasY(52.927536f))
                close()
            }
            drawPath(acidRespCronicaPath, marron, style = Fill)
            drawPath(
                acidRespCronicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Acid. Resp. Crón.",
                dataToCanvasX(70f), dataToCanvasY(50f),
                polygonLabelPaint.apply { color = android.graphics.Color.WHITE }
            )

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
            drawPath(
                alcalosisMetabolicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Alc. Metab.",
                dataToCanvasX(48f), dataToCanvasY(25f),
                polygonLabelPaint.apply { color = android.graphics.Color.BLACK }
            )

            val alcalosisRespAgudaPath = Path().apply {
                moveTo(dataToCanvasX(35f), dataToCanvasY(35.48134f))
                lineTo(dataToCanvasX(11.766135f), dataToCanvasY(16.37681f))
                lineTo(dataToCanvasX(10.847961f), dataToCanvasY(24.31884f))
                lineTo(dataToCanvasX(35f), dataToCanvasY(40.550724f))
                close()
            }
            drawPath(alcalosisRespAgudaPath, Color.Blue.copy(alpha = 0.7f), style = Fill)
            drawPath(
                alcalosisRespAgudaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Alc. Resp. Aguda",
                dataToCanvasX(22f), dataToCanvasY(28f),
                polygonLabelPaint.apply { color = android.graphics.Color.WHITE }
            )

            val alcalosisRespCronicaPath = Path().apply {
                moveTo(dataToCanvasX(35f), dataToCanvasY(40.86956f))
                lineTo(dataToCanvasX(11.064002f), dataToCanvasY(27.2463f))
                lineTo(dataToCanvasX(11.415068f), dataToCanvasY(37.855072f))
                close()
            }
            drawPath(alcalosisRespCronicaPath, Color.Magenta.copy(alpha = 0.7f), style = Fill)
            drawPath(
                alcalosisRespCronicaPath,
                Color.Black,
                style = Stroke(width = 0.5.dp.toPx(localDensity))
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Alc. Resp. Crón.",
                dataToCanvasX(20f), dataToCanvasY(36f),
                polygonLabelPaint.apply { color = android.graphics.Color.WHITE }
            )

            // Normal (cuadro 35..45)
            val normalPath = Path().apply {
                moveTo(dataToCanvasX(35f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(44.66836f))
                lineTo(dataToCanvasX(45f), dataToCanvasY(35.48134f))
                lineTo(dataToCanvasX(35f), dataToCanvasY(35.48134f))
                close()
            }
            drawPath(normalPath, Color.Red.copy(alpha = 0.8f), style = Fill)
            drawPath(normalPath, Color.Black, style = Stroke(width = 1.dp.toPx(localDensity)))
            drawContext.canvas.nativeCanvas.drawText(
                "Normal",
                dataToCanvasX(40f), dataToCanvasY(40f),
                polygonLabelPaint.apply {
                    color = android.graphics.Color.WHITE; textSize = 10.dp.toPx(localDensity)
                }
            )

            // ========== LÍNEAS VERDES ============
            lines.forEach { line ->
                val start =
                    Offset(dataToCanvasX(line.first.first), dataToCanvasY(line.first.second))
                val end =
                    Offset(dataToCanvasX(line.second.first), dataToCanvasY(line.second.second))
                drawLine(verde, start, end, 0.7.dp.toPx(localDensity))
            }

            // Grid y etiquetas
            val gridLabelPaint = Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.DKGRAY
                textSize = 8.dp.toPx(localDensity)
                textAlign = Paint.Align.CENTER
            }
            if (labelInterval > 0) {
                val numX = (maxX / labelInterval).toInt()
                for (i in 0..numX) {
                    val xData = i * labelInterval
                    val xCanvas = dataToCanvasX(xData)
                    drawLine(
                        Color.LightGray.copy(alpha = 0.5f),
                        Offset(xCanvas, 0f),
                        Offset(xCanvas, actualCanvasHeight),
                        0.5.dp.toPx(localDensity)
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
                val numY = (maxY / labelInterval).toInt()
                for (i in 0..numY) {
                    val yData = i * labelInterval
                    val yCanvas = dataToCanvasY(yData)
                    drawLine(
                        Color.LightGray.copy(alpha = 0.5f),
                        Offset(0f, yCanvas),
                        Offset(actualCanvasWidth, yCanvas),
                        0.5.dp.toPx(localDensity)
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
                    "0", -10.dp.toPx(localDensity),
                    actualCanvasHeight + 12.dp.toPx(localDensity),
                    gridLabelPaint.apply { textAlign = Paint.Align.RIGHT }
                )
            }

            // Ejes y textos
            val axisLabelPaint = Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.BLACK
                textSize = 10.dp.toPx(localDensity)
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            // HCO3
            val pA = Offset(dataToCanvasX(4.73744f), dataToCanvasY(18.815413f))
            val pB = Offset(dataToCanvasX(18.07213f), dataToCanvasY(5.742082f))
            val angleRad = kotlin.math.atan2(pB.y - pA.y, pB.x - pA.x)
            val angleDeg = Math.toDegrees(angleRad.toDouble()).toFloat()

            val anchorX = dataToCanvasX(1f)
            val anchorY = dataToCanvasY(20f)

            val nudgePerp = 3.dp.toPx(localDensity)
            val nx = kotlin.math.cos(angleRad + Math.PI / 2).toFloat()
            val ny = kotlin.math.sin(angleRad + Math.PI / 2).toFloat()
            val x = anchorX + nx * nudgePerp
            val y = anchorY + ny * nudgePerp

            val hco3Paint = Paint(axisLabelPaint).apply {
                textAlign = Paint.Align.LEFT
            }

            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.rotate(angleDeg, x, y)
                canvas.nativeCanvas.drawText("HCO₃⁻ (mEq/L)", x, y, hco3Paint)
                canvas.nativeCanvas.restore()
            }

            // CO2
            drawContext.canvas.nativeCanvas.drawText(
                "pCO₂ (mmHg)",
                dataToCanvasX(maxX / 2f),
                actualCanvasHeight + 25.dp.toPx(localDensity),
                axisLabelPaint
            )

            // pH (derecha)
            drawIntoCanvas { canvas ->
                val phLabelX = actualCanvasWidth - 10.dp.toPx(localDensity)
                val phLabelY = actualCanvasHeight - 10.dp.toPx(localDensity)
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.translate(phLabelX, phLabelY)
                canvas.nativeCanvas.rotate(-90f)
                canvas.nativeCanvas.drawText(
                    "pH", 0f, 0f + axisLabelPaint.textSize / 3,
                    axisLabelPaint.apply {
                        color = android.graphics.Color.MAGENTA
                        textSize = phAxisLabelFontSize.toPx(localDensity)
                    })
                canvas.nativeCanvas.restore()
            }

            // [H+] (izquierda)
            drawIntoCanvas { canvas ->
                val hPlusLabelX = 10.dp.toPx(localDensity)
                val hPlusLabelY = actualCanvasHeight / 2
                canvas.nativeCanvas.save()
                canvas.nativeCanvas.translate(hPlusLabelX, hPlusLabelY)
                canvas.nativeCanvas.rotate(-90f)
                canvas.nativeCanvas.drawText(
                    "[H⁺] (nEq/L)",
                    0f,
                    0f + axisLabelPaint.textSize / 3,
                    axisLabelPaint
                )
                canvas.nativeCanvas.restore()
            }

            // Labels de referencia
            val referenceLabelPaint = Paint().apply {
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
            }

            // USAMOS LAS ETIQUETAS PASADAS COMO PARÁMETRO
            referenceLabels.forEach { labelInfo ->
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

            // Puntos de pH (lado derecho)
            val baseRefs = listOf(
                5.71f,
                9.97f,
                17.83f,
                21.94f,
                26.84f,
                38.46f,
                45.36f,
                51.22f,
                57.04f,
                64.32f,
                81.3f,
                100f
            )
            val phRefs = if (maxY > 100f) baseRefs + listOf(113.50f, 132.40f, 158.90f) else baseRefs
            val phPointRadius = 2.dp.toPx(localDensity)
            val phPointPaint = Paint().apply { color = android.graphics.Color.RED }
            val phPointLabelPaint = Paint().apply {
                color = android.graphics.Color.MAGENTA
                textSize = 8.dp.toPx(localDensity)
                textAlign = Paint.Align.LEFT
            }
            phRefs.forEach { yDataValue ->
                if (yDataValue in 0f..maxY) {
                    val yCanvasPosition = dataToCanvasY(yDataValue)
                    drawContext.canvas.nativeCanvas.drawCircle(
                        actualCanvasWidth - phPointRadius - 1.dp.toPx(localDensity),
                        yCanvasPosition, phPointRadius, phPointPaint
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "%.2f".format(9 - log10(yDataValue.toDouble())),
                        actualCanvasWidth + 4.dp.toPx(localDensity),
                        yCanvasPosition + phPointLabelPaint.textSize / 3,
                        phPointLabelPaint
                    )
                }
            }

            // ========== PUNTO ACTUAL CON EFECTO BLINK ==========
            if (currentPH > 0 && currentCO2 > 0) {
                val hPlus = 10.0.pow((9.0 - currentPH).toDouble()).toFloat()
                val x = currentCO2
                val y = hPlus
                if (x in 0f..maxX && y in 0f..maxY) {
                    val cx = dataToCanvasX(x)
                    val cy = dataToCanvasY(y)

                    // Punto con efecto blink
                    drawPoints(
                        points = listOf(Offset(cx, cy)),
                        pointMode = PointMode.Points,
                        color = Pink80.copy(alpha = blinkAlpha),
                        strokeWidth = 6.dp.toPx(localDensity)
                    )
                    drawCircle(
                        color = Pink80.copy(alpha = 0.5f * blinkAlpha),
                        radius = 8.dp.toPx(localDensity),
                        center = Offset(cx, cy),
                        style = Stroke(width = 1.dp.toPx(localDensity))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

// Posibilidad 1: [H+] 0..160, CO2 0..100
@Composable
fun CartesianGraphCO2_0_100_h100_160(
    modifier: Modifier = Modifier,
    labelInterval: Float = 10f,
    lines: List<Pair<Pair<Float, Float>, Pair<Float, Float>>> = emptyList(),
    currentPH: Float,
    currentCO2: Float,
    phAxisLabelFontSize: Dp = 14.dp,
    referenceLabels: List<ReferenceLabelInfo> = emptyList()
) {
    CartesianGraphBase(
        modifier = modifier,
        maxX = 100f,
        maxY = 160f,
        labelInterval = labelInterval,
        lines = lines,
        currentPH = currentPH,
        currentCO2 = currentCO2,
        phAxisLabelFontSize = phAxisLabelFontSize,
        referenceLabels = referenceLabels
    )
}

// Posibilidad 2: [H+] 0..160, CO2 0..160
@Composable
fun CartesianGraph100_160all(
    modifier: Modifier = Modifier,
    labelInterval: Float = 10f,
    lines: List<Pair<Pair<Float, Float>, Pair<Float, Float>>> = emptyList(),
    currentPH: Float,
    currentCO2: Float,
    phAxisLabelFontSize: Dp = 14.dp,
    referenceLabels: List<ReferenceLabelInfo> = emptyList()
) {
    CartesianGraphBase(
        modifier = modifier,
        maxX = 160f,
        maxY = 160f,
        labelInterval = labelInterval,
        lines = lines,
        currentPH = currentPH,
        currentCO2 = currentCO2,
        phAxisLabelFontSize = phAxisLabelFontSize,
        referenceLabels = referenceLabels
    )
}

// Posibilidad 3: [H+] 0..100, CO2 0..160
@Composable
fun CartesianGraghCO2100_160_H0_100(
    modifier: Modifier = Modifier,
    labelInterval: Float = 10f,
    lines: List<Pair<Pair<Float, Float>, Pair<Float, Float>>> = emptyList(),
    currentPH: Float,
    currentCO2: Float,
    phAxisLabelFontSize: Dp = 14.dp,
    referenceLabels: List<ReferenceLabelInfo> = emptyList()
) {
    CartesianGraphBase(
        modifier = modifier,
        maxX = 160f,
        maxY = 100f,
        labelInterval = labelInterval,
        lines = lines,
        currentPH = currentPH,
        currentCO2 = currentCO2,
        phAxisLabelFontSize = phAxisLabelFontSize,
        referenceLabels = referenceLabels//internet
    )
}