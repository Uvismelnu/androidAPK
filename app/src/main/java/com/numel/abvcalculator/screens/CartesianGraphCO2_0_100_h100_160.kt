package com.numel.abvcalculator.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.numel.abvcalculator.ui.theme.Pink80
import com.numel.abvcalculator.ui.theme.verde
import kotlin.math.log10
import kotlin.math.pow


@Composable
fun CartesianGraphCO2_0_100_h100_160(
    modifier: Modifier = Modifier,
    maxDataValue: Float,
    labelInterval: Float,
    lineStart: Pair<Float, Float>,
    lineEnd: Pair<Float, Float>,
    lineStart1: Pair<Float, Float>,
    lineEnd1: Pair<Float, Float>,
    lineStart2: Pair<Float, Float>,
    lineEnd2: Pair<Float, Float>,
    lineStart3: Pair<Float, Float>,
    lineEnd3: Pair<Float, Float>,
    lineStart4: Pair<Float, Float>,
    lineEnd4: Pair<Float, Float>,
    lineStart5: Pair<Float, Float>,
    lineEnd5: Pair<Float, Float>,
    phsize: Float,
    pH: Float,
    co2Posicion: Float
) {

    val canvasDp = 300.dp
    val canvasPx = with(LocalDensity.current) { canvasDp.toPx() }
    val scaleFactor = canvasPx / maxDataValue

    // Rango del eje Y
    val minY = 100f
    val maxY = 160f
    val yRange = maxY - minY

    // Función para calcular la posición vertical
    fun calculateYPosition(yValue: Float, sizeHeight: Float): Float {
        return sizeHeight - ((yValue - minY) / yRange) * sizeHeight
    }

    // Función para crear un Paint reutilizable
    fun createTextPaint(color: Int, textSize: Float, textAlign: Paint.Align = Paint.Align.LEFT): Paint {
        return Paint().apply {
            isAntiAlias = true
            this.color = color
            this.textSize = textSize
            this.textAlign = textAlign
        }
    }

    // Función para dibujar texto rotado
    fun DrawScope.drawRotatedText(
        text: String,
        x: Float,
        y: Float,
        angle: Float,
        paint: Paint
    ) {
        drawContext.canvas.save()
        drawContext.canvas.rotate(angle, x, y)
        drawContext.canvas.nativeCanvas.drawText(text, x, y, paint)
        drawContext.canvas.restore()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = modifier) {
            // Dibujar ejes principales
            drawLine(start = Offset(0f, size.height), end = Offset(size.width, size.height), color = Color.Black) // Eje X
            drawLine(start = Offset(0f, 0f), end = Offset(0f, size.height), color = Color.Black) // Eje Y

            // Dibujar líneas entre puntos
            val lines = listOf(
                Pair(lineStart, lineEnd),
                Pair(lineStart1, lineEnd1),
                Pair(lineStart2, lineEnd2),
                Pair(lineStart3, lineEnd3),
                Pair(lineStart4, lineEnd4),
                Pair(lineStart5, lineEnd5)
            )
            lines.forEach { (startPair, endPair) ->
                val startX = startPair.first * scaleFactor
                val startY = calculateYPosition(startPair.second, size.height)
                val endX = endPair.first * scaleFactor
                val endY = calculateYPosition(endPair.second, size.height)
                drawLine(
                    color = verde,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Dibujar líneas de cuadrícula y etiquetas del eje Y
            for (i in 0..(yRange / labelInterval).toInt()) {
                val yValue = minY + i * labelInterval
                val yPosition = calculateYPosition(yValue, size.height)
                drawLine(start = Offset(0f, yPosition), end = Offset(size.width, yPosition), color = Color.LightGray)
                drawContext.canvas.nativeCanvas.drawText(
                    yValue.toInt().toString(),
                    -30.dp.toPx(),
                    yPosition,
                    createTextPaint(android.graphics.Color.BLACK, 12.dp.toPx())
                )
            }

            // Dibujar líneas de cuadrícula y etiquetas del eje X
            for (i in 0..(maxDataValue / labelInterval).toInt()) {
                val x = i * labelInterval * scaleFactor
                drawLine(start = Offset(x, 0f), end = Offset(x, size.height), color = Color.LightGray)
                drawContext.canvas.nativeCanvas.drawText(
                    (10 * i).toString(),
                    x,
                    size.height + 20.dp.toPx(),
                    createTextPaint(android.graphics.Color.BLACK, 12.dp.toPx())
                )
            }

            // Dibujar texto "HCO3(mEq/L)"
            val xText = 35f * scaleFactor
            val yText = calculateYPosition(150f, size.height)
            drawRotatedText(
                text = "HCO3(mEq/L)",
                x = xText,
                y = yText,
                angle = -79.38f,
                paint = createTextPaint(verde.toArgb(), 30f)
            )

            // Dibujar texto "pH" en su posición original
            val xTexte = 97f * scaleFactor
            val yTexte = calculateYPosition(100f, size.height)
            drawRotatedText(
                text = "pH",
                x = xTexte,
                y = yTexte,
                angle = -90f,
                paint = createTextPaint(android.graphics.Color.MAGENTA, phsize, Paint.Align.CENTER)
            )

            // Dibujar texto "CO2(mmHg)"
            val xCO2 = 3f * scaleFactor
            val yCO2 = calculateYPosition(101f, size.height)
            drawContext.canvas.nativeCanvas.drawText(
                "CO2(mmHg)",
                xCO2,
                yCO2,
                createTextPaint(android.graphics.Color.BLACK, 30f)
            )

            // Dibujar texto "[H+](nEq/L)"
            val xH = 3f * scaleFactor
            val yH = calculateYPosition(104f, size.height)
            drawRotatedText(
                text = "[H+](nEq/L)",
                x = xH,
                y = yH,
                angle = -90f,
                paint = createTextPaint(android.graphics.Color.BLACK, 30f)
            )

            // Dibujar etiquetas "15", "18", "21" en sus posiciones originales
            val labels = listOf(
                Triple(93f, 155f, "15"),
                Triple(93f, 132.3772f, "18"),
                Triple(93f, 113.4869f, "21")
            )
            labels.forEach { (xLabel, yLabel, labelText) ->
                val xPosition = xLabel * scaleFactor
                val yPosition = calculateYPosition(yLabel, size.height)
                drawContext.canvas.nativeCanvas.drawText(
                    labelText,
                    xPosition + 10f,
                    yPosition - 10f,
                    createTextPaint(verde.toArgb(), 30f, Paint.Align.LEFT)
                )
            }

            // Dibujar etiquetas "6", "9", "12" en sus posiciones originales
            val lineLabels = listOf(
                Pair(40.2925f, "6"),
                Pair(60.41f, "9"),
                Pair(80.54f, "12")
            )
            lineLabels.forEach { (xLabel, labelText) ->
                val yValuel = 160f // Extremo superior de las líneas
                val yPosition = calculateYPosition(yValuel, size.height)
                val xPosition = xLabel * scaleFactor
                drawContext.canvas.nativeCanvas.drawText(
                    labelText,
                    xPosition + 10f, // Desplazar el texto a la derecha de la línea
                    yPosition - 10f, // Desplazar el texto hacia arriba
                    createTextPaint(verde.toArgb(), 30f, Paint.Align.LEFT)
                )
            }

            // Dibujar marcadores de puntos y etiquetas del eje pH
            val points = listOf(
                104f, 110f, 107f, 115f, 120f, 125f, 130f, 135f, 140f, 145f, 150f, 155f, 160f, 100f, 112f
            )
            val pointRadius = 5f
            val pointPaint = createTextPaint(android.graphics.Color.RED, 0f)
            val pointLabelPaint = createTextPaint(android.graphics.Color.MAGENTA, 30f, Paint.Align.CENTER)

            points.forEach { yValuen ->
                val yPosition = calculateYPosition(yValuen, size.height)
                drawContext.canvas.nativeCanvas.drawCircle(size.width, yPosition, pointRadius, pointPaint)
                drawContext.canvas.nativeCanvas.drawText(
                    "%.2f".format(9 - log10(yValuen.toDouble())),
                    size.width + 35f,
                    yPosition,
                    pointLabelPaint
                )
            }

            // Dibujar el punto principal del gráfico
            val xPosition = co2Posicion * scaleFactor
            val h = 10.0.pow(9 - pH.toDouble())
            val yPosition = calculateYPosition(h.toFloat(), size.height)
            drawPoints(
                points = listOf(Offset(xPosition, yPosition)),
                pointMode = PointMode.Points,
                color = Pink80,
                strokeWidth = 20f
            )
        }
    }
}