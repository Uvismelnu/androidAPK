package com.numel.abvcalculator.viewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var isScreen1Visible by mutableStateOf(false)
    var isScreenCO2100_160_H_0_100Visible by mutableStateOf(false)
    var isScreenCO2100_160_H_100_160Visible by mutableStateOf(false)
    var isScreenCO2_0_100_h100_160Visible by mutableStateOf(false)

    private val _selectedGender = mutableStateOf<Gender?>(null) // Inicialmente nulo
    val selectedGender: State<Gender?> = _selectedGender

    fun updateSelectedGender(newGender: Gender) {
        _selectedGender.value = newGender
    }


    private val _sharedPHa = mutableStateOf("")
    val sharedPHa: State<String> = _sharedPHa
    fun updateSharedPHa(newText: String) {
        _sharedPHa.value = newText
    }
    private val _sharedSO2a = mutableStateOf("")
    val sharedSO2a: State<String> = _sharedSO2a
    fun updateSharedSO2a(newText: String) {
        _sharedSO2a.value = newText
    }
    private val _sharedSO2v = mutableStateOf("")
    val sharedSO2v: State<String> = _sharedSO2v
    fun updateSharedSO2v(newText: String) {
        _sharedSO2v.value = newText
    }
    private val _sharedWeight = mutableStateOf("")
    val sharedWeight: State<String> = _sharedWeight
    fun updateSharedWeight(newText: String) {
        _sharedWeight.value = newText
    }
    private val _sharedHeight = mutableStateOf("")
    val sharedHeight: State<String> = _sharedHeight
    fun updateSharedHeight(newText: String) {
        _sharedHeight.value = newText
    }
    private val _sharedAge = mutableStateOf("")
    val sharedAge: State<String> = _sharedAge
    fun updateSharedAge(newText: String) {
        _sharedAge.value = newText
    }
    private val _sharedHeartRate = mutableStateOf("")
    val sharedHeartRate: State<String> = _sharedHeartRate
    fun updateSharedHeartRate(newText: String) {
        _sharedHeartRate.value = newText
    }
    private val _sharedVO2 = mutableStateOf("")
    val sharedVO2: State<String> = _sharedVO2
    fun updateSharedVO2(newText: String) {
        _sharedVO2.value = newText
    }
    private val _sharedBSA = mutableStateOf("")
    val sharedBSA: State<String> = _sharedBSA
    fun updateSharedBSA(newText: String) {
        _sharedBSA.value = newText
    }


    private val _sharedHCO3 = mutableStateOf("")
    val sharedHCO3: State<String> = _sharedHCO3
    fun updateSharedHCO3(newText: String) {
            _sharedHCO3.value = newText
    }
    private val _sharedCO2a = mutableStateOf("")
    val sharedCO2a: State<String> = _sharedCO2a
    fun updateSharedCO2a(newText: String) {
        _sharedCO2a.value = newText
    }
    private val _sharedNa = mutableStateOf("")
    val sharedNa: State<String> = _sharedNa
    fun updateSharedNa(newText: String) {
        _sharedNa.value = newText
    }
    private val _sharedK = mutableStateOf("")
    val sharedK: State<String> = _sharedK
    fun updateSharedK(newText: String) {
        _sharedK.value = newText
    }
    private val _sharedCl = mutableStateOf("")
    val sharedCl: State<String> = _sharedCl
    fun updateSharedCl(newText: String) {
        _sharedCl.value = newText
    }
    private val _sharedPO2a = mutableStateOf("")
    val sharedPO2a: State<String> = _sharedPO2a
    fun updateSharedPO2a(newText: String) {
        _sharedPO2a.value = newText
    }
    private val _sharedHb = mutableStateOf("")
    val sharedHb: State<String> = _sharedHb
    fun updateSharedHb(newText: String) {
        _sharedHb.value = newText
    }
    private val _sharedCO2v = mutableStateOf("")
    val sharedCO2v: State<String> = _sharedCO2v
    fun updateSharedCO2v(newText: String) {
        _sharedCO2v.value = newText
    }




    private val _txPharterial = mutableStateOf("")
    val txPharterial: State<String> = _txPharterial

    fun updateTxPharterial(newValue: String) {
        _txPharterial.value = newValue
    }



    private val _txCO2arterial = mutableStateOf("")  //2
    val txCO2arterial: State<String> = _txCO2arterial
    fun updateTxCO2arterial(newValue: String) {
        _txCO2arterial.value = newValue
    }

    private val _txNa = mutableStateOf("")  //3
    val txNa: State<String> = _txNa
    fun updateTxNa(newValue: String) {
        _txNa.value = newValue
    }

    private val _txK = mutableStateOf("")  //4
    val txK: State<String> = _txK
    fun updateTxK(newValue: String) {
        _txK.value = newValue
    }

    private val _txCl = mutableStateOf("")  //5
    val txCl: State<String> = _txCl
    fun updateTxCl(newValue: String) {
        _txCl.value = newValue
    }

    private val _txCa = mutableStateOf("")  //6
    val txCa: State<String> = _txCa
    fun updateTxCa(newValue: String) {
        _txCa.value = newValue
    }

    private val _txHb = mutableStateOf("")  //7
    val txHb: State<String> = _txHb
    fun updateTxHb(newValue: String) {
        _txHb.value = newValue
    }

    private val _txHCO3 = mutableStateOf("")  //8
    val txHCO3: State<String> = _txHCO3
    fun updateTxHCO3(newValue: String) {
        _txHCO3.value = newValue
    }

    private val _txSaO2 = mutableStateOf("") //9
    val txSaO2: State<String> = _txSaO2
    fun updateTxSaO2(newValue: String) {
        _txSaO2.value = newValue
    }

    private val _txSvO2 = mutableStateOf("")    //10
    val txSvO2: State<String> = _txSvO2
    fun updateTxSvO2(newValue: String) {
        _txSvO2.value = newValue
    }

    private val _txPO2arterial = mutableStateOf("")   //11
    val txPO2arterial: State<String> = _txPO2arterial
    fun updateTxPO2arterial(newValue: String) {
        _txPO2arterial.value = newValue
    }

    private val _txCO2venoso = mutableStateOf("")   //12
    val txCO2venoso: State<String> = _txCO2venoso
    fun updateTxCO2venoso(newValue: String) {
        _txCO2venoso.value = newValue
    }


    private val _text = mutableStateOf("Texto inicial")
    val text: State<String> = _text

    fun updateText(newText: String) {
        _text.value = newText // Removed unnecessary newline
    }

    private val _text1 = mutableStateOf("Texto1")
    val text1: State<String> = _text1

    fun updateText1(newText1: String) {
        _text1.value = newText1 // Removed unnecessary newline
    }

    private val _circMetab = mutableStateOf("")  //6
    val cirMetab: State<String> = _circMetab
    fun updateCirMetab(newValue: String) {
        _circMetab.value = newValue
    }
    private val _oxygenConsump = mutableStateOf("")  //6
    val oxygenConsump: State<String> = _oxygenConsump
    fun updateOxygenConsump(newValue: String) {
        _oxygenConsump.value = newValue
    }


    // Correctly calculate resultText as a State<String> that updates automatically
    val resultText: State<String> = derivedStateOf {
        text.value + txPharterial.value + text1.value + txNa.value +
                txCa.value + cirMetab.value + txHCO3.value + txHb.value + txPO2arterial.value + txCO2venoso.value +
                txSaO2.value + txCl.value + txSvO2.value + txK.value + oxygenConsump.value + txCO2arterial.value

    }
}
enum class Gender { MALE, FEMALE }