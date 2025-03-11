import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.numel.abvcalculator.screens.AcidoBase
import com.numel.abvcalculator.screens.Bicarbonate
import com.numel.abvcalculator.screens.Calcium
import com.numel.abvcalculator.screens.CirculationMetabolism
import com.numel.abvcalculator.screens.ClassifyHemoglobin
import com.numel.abvcalculator.screens.ClassifyPaO2
import com.numel.abvcalculator.screens.ClassifyPvCO2

@Composable
fun NavigationWrapper(viewModel: MainViewModel, modifier: Modifier) {
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
                {navController.navigate(ClassifyPvO2Ob)})
        }
        composable<Screen1> {
            val screenData = ScreenData(viewModel.text.value)
            Screen1(navController, screenData, viewModel)
        }
        composable<Screen2> {
            val screenData = ScreenData(viewModel.text.value)
            Screen2(navController, screenData, viewModel)
        }
        composable<AcidoBase> {
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
            AcidoBase(navController, screenData,screenData1, viewModel)
        }
        composable<CalciumOb> {
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
            Calcium(navController, screenData,screenData1, viewModel)
        }
        composable<CirculationMetabolismOb> {
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
            CirculationMetabolism(navController, screenData,screenData1, viewModel)

        }
        composable<BicarbonateOb> {
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
            Bicarbonate(navController, screenData,screenData1, viewModel)

        }
        composable<HemoglobinOb> {
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
            ClassifyHemoglobin(navController, screenData,screenData1, viewModel)

        }
        composable<ClassifyPaO2Ob> {
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
            ClassifyPaO2(navController, screenData,screenData1, viewModel)

        }
        composable<ClassifyPvO2Ob> {
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
            ClassifyPvCO2(navController, screenData,screenData1, viewModel)

        }
    }
}
