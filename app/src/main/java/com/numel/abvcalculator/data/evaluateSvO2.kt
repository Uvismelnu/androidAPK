package com.numel.abvcalculator.data

fun evaluateSvO2(svO2: Double): String {
    val textoSat: String = when {
        svO2 > 80 -> "\n" +
                "->Baja extraccion de O2, Bajo metabolismo celular de O2"
        svO2 in 65.0..80.0 -> "\n" +
                "->Entrega normal de O2 → Extraccion de O2 normal"
        svO2 in 50.0..65.0 -> "\n" +
                "->Entrega baja de O2 → Incremento de la extracion compesatoria de O2"
        svO2 in 30.0..50.0 -> "\n" +
                "\n" +
                "->Entrega de O2 critica → Extraccion de O2 depletada\nInicio del metabolismo anaerobio"
        svO2 < 25 -> "\n" +"Muerte celular"
        else -> "\n" +"Valor invalido"
    }
    return textoSat
}
