import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    toPO2venous: () -> Unit
) {
    val text = viewModel.resultText// Obtener el texto actual del ViewModel
    val screenData = ScreenData(text.value) // Crear un objeto ScreenData con el texto actual
    val scrollerState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollerState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = screenData.text,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        ) // Mostrar el texto actual

        Button(onClick = { toScreen1() }) {
            Text("Ir a Pantalla 1")
        }

        Button(onClick = { toScreen2() }) {
            Text("Ir a Pantalla 2")
        }
        Button(onClick = { toAcidoBase() }) {
            Text("Ir a Acido Base")
        }
        Button(onClick = { toCalcium() }) {
            Text("Ir a Calcio")
        }
        Button(onClick = { toCirculationMetabolism() }) {
            Text("Ir a Circulacion y Metabolica")
        }
        Button(onClick = { toBicarbonate() }) {
            Text("Ir a Bicarbonato")
        }
        Button(onClick = { toHemoglobin() }) {
            Text("Ir a Hemoglobina")
        }
        Button(onClick = { toPO2arterial() }) {
            Text("Ir a PO2arterial")
        }
        Button(onClick = { toPO2venous() }) {
            Text("Ir a PO2venoso")
        }

    }
}
