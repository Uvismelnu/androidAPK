package com.numel.abvcalculator.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.numel.abvcalculator.ui.theme.Pink40
import com.numel.abvcalculator.ui.theme.Pink80
import com.numel.abvcalculator.ui.theme.crema
import com.numel.abvcalculator.ui.theme.marron
import com.numel.abvcalculator.ui.theme.verde
import kotlin.math.log10
import kotlin.math.pow

@Composable
fun CartesianGraph(
    modifier: Modifier = Modifier,
    maxDataValue: Float, // The maximum value in your data range
    labelInterval: Float, // The interval at which you want to place labels
    lineStart: Pair<Float, Float>, // Starting point (x1, y1) of the line
    lineEnd: Pair<Float, Float>, // Ending point (x2, y2) of the line
    lineStart1: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd1: Pair<Float, Float>,
    lineStart2: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd2: Pair<Float, Float>,
    lineStart3: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd3: Pair<Float, Float>,
    lineStart4: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd4: Pair<Float, Float>,
    lineStart5: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd5: Pair<Float, Float>,
    lineStart6: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd6: Pair<Float, Float>,
    lineStart7: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd7: Pair<Float, Float>,
    lineStart8: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd8: Pair<Float, Float>,
    lineStart9: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd9: Pair<Float, Float>,
    lineStart10: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd10: Pair<Float, Float>,
    lineStart11: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd11: Pair<Float, Float>,
    lineStart12: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd12: Pair<Float, Float>,
    lineStart13: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd13: Pair<Float, Float>,
    lineStart14: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd14: Pair<Float, Float>,
    lineStart15: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd15: Pair<Float, Float>,
    lineStart16: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd16: Pair<Float, Float>,
    lineStart17: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd17: Pair<Float, Float>,
    lineStart18: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd18: Pair<Float, Float>,
    lineStart19: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd19: Pair<Float, Float>,
    lineStart20: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd20: Pair<Float, Float>,
    lineStart21: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd21: Pair<Float, Float>,
    lineStart22: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd22: Pair<Float, Float>,
    lineStart23: Pair<Float, Float>, // Starting point (x1, y1) of the lineUltima
    lineEnd23: Pair<Float, Float>,
    phPosicion: Float,
    phsize: Float,
    pH: Float,
    co2Posicion: Float

) {


    val canvasDp = 300.dp // Your canvas size in dp
    val canvasPx = with(LocalDensity.current) { canvasDp.toPx() }
    val scaleFactor = canvasPx / maxDataValue // Calculate scale factor based on max value


    Column {

        Canvas(modifier = modifier) {
            // Draw x-axis
            drawLine(
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                color = Color.Black
            )
            // Draw y-axis
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                color = Color.Black
            )
            // segunda linea borde derecho acidosis
            val startbd2 = Pair(17.96111f, 81.04058f)
            val endbd2 = Pair(17.96651f, 100f)
            // primera linea borde izquierdo acidosis
            val startbi1 = Pair(35.0f, 41.02029f)
            val endbi1 = Pair(28.493114f, 41.00869f)
            // segunda linea borde izquierdo acidosis met
            val startseg = Pair(28.493114f, 41.00869f)
            val endbseg = Pair(10.942479f, 75.23188f)
            // tercera linea borde izquierdo acidosis met
            val endb3ra = Pair(10.831758f, 100f)
            // linea inferior acidosis respiratoria cronica
            val startarci = Pair(45f, 37f)
            val endarci = Pair(100f, 52.927536f)
            //Cruce de alcalosis metabolica linea inferior acidosis respiratoria cronica
            val startalcam = Pair(51.24459f, 38.80838f)
            //Linea superior alcalosis metabolica final
            val startalcamfinal = Pair(60.21334f, 35.24637f)
            val endalcamfinal = Pair(62.39542f, 19.82497f)
            //Linea inferior alcalosis metabolica inicial
            val startalcaminicial = Pair(35.2f, 35.48134f)
            val endalcaminicial = Pair(36.52984f, 30.95652f)
            //Linea inferior alcalosis metabolica ultima
            val endalcamfin = Pair(51.12936f, 16.24539f)
            //Linea superior acidosis respiratoria cronica
            val startarc = Pair(45f, 43f)
            val endarc = Pair(100f, 66.63768f)
            //Linea superior acidosis respiratoria aguda
            val startara = Pair(45f, 44.66836f)
            val endara = Pair(100f, 91.47f)
            //Linea inferior alcalosis respiratoria aguda
            val startalra = Pair(11.766135f, 16.37681f)
            val endalra = Pair(35f, 35.48134f)
            //Linea izquierdo alcalosis respiratoria aguda
            val endalraiz = Pair(10.847961f, 24.31884f)
            //Linea superior alcalosis respiratoria aguda
            val endalras = Pair(35f, 40.550724f)
            //Linea inferior acidosis respiratoria aguda
            val startarai = Pair(55.831017f, 47.65491f)
            val endarai = Pair(100f, 82.46376f)
            //Linea inferior alcalosis respiratoria cronica
            val startarcr = Pair(11.064002f, 27.2463f)
            val endarcr = Pair(35f, 40.86956f)
            //Linea izquierda alcalosis respiratoria cronica
            val endarcriz = Pair(11.415068f, 37.855072f)
            //Poligono acidosis metablica
            val composePath = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(endbd2.first * scaleFactor, size.height - (endbd2.second * scaleFactor))
                lineTo(startbd2.first * scaleFactor, size.height - (startbd2.second * scaleFactor))
                lineTo(35f * scaleFactor, size.height - (44.66836f * scaleFactor))
                lineTo(startbi1.first * scaleFactor, size.height - (startbi1.second * scaleFactor))
                lineTo(
                    x = startseg.first * scaleFactor,
                    y = size.height - (startseg.second * scaleFactor)
                )
                lineTo(
                    x = endbi1.first * scaleFactor,
                    y = size.height - (endbi1.second * scaleFactor)
                )
                lineTo(
                    x = endbseg.first * scaleFactor,
                    y = size.height - (endbseg.second * scaleFactor)
                )
                lineTo(
                    x = endb3ra.first * scaleFactor,
                    y = size.height - (endb3ra.second * scaleFactor)
                )
                close() // Close the path to form a polygon
            }
            //Leyenda acidosis metabolica
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(-5f * scaleFactor, (size.height - (-45f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(composePath, Color.Gray)
                // Calculate text position
                val bounds = composePath.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "acidosis metabolica",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.GRAY
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = composePath,
                color = Color.Gray, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = composePath,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            //Poligono acidosis respiratoria aguda
            val acidrespaguda = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(
                    x = endara.first * scaleFactor,
                    y = size.height - (endara.second * scaleFactor)
                )
                lineTo(
                    x = startara.first * scaleFactor,
                    y = size.height - (startara.second * scaleFactor)
                )
                lineTo(
                    x = startarc.first * scaleFactor,
                    y = size.height - (startarc.second * scaleFactor)
                )
                lineTo(
                    x = startarai.first * scaleFactor,
                    y = size.height - (startarai.second * scaleFactor)
                )
                lineTo(
                    x = endarai.first * scaleFactor,
                    y = size.height - (endarai.second * scaleFactor)
                )
                close() // Close the path to form a polygon
            }
            //Leyenda acidosis respiratoria aguda
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(45f * scaleFactor, (size.height - (-60f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(acidrespaguda, crema)
                // Calculate text position
                val bounds = acidrespaguda.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "acidosis respiratoria aguda",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.rgb(205, 157, 78)
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = acidrespaguda,
                color = crema, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = acidrespaguda,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            //Poligono acidosis respiratoria cronica
            val acidrespcronica = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(
                    x = endarc.first * scaleFactor,
                    y = size.height - (endarc.second * scaleFactor)
                )
                lineTo(
                    x = startarc.first * scaleFactor,
                    y = size.height - (startarc.second * scaleFactor)
                )
                lineTo(
                    x = startarci.first * scaleFactor,
                    y = size.height - (startarci.second * scaleFactor)
                )
                lineTo(
                    x = endarci.first * scaleFactor,
                    y = size.height - (endarci.second * scaleFactor)
                )

                close() // Close the path to form a polygon
            }
            //Leyenda acidosis respiratoria cronica
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(-25f * scaleFactor, (size.height - (-75f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(acidrespcronica, marron)
                // Calculate text position
                val bounds = acidrespcronica.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "acidosis resp. cronica",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.rgb(78, 7, 7)
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = acidrespcronica,
                color = marron, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = acidrespcronica,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            //Poligono alcalosis metabolica
            val alcalosismetabolica = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(
                    x = endalcamfinal.first * scaleFactor,
                    y = size.height - (endalcamfinal.second * scaleFactor)
                )
                lineTo(
                    x = startalcamfinal.first * scaleFactor,
                    y = size.height - (startalcamfinal.second * scaleFactor)
                )
                lineTo(
                    x = startalcam.first * scaleFactor,
                    y = size.height - (startalcam.second * scaleFactor)
                )
                lineTo(
                    x = startarci.first * scaleFactor,
                    y = size.height - (startarci.second * scaleFactor)
                )
                lineTo(45f * scaleFactor, size.height - (35.48134f * scaleFactor))
                lineTo(
                    x = startalcaminicial.first * scaleFactor,
                    y = size.height - (startalcaminicial.second * scaleFactor)
                )
                lineTo(
                    x = endalcaminicial.first * scaleFactor,
                    y = size.height - (endalcaminicial.second * scaleFactor)
                )
                lineTo(
                    x = endalcamfin.first * scaleFactor,
                    y = size.height - (endalcamfin.second * scaleFactor)
                )
                close() // Close the path to form a polygon
            }
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(-20f * scaleFactor, (size.height - (-50f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(alcalosismetabolica, Pink40)
                // Calculate text position
                val bounds = alcalosismetabolica.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "alcalosis metabolica",
                    textX,
                    textY,
                    Paint().apply {
                        color = Pink40.toArgb()
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = alcalosismetabolica,
                color = Pink40, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = alcalosismetabolica,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            //Poligono alcalosis respiratoria aguda
            val alcalosisrespaguda = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(
                    x = endalra.first * scaleFactor,
                    y = size.height - (endalra.second * scaleFactor)
                )
                lineTo(
                    x = startalra.first * scaleFactor,
                    y = size.height - (startalra.second * scaleFactor)
                )
                lineTo(
                    x = endalraiz.first * scaleFactor,
                    y = size.height - (endalraiz.second * scaleFactor)
                )
                lineTo(
                    x = endalras.first * scaleFactor,
                    y = size.height - (endalras.second * scaleFactor)
                )
                close() // Close the path to form a polygon
            }
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(70f * scaleFactor, (size.height - (-30f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(alcalosisrespaguda, Color.Blue)
                // Calculate text position
                val bounds = alcalosisrespaguda.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "alcalosis respiratoria aguda",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.BLUE
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = alcalosisrespaguda,
                color = Color.Blue, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = alcalosisrespaguda,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            //Poligono alcalosis respiratoria cronica
            val alcalosisrescronica = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(
                    x = endarcr.first * scaleFactor,
                    y = size.height - (endarcr.second * scaleFactor)
                )
                lineTo(
                    x = startarcr.first * scaleFactor,
                    y = size.height - (startarcr.second * scaleFactor)
                )
                lineTo(
                    x = endarcriz.first * scaleFactor,
                    y = size.height - (endarcriz.second * scaleFactor)
                )
                close() // Close the path to form a polygon
            }
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(65f * scaleFactor, (size.height - (-70f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(alcalosisrescronica, Color.Magenta)
                // Calculate text position
                val bounds = alcalosisrescronica.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "alcalosis respiratoria cronica",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.MAGENTA
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = alcalosisrescronica,
                color = Color.Magenta, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = alcalosisrescronica,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            //Poligono normal
            val normal = Path().apply {
                // Define the points for your polygon (delimited area)
                moveTo(
                    x = 35f * scaleFactor,
                    y = size.height - (44.66836f * scaleFactor)
                )
                lineTo(
                    x = 45f * scaleFactor,
                    y = size.height - (44.66836f * scaleFactor)
                )
                lineTo(
                    x = 45f * scaleFactor,
                    y = size.height - (35.48134f * scaleFactor)
                )
                lineTo(35f * scaleFactor, size.height - (35.48134f * scaleFactor))

                close() // Close the path to form a polygon
            }
            scale(
                scaleX = 0.25f,
                scaleY = 0.25f,
                pivot = Offset(45f * scaleFactor, (size.height - (-85f * scaleFactor)))
            ) {// Draw the scaled polygon
                drawPath(normal, Color.Red)
                // Calculate text position
                val bounds = normal.getBounds() // Get bounds of the scaled polygon
                val textX = bounds.right + 10f // Adjust spacing as needed
                val textY = bounds.top + (bounds.bottom - bounds.top) / 2 // Vertical center
                // Draw the text
                drawContext.canvas.nativeCanvas.drawText(
                    "Normal",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.RED
                        textSize = 16f * scaleFactor // Adjust text size as needed
                    }
                )
            }
            // Draw the path with custom colors
            drawPath(
                path = normal,
                color = Color.Red, // Fill color
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // You can also draw the edges of the polygon (optional)
            drawPath(
                path = normal,
                color = Color.Black, // Edge color
                style = Stroke(width = 2f)
            )
            // Draw line between two points 1
            val start = Offset(
                x = lineStart.first * scaleFactor,
                y = size.height - (lineStart.second * scaleFactor)
            )
            val end = Offset(
                x = lineEnd.first * scaleFactor,
                y = size.height - (lineEnd.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start,
                end = end,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 2
            val start1 = Offset(
                x = lineStart1.first * scaleFactor,
                y = size.height - (lineStart1.second * scaleFactor)
            )
            val end1 = Offset(
                x = lineEnd1.first * scaleFactor,
                y = size.height - (lineEnd1.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start1,
                end = end1,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 3
            val start2 = Offset(
                x = lineStart2.first * scaleFactor,
                y = size.height - (lineStart2.second * scaleFactor)
            )
            val end2 = Offset(
                x = lineEnd2.first * scaleFactor,
                y = size.height - (lineEnd2.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start2,
                end = end2,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 4
            val start3 = Offset(
                x = lineStart3.first * scaleFactor,
                y = size.height - (lineStart3.second * scaleFactor)
            )
            val end3 = Offset(
                x = lineEnd3.first * scaleFactor,
                y = size.height - (lineEnd3.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start3,
                end = end3,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 5
            val start4 = Offset(
                x = lineStart4.first * scaleFactor,
                y = size.height - (lineStart4.second * scaleFactor)
            )
            val end4 = Offset(
                x = lineEnd4.first * scaleFactor,
                y = size.height - (lineEnd4.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start4,
                end = end4,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 6
            val start5 = Offset(
                x = lineStart5.first * scaleFactor,
                y = size.height - (lineStart5.second * scaleFactor)
            )
            val end5 = Offset(
                x = lineEnd5.first * scaleFactor,
                y = size.height - (lineEnd5.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start5,
                end = end5,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 7
            val start6 = Offset(
                x = lineStart6.first * scaleFactor,
                y = size.height - (lineStart6.second * scaleFactor)
            )
            val end6 = Offset(
                x = lineEnd6.first * scaleFactor,
                y = size.height - (lineEnd6.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start6,
                end = end6,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 8
            val start7 = Offset(
                x = lineStart7.first * scaleFactor,
                y = size.height - (lineStart7.second * scaleFactor)
            )
            val end7 = Offset(
                x = lineEnd7.first * scaleFactor,
                y = size.height - (lineEnd7.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start7,
                end = end7,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 9
            val start8 = Offset(
                x = lineStart8.first * scaleFactor,
                y = size.height - (lineStart8.second * scaleFactor)
            )
            val end8 = Offset(
                x = lineEnd8.first * scaleFactor,
                y = size.height - (lineEnd8.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start8,
                end = end8,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 10
            val start9 = Offset(
                x = lineStart9.first * scaleFactor,
                y = size.height - (lineStart9.second * scaleFactor)
            )
            val end9 = Offset(
                x = lineEnd9.first * scaleFactor,
                y = size.height - (lineEnd9.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start9,
                end = end9,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 11
            val start10 = Offset(
                x = lineStart10.first * scaleFactor,
                y = size.height - (lineStart10.second * scaleFactor)
            )
            val end10 = Offset(
                x = lineEnd10.first * scaleFactor,
                y = size.height - (lineEnd10.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start10,
                end = end10,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 12
            val start11 = Offset(
                x = lineStart11.first * scaleFactor,
                y = size.height - (lineStart11.second * scaleFactor)
            )
            val end11 = Offset(
                x = lineEnd11.first * scaleFactor,
                y = size.height - (lineEnd11.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start11,
                end = end11,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 13
            val start12 = Offset(
                x = lineStart12.first * scaleFactor,
                y = size.height - (lineStart12.second * scaleFactor)
            )
            val end12 = Offset(
                x = lineEnd12.first * scaleFactor,
                y = size.height - (lineEnd12.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start12,
                end = end12,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 14
            val start13 = Offset(
                x = lineStart13.first * scaleFactor,
                y = size.height - (lineStart13.second * scaleFactor)
            )
            val end13 = Offset(
                x = lineEnd13.first * scaleFactor,
                y = size.height - (lineEnd13.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start13,
                end = end13,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 15
            val start14 = Offset(
                x = lineStart14.first * scaleFactor,
                y = size.height - (lineStart14.second * scaleFactor)
            )
            val end14 = Offset(
                x = lineEnd14.first * scaleFactor,
                y = size.height - (lineEnd14.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start14,
                end = end14,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 16
            val start15 = Offset(
                x = lineStart15.first * scaleFactor,
                y = size.height - (lineStart15.second * scaleFactor)
            )
            val end15 = Offset(
                x = lineEnd15.first * scaleFactor,
                y = size.height - (lineEnd15.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start15,
                end = end15,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 17
            val start16 = Offset(
                x = lineStart16.first * scaleFactor,
                y = size.height - (lineStart16.second * scaleFactor)
            )
            val end16 = Offset(
                x = lineEnd16.first * scaleFactor,
                y = size.height - (lineEnd16.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start16,
                end = end16,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 18
            val start17 = Offset(
                x = lineStart17.first * scaleFactor,
                y = size.height - (lineStart17.second * scaleFactor)
            )
            val end17 = Offset(
                x = lineEnd17.first * scaleFactor,
                y = size.height - (lineEnd17.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start17,
                end = end17,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 19
            val start18 = Offset(
                x = lineStart18.first * scaleFactor,
                y = size.height - (lineStart18.second * scaleFactor)
            )
            val end18 = Offset(
                x = lineEnd18.first * scaleFactor,
                y = size.height - (lineEnd18.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start18,
                end = end18,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 20
            val start19 = Offset(
                x = lineStart19.first * scaleFactor,
                y = size.height - (lineStart19.second * scaleFactor)
            )
            val end19 = Offset(
                x = lineEnd18.first * scaleFactor,
                y = size.height - (lineEnd19.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start19,
                end = end19,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 21
            val start20 = Offset(
                x = lineStart20.first * scaleFactor,
                y = size.height - (lineStart20.second * scaleFactor)
            )
            val end20 = Offset(
                x = lineEnd20.first * scaleFactor,
                y = size.height - (lineEnd20.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start20,
                end = end20,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 22
            val start21 = Offset(
                x = lineStart21.first * scaleFactor,
                y = size.height - (lineStart21.second * scaleFactor)
            )
            val end21 = Offset(
                x = lineEnd21.first * scaleFactor,
                y = size.height - (lineEnd21.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start21,
                end = end21,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 23
            val start22 = Offset(
                x = lineStart22.first * scaleFactor,
                y = size.height - (lineStart22.second * scaleFactor)
            )
            val end22 = Offset(
                x = lineEnd22.first * scaleFactor,
                y = size.height - (lineEnd22.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start22,
                end = end22,
                strokeWidth = 1.dp.toPx()
            )
            // Draw line between two points 24
            val start23 = Offset(
                x = lineStart23.first * scaleFactor,
                y = size.height - (lineStart23.second * scaleFactor)
            )
            val end23 = Offset(
                x = lineEnd23.first * scaleFactor,
                y = size.height - (lineEnd23.second * scaleFactor)
            )
            drawLine(
                color = verde,
                start = start23,
                end = end23,
                strokeWidth = 1.dp.toPx()
            )
            // Draw grid lines and labels
            for (i in 0..(maxDataValue / labelInterval).toInt()) {
                val x = i * labelInterval * scaleFactor
                val y = i * labelInterval * scaleFactor
                // Horizontal grid line
                drawLine(
                    start = Offset(0f, size.height - y),
                    end = Offset(size.width, size.height - y),
                    color = Color.LightGray
                )
                // Vertical grid line
                drawLine(
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    color = Color.LightGray
                )
                // Label for x-axis
                drawContext.canvas.nativeCanvas.drawText(
                    (10 * i).toString(),
                    x,
                    size.height + 20.dp.toPx(), // Adjust text position as needed
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 12.dp.toPx()
                    }
                )
                // Label for y-axis
                drawContext.canvas.nativeCanvas.drawText(
                    (10 * i).toString(),
                    -30.dp.toPx(), // Adjust text position as needed
                    size.height - y,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 12.dp.toPx()
                    }
                )
            }
// Draw the text on the canvas9
            val paint9 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x9 = 35.87937f * scaleFactor
            val y9 = size.height - (95f * scaleFactor)
            val text9 = "9"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text9, x9, y9, paint9)
            }
            // Draw the text on the canvas33
            val paint33 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x33 = 95f * scaleFactor
            val y33 = size.height - (68.601074f * scaleFactor)
            val text33 = "33"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text33, x33, y33, paint33)
            }
            // Draw the text on the canvas45
            val paint45 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x45 = 95f * scaleFactor
            val y45 = size.height - (50.30745f * scaleFactor)
            val text45 = "45"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text45, x45, y45, paint45)
            }
            // Draw the text on the canvas75
            val paint75 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.WHITE
            }
            // Specify the position and style of the text
            val x75 = 95f * scaleFactor
            val y75 = size.height - (30.18447f * scaleFactor)
            val text75 = "75"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text75, x75, y75, paint75)
            }
            //texto del bicarbonato
            val paintb = Paint().apply {
                isAntiAlias = true
                textSize = 34f
                color = android.graphics.Color.BLACK
            }
            val xb = 2f * scaleFactor
            val yb = size.height - (21.7852f * scaleFactor)
            val textb = "HCO3(mEq/L)"
            drawIntoCanvas { canvas ->
                canvas.save()
                canvas.rotate(46.5f, xb, yb)
                canvas.nativeCanvas.drawText(textb, xb, yb, paintb)
                canvas.restore()
            }
            //texto de CO2
            val paintco2 = Paint().apply {
                isAntiAlias = true
                textSize = 40f
                color = android.graphics.Color.BLACK
            }
            val xco2 = 46f * scaleFactor
            val yco2 = size.height - (0f * scaleFactor)
            val textco2 = "CO2(mmHg)"
            drawIntoCanvas { canvas ->
                canvas.save()
                canvas.rotate(0f, xco2, yco2)
                canvas.nativeCanvas.drawText(textco2, xco2, yco2, paintco2)
                canvas.restore()
            }
            //texto de eje pH
            val paintph = Paint().apply {
                isAntiAlias = true
                textSize = phsize
                color = android.graphics.Color.MAGENTA
            }
            val xph = phPosicion * scaleFactor
            val yph = size.height
            val textph = "pH"
            drawIntoCanvas { canvas ->
                canvas.save()
                canvas.rotate(-90f, xph, yph)
                canvas.nativeCanvas.drawText(textph, xph, yph, paintph)
                canvas.restore()
            }
            //texto de [H+]
            val painth = Paint().apply {
                isAntiAlias = true
                textSize = 40f
                color = android.graphics.Color.BLACK
            }
            val xh = 0f * scaleFactor
            val yh = size.height - (46f * scaleFactor)
            val texth = "[H+](nEq/L)"
            drawIntoCanvas { canvas ->
                canvas.save()
                canvas.rotate(-90f, xh, yh)
                canvas.nativeCanvas.drawText(texth, xh, yh, painth)
                canvas.restore()
            }
            // Draw the text on the canvas72
            val paint72 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x72 = 95f * scaleFactor
            val y72 = size.height - (31.44216f * scaleFactor)
            val text72 = "72"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text72, x72, y72, paint72)
            }
            // Draw the text on the canvas69
            val paint69 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x69 = 95f * scaleFactor
            val y69 = size.height - (32.80921f * scaleFactor)
            val text69 = "69"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text69, x69, y69, paint69)
            }
            // Draw the text on the canvas66
            val paint66 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x66 = 95f * scaleFactor
            val y66 = size.height - (34.300537f * scaleFactor)
            val text66 = "66"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text66, x66, y66, paint66)
            }
            // Draw the text on the canvas63
            val paint63 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x63 = 95f * scaleFactor
            val y63 = size.height - (35.93389f * scaleFactor)
            val text63 = "63"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text63, x63, y63, paint63)
            }
            // Draw the text on the canvas60
            val paint60 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x60 = 95f * scaleFactor
            val y60 = size.height - (37.73059f * scaleFactor)
            val text60 = "60"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text60, x60, y60, paint60)
            }
            // Draw the text on the canvas57
            val paint57 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x57 = 95f * scaleFactor
            val y57 = size.height - (39.71641f * scaleFactor)
            val text57 = "57"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text57, x57, y57, paint57)
            }
            // Draw the text on the canvas54
            val paint54 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x54 = 95f * scaleFactor
            val y54 = size.height - (41.92288f * scaleFactor)
            val text54 = "54"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text54, x54, y54, paint54)
            }
            // Draw the text on the canvas51
            val paint51 = Paint().apply {
                isAntiAlias = true
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x51 = 95f * scaleFactor
            val y51 = size.height - (44.38893f * scaleFactor)
            val text51 = "51"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text51, x51, y51, paint51)
            }
            // Draw the text on the canvas48
            val paint48 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x48 = 95f * scaleFactor
            val y48 = size.height - (47.16324f * scaleFactor)
            val text48 = "48"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text48, x48, y48, paint48)
            }
            // Draw the text on the canvas42
            val paint42 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.WHITE
            }
            // Specify the position and style of the text
            val x42 = 95f * scaleFactor
            val y42 = size.height - (53.90084f * scaleFactor)
            val text42 = "42"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text42, x42, y42, paint42)
            }
            // Draw the text on the canvas39
            val paint39 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.WHITE
            }
            // Specify the position and style of the text
            val x39 = 95f * scaleFactor
            val y39 = size.height - (58.04706f * scaleFactor)
            val text39 = "39"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text39, x39, y39, paint39)
            }
            // Draw the text on the canvas36
            val paint36 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.WHITE
            }
            // Specify the position and style of the text
            val x36 = 95f * scaleFactor
            val y36 = size.height - (62.88432f * scaleFactor)
            val text36 = "36"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text36, x36, y36, paint36)
            }
            // Draw the text on the canvas30
            val paint30 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x30 = 95f * scaleFactor
            val y30 = size.height - (75.46118f * scaleFactor)
            val text30 = "30"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text30, x30, y30, paint30)
            }
            // Draw the text on the canvas27
            val paint27 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x27 = 95f * scaleFactor
            val y27 = size.height - (83.84576f * scaleFactor)
            val text27 = "27"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text27, x27, y27, paint27)
            }
            // Draw the text on the canvas24
            val paint24 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x24 = 95f * scaleFactor
            val y24 = size.height - (94.32647f * scaleFactor)
            val text24 = "24"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text24, x24, y24, paint24)
            }
            // Draw the text on the canvas21
            val paint21 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x21 = 83.71854f * scaleFactor
            val y21 = size.height - (95f * scaleFactor)
            val text21 = "21"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text21, x21, y21, paint21)
            }
            // Draw the text on the canvas18
            val paint18 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x18 = 71.75875f * scaleFactor
            val y18 = size.height - (95f * scaleFactor)
            val text18 = "18"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text18, x18, y18, paint18)
            }
            // Draw the text on the canvas15
            val paint15 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x15 = 59.79896f * scaleFactor
            val y15 = size.height - (95f * scaleFactor)
            val text15 = "15"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text15, x15, y15, paint15)
            }
            // Draw the text on the canvas12
            val paint12 = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x12 = 47.839165f * scaleFactor
            val y12 = size.height - (95f * scaleFactor)
            val text12 = "12"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text12, x12, y12, paint12)
            }
            // Draw the text on the canvas6
            val paint = Paint().apply {
                isAntiAlias = true
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
            // Specify the position and style of the text
            val x1 = 23.91958f * scaleFactor
            val y1 = size.height - (95f * scaleFactor)
            val text = "6"
            // Draw the text on the canvas
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(text, x1, y1, paint)
            }
            //Eje del pH
            val canvasWidth = size.width
            val points = listOf(
                Pair(100f, 5.71f),
                Pair(100f, 9.97f),
                Pair(100f, 17.83f),
                Pair(100f, 21.94f),
                Pair(100f, 26.84f),
                Pair(100f, 38.46f),
                Pair(100f, 45.36f),
                Pair(100f, 51.22f),
                Pair(100f, 57.04f),
                Pair(100f, 64.32f),
                Pair(100f, 81.3f),
                Pair(100f, 100f),
                Pair(100f, 90f),
                Pair(100f, 74f),
                Pair(100f, 34f)
            ) // Replace with your specific points
            val pointRadius = 5f // Radius of the marker circles
            // Custom paint for point markers
            val pointPaint = Paint().apply {
                color = android.graphics.Color.RED // Custom color for point markers
            }
            // Custom paint for point labels
            val pointLabelPaint = Paint().apply {
                color = android.graphics.Color.MAGENTA // Custom color for point labels
                textSize = 30f // Custom text size for point labels
                textAlign = Paint.Align.CENTER // Center text horizontally
            }
            // Draw point markers and labels
            points.forEach { point ->
                val yPosition =
                    size.height - (point.second * scaleFactor) // Subtract from height for y value
                // Convert canvas Y position to Cartesian Y value
                // val cartesianYValue = canvasYToCartesianY(yPosition, canvasHeight, maxYValue)
                // Draw marker circle
                drawContext.canvas.nativeCanvas.drawCircle(
                    canvasWidth,
                    yPosition,
                    pointRadius,
                    pointPaint
                )
                // Draw label text for the Cartesian Y value
                drawContext.canvas.nativeCanvas.drawText(
                    "%.2f".format(9 - log10(point.second.toDouble())), // Display as an integer value
                    canvasWidth + 35f, // X position (right of the marker)
                    yPosition, // Y position (aligned with the marker)
                    pointLabelPaint
                )
            }

            val xPosition= co2Posicion * scaleFactor
            val h = 10.0.pow(9 - pH.toDouble())
            val yPosition=size.height-(h * scaleFactor)
            val point = listOf(Offset(xPosition, yPosition.toFloat()))
            drawPoints(
                points = point,
                pointMode = PointMode.Points, // Modo para dibujar puntos individuales
                color = Pink80,
                strokeWidth = 20f // Grosor de los puntos
            )


        }
        Spacer(modifier = Modifier.height(170.dp))

    }


}



