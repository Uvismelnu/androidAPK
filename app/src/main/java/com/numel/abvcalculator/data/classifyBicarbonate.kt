package com.numel.abvcalculator.data

fun classifyBicarbonate(hCO3: Double): String {
    return when {
        hCO3 < 15 -> "Clasificación: Acidosis metabólica severa\n" +
                "Causas comunes: Cetoacidosis diabética, insuficiencia renal, diarrea severa."

        hCO3 in 15.0..20.0 -> "Clasificación: Acidosis metabólica leve/moderada\n" +
                "Causas comunes: Pérdida de bicarbonato (diarrea), acidosis láctica, insuficiencia renal incipiente."

        hCO3 in 21.0..28.0 -> "Clasificación: Normal\n" +
                "Causas comunes: Equilibrio ácido-base adecuado. Sin alteraciones significativas."

        hCO3 in 29.0..35.0 -> "Clasificación: Alcalosis metabólica leve/moderada\n" +
                "Causas comunes: Vómitos persistentes, uso de diuréticos, hiperaldosteronismo."

        hCO3 > 35 -> "Clasificación: Alcalosis metabólica severa\n" +
                "Causas comunes: Descompensación grave, riesgo de arritmias y síntomas neurológicos."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para el bicarbonato."
    }
}

