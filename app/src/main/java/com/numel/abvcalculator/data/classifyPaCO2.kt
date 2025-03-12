package com.numel.abvcalculator.data

fun classifyPaCO2(co2: Double): String {
    var textoVentilation = ""
    if (co2 > 45) {
        textoVentilation = "\n->Hipercapnia. PCO2=${
            co2
        } mmHg."

    } else if (co2 < 35) {
        textoVentilation = "\n->Hipocapnia. PCO2=${
            co2
        } mmHg."

    }
    return textoVentilation

}