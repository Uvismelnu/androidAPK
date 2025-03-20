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
fun CartesianGraph100_160all(
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
    lineStart6: Pair<Float, Float>,
    lineEnd6: Pair<Float, Float>,
    lineStart7: Pair<Float, Float>,
    lineEnd7: Pair<Float, Float>,
    phsize: Float,
    pH: Float,
    co2Posicion: Float
) {
    val canvasDp = 300.dp
    val canvasPx = with(LocalDensity.current) { canvasDp.toPx() }
    val scaleFactor = canvasPx / (maxDataValue - 100f) // Ajuste del factor de escala basado en el nuevo rango

    // Rangos de los ejes X e Y
    val minX = 100f
    val maxX = 160f
    val minY = 100f
    val maxY = 160f
    val xRange = maxX - minX
    val yRange = maxY - minY

    // Función para calcular la posición vertical (eje Y)
    fun calculateYPosition(yValue: Float, sizeHeight: Float): Float {
        return sizeHeight - ((yValue - minY) / yRange) * sizeHeight
    }

    // Función para calcular la posición horizontal (eje X)
    fun calculateXPosition(xValue: Float, sizeWidth: Float): Float {
        return ((xValue - minX) / xRange) * sizeWidth
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
                Pair(lineStart5, lineEnd5),
                Pair(lineStart6, lineEnd6),
                Pair(lineStart7, lineEnd7)
            )
            lines.forEach { (startPair, endPair) ->
                val startX = calculateXPosition(startPair.first, size.width)
                val startY = calculateYPosition(startPair.second, size.height)
                val endX = calculateXPosition(endPair.first, size.width)
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
            for (i in 0..(xRange / labelInterval).toInt()) {
                val xValue = minX + i * labelInterval
                val xPosition = calculateXPosition(xValue, size.width)
                drawLine(start = Offset(xPosition, 0f), end = Offset(xPosition, size.height), color = Color.LightGray)
                drawContext.canvas.nativeCanvas.drawText(
                    xValue.toInt().toString(),
                    xPosition,
                    size.height + 20.dp.toPx(),
                    createTextPaint(android.graphics.Color.BLACK, 12.dp.toPx(), Paint.Align.CENTER)
                )
            }

            // Dibujar texto "HCO3(mEq/L)"
            val xText = calculateXPosition(113f, size.width) // Posición ajustada al nuevo rango
            val yText = calculateYPosition(150f, size.height)
            drawRotatedText(
                text = "HCO3(mEq/L)",
                x = xText,
                y = yText,
                angle = -52.99f,
                paint = createTextPaint(verde.toArgb(), 30f)
            )

            // Dibujar texto "pH" en su posición original
            val xTexte = calculateXPosition(157f, size.width) // Posición ajustada al nuevo rango
            val yTexte = calculateYPosition(100f, size.height)
            drawRotatedText(
                text = "pH",
                x = xTexte,
                y = yTexte,
                angle = -90f,
                paint = createTextPaint(android.graphics.Color.MAGENTA, phsize, Paint.Align.CENTER)
            )

            // Dibujar texto "CO2(mmHg)"
            val xCO2 = calculateXPosition(103f, size.width) // Posición ajustada al nuevo rango
            val yCO2 = calculateYPosition(101f, size.height)
            drawContext.canvas.nativeCanvas.drawText(
                "CO2(mmHg)",
                xCO2,
                yCO2,
                createTextPaint(android.graphics.Color.BLACK, 30f)
            )

            // Dibujar texto "[H+](nEq/L)"
            val xH = calculateXPosition(103f, size.width) // Posición ajustada al nuevo rango
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
                Triple(156f, 158.88f, "24"),
                Triple(156f, 141.28f, "27"),
                Triple(156f, 127.04f, "30"),
                Triple(156f, 115.52f, "33"),
                Triple(156f, 105.92f, "36")

            )
            labels.forEach { (xLabel, yLabel, labelText) ->
                val xPosition = calculateXPosition(xLabel, size.width)
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
                Pair(100.70f, "15"),
                Pair(120.86f, "18"),
                Pair(140.96f, "21")
            )
            lineLabels.forEach { (xLabel, labelText) ->
                val yValuel = 160f // Extremo superior de las líneas
                val yPosition = calculateYPosition(yValuel, size.height)
                val xPosition = calculateXPosition(xLabel, size.width)
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
            val xPosition = calculateXPosition(co2Posicion, size.width)
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