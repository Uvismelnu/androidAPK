package com.numel.abvcalculator.data

fun classifyPvCO2(pvCO2: Double): String {
    return when {
        pvCO2 < 35 -> "Clasificación: Hipocapnia venosa\n" +
                "Causas comunes: Hiperventilación, alcalosis respiratoria, baja producción de CO₂."

        pvCO2 in 35.0..45.0 -> "Clasificación: Normal\n" +
                "Causas comunes: Equilibrio adecuado entre producción y eliminación de CO₂."

        pvCO2 in 46.0..55.0 -> "Clasificación: Hiperxia venosa leve\n" +
                "Causas comunes: Acidosis metabólica compensatoria, hipoperfusión tisular."

        pvCO2 in 56.0..70.0 -> "Clasificación: Hiperxia venosa moderada\n" +
                "Causas comunes: Insuficiencia respiratoria, shock, acidosis metabólica severa."

        pvCO2 > 70 -> "Clasificación: Hiperxia venosa severa\n" +
                "Causas comunes: Fallo respiratorio grave, acidosis respiratoria severa, paro cardiorrespiratorio."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para PvCO₂."
    }
}

