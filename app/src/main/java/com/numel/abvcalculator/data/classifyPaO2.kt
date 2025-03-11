package com.numel.abvcalculator.data

fun classifyPaO2(PaO2: Double): String {
    return when {
        PaO2 > 100 -> "Clasificación: Hiperoxia\n" +
                "Causas comunes: Oxigenoterapia excesiva, altitud elevada (en neonatos), o condiciones fisiológicas específicas. Puede aumentar el riesgo de toxicidad por oxígeno."

        PaO2 in 80.0..100.0 -> "Clasificación: Normal\n" +
                "Causas comunes: Valores dentro del rango esperado para adultos sanos respirando aire ambiente al nivel del mar."

        PaO2 in 60.0..79.0 -> "Clasificación: Hipoxemia leve\n" +
                "Causas comunes: Enfermedades pulmonares leves (ej., EPOC inicial), insuficiencia respiratoria compensada."

        PaO2 in 40.0..59.0 -> "Clasificación: Hipoxemia moderada\n" +
                "Causas comunes: Enfermedades pulmonares más severas (ej., neumonía, edema pulmonar), embolia pulmonar."

        PaO2 < 40.0 -> "Clasificación: Hipoxemia severa\n" +
                "Causas comunes: Síndrome de dificultad respiratoria aguda (SDRA), fallo respiratorio grave, shock séptico."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para PaO2."
    }
}

