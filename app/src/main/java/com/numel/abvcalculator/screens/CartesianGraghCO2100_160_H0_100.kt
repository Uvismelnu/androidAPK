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
import androidx.compose.ui.unit.dp
import com.numel.abvcalculator.ui.theme.Pink80
import com.numel.abvcalculator.ui.theme.verde
import kotlin.math.log10
import kotlin.math.pow


@Composable
fun CartesianGraghCO2100_160_H0_100(
    modifier: Modifier = Modifier,
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
    lineStart8: Pair<Float, Float>,
    lineEnd8: Pair<Float, Float>,
    lineStart9: Pair<Float, Float>,
    lineEnd9: Pair<Float, Float>,
    lineStart10: Pair<Float, Float>,
    lineEnd10: Pair<Float, Float>,
    lineStart11: Pair<Float, Float>,
    lineEnd11: Pair<Float, Float>,
    lineStart12: Pair<Float, Float>,
    lineEnd12: Pair<Float, Float>,
    lineStart13: Pair<Float, Float>,
    lineEnd13: Pair<Float, Float>,
    lineStart14: Pair<Float, Float>,
    lineEnd14: Pair<Float, Float>,
    lineStart15: Pair<Float, Float>,
    lineEnd15: Pair<Float, Float>,
    lineStart16: Pair<Float, Float>,
    lineEnd16: Pair<Float, Float>,
    lineStart17: Pair<Float, Float>,
    lineEnd17: Pair<Float, Float>,
    phsize: Float,
    pH: Float,
    co2Posicion: Float
) {

    // Nuevos rangos de los ejes X e Y
    val minX = 100f
    val maxX = 160f
    val minY = 0f
    val maxY = 100f
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
    fun createTextPaint(
        color: Int,
        textSize: Float,
        textAlign: Paint.Align = Paint.Align.LEFT
    ): Paint {
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
            drawLine(
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                color = Color.Black
            ) // Eje X
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                color = Color.Black
            ) // Eje Y

            // Dibujar líneas entre puntos
            val lines = listOf(
                Pair(lineStart, lineEnd),
                Pair(lineStart1, lineEnd1),
                Pair(lineStart2, lineEnd2),
                Pair(lineStart3, lineEnd3),
                Pair(lineStart4, lineEnd4),
                Pair(lineStart5, lineEnd5),
                Pair(lineStart6, lineEnd6),
                Pair(lineStart7, lineEnd7),
                Pair(lineStart8, lineEnd8),
                Pair(lineStart9, lineEnd9),
                Pair(lineStart10, lineEnd10),
                Pair(lineStart11, lineEnd11),
                Pair(lineStart12, lineEnd12),
                Pair(lineStart13, lineEnd13),
                Pair(lineStart14, lineEnd14),
                Pair(lineStart15, lineEnd15),
                Pair(lineStart16, lineEnd16),
                Pair(lineStart17, lineEnd17)
            )

            // Calcular pendientes y asociarlas con las líneas
            val slopesWithLines = lines.map { (startPair, endPair) ->
                val slope = (endPair.second - startPair.second) / (endPair.first - startPair.first)
                Triple(slope, startPair, endPair)
            }.sortedByDescending { it.first } // Ordenar por pendiente (de mayor a menor)

            // Textos a colocar en las líneas
            val texts = listOf(
                "24",
                "27",
                "30",
                "33",
                "36",
                "39",
                "42",
                "45",
                "48",
                "51",
                "54",
                "57",
                "60",
                "63",
                "66",
                "69",
                "72",
                "75"
            )

            // Dibujar líneas y textos
            slopesWithLines.forEachIndexed { index, (_, startPair, endPair) ->
                val startX = calculateXPosition(startPair.first, size.width)
                val startY = calculateYPosition(startPair.second, size.height)
                val endX = calculateXPosition(endPair.first, size.width)
                val endY = calculateYPosition(endPair.second, size.height)

                // Dibujar línea
                drawLine(
                    color = verde,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 1.dp.toPx()
                )

                // Dibujar texto en el extremo lineEnd
                if (index < texts.size) {
                    val text = texts[index]
                    val textValue = text.toInt() // Convertir el texto a entero para comparar

                    // Determinar el desplazamiento basado en el valor del texto
                    val xOffset = if (textValue in 24..36) {
                        3.dp.toPx() // Desplazamiento hacia la derecha
                    } else {
                        -12.dp.toPx() // Desplazamiento hacia la izquierda

                    }

                    drawContext.canvas.nativeCanvas.drawText(
                        text,
                        endX + xOffset, // Aplicar el desplazamiento
                        endY, // Posición vertical
                        createTextPaint(android.graphics.Color.BLACK, 9.dp.toPx(), Paint.Align.LEFT)
                    )
                }
            }

            // Dibujar líneas de cuadrícula y etiquetas del eje Y
            for (i in 0..(yRange / labelInterval).toInt()) {
                val yValue = minY + i * labelInterval
                val yPosition = calculateYPosition(yValue, size.height)
                drawLine(
                    start = Offset(0f, yPosition),
                    end = Offset(size.width, yPosition),
                    color = Color.LightGray
                )
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
                drawLine(
                    start = Offset(xPosition, 0f),
                    end = Offset(xPosition, size.height),
                    color = Color.LightGray
                )
                drawContext.canvas.nativeCanvas.drawText(
                    xValue.toInt().toString(),
                    xPosition,
                    size.height + 20.dp.toPx(),
                    createTextPaint(android.graphics.Color.BLACK, 12.dp.toPx(), Paint.Align.CENTER)
                )
            }

            // Dibujar texto "HCO3(mEq/L)"
            val xText = calculateXPosition(110f, size.width) // Posición ajustada al nuevo rango
            val yText = calculateYPosition(88f, size.height)
            drawRotatedText(
                text = "HCO3(mEq/L)",
                x = xText,
                y = yText,
                angle = -27.36f,
                paint = createTextPaint(verde.toArgb(), 30f)
            )

            // Dibujar texto "pH" en su posición original
            val xTexte = calculateXPosition(163f, size.width) // Posición ajustada al nuevo rango
            val yTexte = calculateYPosition(2f, size.height)
            drawRotatedText(
                text = "pH",
                x = xTexte,
                y = yTexte,
                angle = -90f,
                paint = createTextPaint(android.graphics.Color.MAGENTA, phsize, Paint.Align.CENTER)
            )

            // Dibujar texto "CO2(mmHg)"
            val xCO2 = calculateXPosition(102f, size.width) // Posición ajustada al nuevo rango
            val yCO2 = calculateYPosition(2f, size.height)
            drawContext.canvas.nativeCanvas.drawText(
                "CO2(mmHg)",
                xCO2,
                yCO2,
                createTextPaint(android.graphics.Color.BLACK, 30f)
            )

            // Dibujar texto "[H+](nEq/L)"
            val xH = calculateXPosition(102f, size.width) // Posición ajustada al nuevo rango
            val yH = calculateYPosition(5f, size.height)
            drawRotatedText(
                text = "[H+](nEq/L)",
                x = xH,
                y = yH,
                angle = -90f,
                paint = createTextPaint(android.graphics.Color.BLACK, 30f)
            )

            // Dibujar marcadores de puntos y etiquetas del eje pH
            val points = listOf(
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
                100f,
                90f,
                74f,
                34f
            )
            val pointRadius = 5f
            val pointPaint = createTextPaint(android.graphics.Color.RED, 0f)
            val pointLabelPaint =
                createTextPaint(android.graphics.Color.MAGENTA, 30f, Paint.Align.CENTER)
            points.forEach { yValuen ->
                val yPosition = calculateYPosition(yValuen, size.height)
                drawContext.canvas.nativeCanvas.drawCircle(
                    size.width,
                    yPosition,
                    pointRadius,
                    pointPaint
                )
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