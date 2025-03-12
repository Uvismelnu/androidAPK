import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Screen1

@Serializable
object Screen2

@Serializable
object AcidoBase

@Serializable
data class ScreenData(val text: String)

@Serializable
data class ScreenData1(val txPharterial: String,
val txCO2arterial: String,
val txNa: String,
val txK: String,
val txCl: String,
val txCa: String,
val txHb: String,
val txHCO3: String,
val txSaO2: String,
val txSvO2: String,
val txPO2arterial: String,
val txCO2venoso: String)

@Serializable
object CalciumOb

@Serializable
object CirculationMetabolismOb

@Serializable
object BicarbonateOb
@Serializable
object HemoglobinOb
@Serializable
object ClassifyPaO2Ob
@Serializable
object ClassifyPvO2Ob
@Serializable
object ClassifySaO2Ob
@Serializable
object ChlorineOb
@Serializable
object EvaluateSvO2Ob
@Serializable
object PotassiumOb
@Serializable
object SodiumOb
@Serializable
object OxygenConsumpOb
@Serializable
object ClassifyPaCO2Ob



