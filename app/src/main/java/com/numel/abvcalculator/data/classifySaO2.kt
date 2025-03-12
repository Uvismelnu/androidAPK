package com.numel.abvcalculator.data

fun classifySaO2(SaO2: Double): String {
    return when {
        SaO2 > 95 -> "Clasificación: Normal\n" +
                "Interpretación: Oxigenación adecuada. Sin alteraciones significativas."

        SaO2 in 90.0..95.0 -> "Clasificación: Ligeramente reducida\n" +
                "Interpretación: Oxigenación subóptima. Puede ser fisiológica o indicar hipoxemia leve. Monitorear al paciente."

        SaO2 in 85.0..89.0 -> "Clasificación: Hipoxemia moderada\n" +
                "Interpretación: Insuficiencia respiratoria o alteraciones en la relación ventilación-perfusión. Requiere intervención médica."

        SaO2 < 85 -> "Clasificación: Hipoxemia severa\n" +
                "Interpretación: Emergencia médica. Riesgo de daño tisular y fallo multiorgánico. Administrar oxígeno inmediatamente."

        else -> "Valor inválido. Por favor, ingrese un valor positivo para la saturación arterial de oxígeno."
    }
}

