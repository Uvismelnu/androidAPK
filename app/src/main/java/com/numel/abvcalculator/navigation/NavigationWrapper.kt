import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.numel.abvcalculator.screens.AcidoBase
import com.numel.abvcalculator.screens.Bicarbonate
import com.numel.abvcalculator.screens.Calcium
import com.numel.abvcalculator.screens.Chlorine
import com.numel.abvcalculator.screens.CirculationMetabolism
import com.numel.abvcalculator.screens.ClassifyHemoglobin
import com.numel.abvcalculator.screens.ClassifyPaCO2
import com.numel.abvcalculator.screens.ClassifyPaO2
import com.numel.abvcalculator.screens.ClassifyPvCO2
import com.numel.abvcalculator.screens.ClassifySaO2
import com.numel.abvcalculator.screens.EvaluateSvO2
import com.numel.abvcalculator.screens.OxygenConsump
import com.numel.abvcalculator.screens.Potassium
import com.numel.abvcalculator.screens.Sodium

@Composable
fun NavigationWrapper(viewModel: MainViewModel, modifier: Modifier) {
    val screenData = ScreenData(viewModel.text.value)
    val screenData1 = ScreenData1(
        viewModel.txPharterial.value,
        viewModel.txCO2arterial.value,
        viewModel.txNa.value,
        viewModel.txK.value,
        viewModel.txCl.value,
        viewModel.txCa.value,
        viewModel.txHb.value,
        viewModel.txHCO3.value,
        viewModel.txSaO2.value,
        viewModel.txSvO2.value,
        viewModel.txPO2arterial.value,
        viewModel.txCO2venoso.value
    )
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Main) {
        composable<Main> {
            MainScreen(viewModel,
                { navController.navigate(Screen1) },
                { navController.navigate(Screen2) },
                { navController.navigate(AcidoBase) },
                {navController.navigate(CalciumOb)},
                {navController.navigate(CirculationMetabolismOb)},
                {navController.navigate(BicarbonateOb)},
                {navController.navigate(HemoglobinOb)},
                {navController.navigate(ClassifyPaO2Ob)},
                {navController.navigate(ClassifyPvO2Ob)},
                {navController.navigate(ClassifySaO2Ob)},
                {navController.navigate(ChlorineOb)},
                {navController.navigate(EvaluateSvO2Ob)},
                {navController.navigate(PotassiumOb)},
                {navController.navigate(SodiumOb)},
                {navController.navigate(OxygenConsumpOb)},
                {navController.navigate(ClassifyPaCO2Ob)})
        }
        composable<Screen1> {

            Screen1(navController, screenData, viewModel)
        }
        composable<Screen2> {

            Screen2(navController, screenData, viewModel)
        }
        composable<AcidoBase> {

            AcidoBase(navController, screenData,screenData1, viewModel)
        }
        composable<CalciumOb> {

            Calcium(navController, screenData,screenData1, viewModel)
        }
        composable<CirculationMetabolismOb> {

            CirculationMetabolism(navController, screenData,screenData1, viewModel)

        }
        composable<BicarbonateOb> {

            Bicarbonate(navController, screenData,screenData1, viewModel)

        }
        composable<HemoglobinOb> {

            ClassifyHemoglobin(navController, screenData,screenData1, viewModel)

        }
        composable<ClassifyPaO2Ob> {

            ClassifyPaO2(navController, screenData,screenData1, viewModel)

        }
        composable<ClassifyPvO2Ob> {

            ClassifyPvCO2(navController, screenData,screenData1, viewModel)

        }
        composable<ClassifySaO2Ob> {

            ClassifySaO2(navController, screenData, screenData1, viewModel)
        }
        composable<ChlorineOb> {
            Chlorine(navController, screenData,screenData1, viewModel)
        }
        composable<EvaluateSvO2Ob> {
            EvaluateSvO2(navController, screenData,screenData1, viewModel)
        }
        composable<PotassiumOb> {
            Potassium(navController, screenData,screenData1, viewModel)
        }
        composable<SodiumOb> {
            Sodium(navController, screenData,screenData1, viewModel)
        }
        composable<OxygenConsumpOb> {
            OxygenConsump(navController, screenData,screenData1, viewModel)
        }
        composable<ClassifyPaCO2Ob> {
            ClassifyPaCO2(navController, screenData,screenData1, viewModel)
        }
    }
}
