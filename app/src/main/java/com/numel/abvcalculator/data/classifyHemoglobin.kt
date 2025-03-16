package com.numel.abvcalculator.data

fun classifyHemoglobin(hb: Double, isHighRisk: Boolean = false): String {
    return when {
        hb < 6 -> "Clasificación: Emergencia\n" +
                "Acción: Transfusión obligatoria. Nivel críticamente bajo de hemoglobina."

        hb in 6.0..7.0 -> "Clasificación: Bajo\n" +
                "Acción: Transfusión recomendada en la mayoría de los casos. Evaluar signos de hipoperfusión."

        hb in 7.1..8.0 -> if (isHighRisk) {
            "Clasificación: Moderado (paciente de alto riesgo)\n" +
                    "Acción: Considerar transfusión debido al riesgo cardiovascular."
        } else {
            "Clasificación: Moderado (paciente estable)\n" +
                    "Acción: Transfusión opcional. Monitorear estado clínico."
        }

        hb in 8.1..10.0 -> if (isHighRisk) {
            "Clasificación: Aceptable (paciente de alto riesgo)\n" +
                    "Acción: Evaluar necesidad de transfusión solo si hay síntomas."
        } else {
            "Clasificación: Aceptable (paciente estable)\n" +
                    "Acción: No se recomienda transfusión."
        }

        hb > 10 -> "Clasificación: Normal\n" +
                "Acción: No requiere transfusión. Valores dentro del rango adecuado."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para la hemoglobina."
    }
}

