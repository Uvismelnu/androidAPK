package com.numel.abvcalculator.data

fun classifyPvCO2(PvCO2: Double): String {
    return when {
        PvCO2 < 35 -> "Clasificación: Hipocapnia venosa\n" +
                "Causas comunes: Hiperventilación, alcalosis respiratoria, baja producción de CO₂."

        PvCO2 in 35.0..45.0 -> "Clasificación: Normal\n" +
                "Causas comunes: Equilibrio adecuado entre producción y eliminación de CO₂."

        PvCO2 in 46.0..55.0 -> "Clasificación: Hiperxia venosa leve\n" +
                "Causas comunes: Acidosis metabólica compensatoria, hipoperfusión tisular."

        PvCO2 in 56.0..70.0 -> "Clasificación: Hiperxia venosa moderada\n" +
                "Causas comunes: Insuficiencia respiratoria, shock, acidosis metabólica severa."

        PvCO2 > 70 -> "Clasificación: Hiperxia venosa severa\n" +
                "Causas comunes: Fallo respiratorio grave, acidosis respiratoria severa, paro cardiorrespiratorio."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para PvCO₂."
    }
}

