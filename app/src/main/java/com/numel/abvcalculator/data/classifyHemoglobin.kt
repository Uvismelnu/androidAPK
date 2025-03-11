package com.numel.arancel

fun classifyHemoglobin(Hb: Double, isHighRisk: Boolean = false): String {
    return when {
        Hb < 6 -> "Clasificación: Emergencia\n" +
                "Acción: Transfusión obligatoria. Nivel críticamente bajo de hemoglobina."

        Hb in 6.0..7.0 -> "Clasificación: Bajo\n" +
                "Acción: Transfusión recomendada en la mayoría de los casos. Evaluar signos de hipoperfusión."

        Hb in 7.1..8.0 -> if (isHighRisk) {
            "Clasificación: Moderado (paciente de alto riesgo)\n" +
                    "Acción: Considerar transfusión debido al riesgo cardiovascular."
        } else {
            "Clasificación: Moderado (paciente estable)\n" +
                    "Acción: Transfusión opcional. Monitorear estado clínico."
        }

        Hb in 8.1..10.0 -> if (isHighRisk) {
            "Clasificación: Aceptable (paciente de alto riesgo)\n" +
                    "Acción: Evaluar necesidad de transfusión solo si hay síntomas."
        } else {
            "Clasificación: Aceptable (paciente estable)\n" +
                    "Acción: No se recomienda transfusión."
        }

        Hb > 10 -> "Clasificación: Normal\n" +
                "Acción: No requiere transfusión. Valores dentro del rango adecuado."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para la hemoglobina."
    }
}

