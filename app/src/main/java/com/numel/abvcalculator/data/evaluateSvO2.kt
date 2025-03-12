package com.numel.arancel

fun evaluateSvO2(svO2: Double): String {
    var textoSat = ""
    when {
        svO2 > 80 -> textoSat="\n" +
                "->Baja extraccion de O2, Bajo metabolismo celular de O2"
        svO2 in 65.0..80.0 -> textoSat="\n" +
                "->Entrega normal de O2 → Extraccion de O2 normal"
        svO2 in 50.0..65.0 -> textoSat="\n" +
                "->Entrega baja de O2 → Incremento de la extracion compesatoria de O2"
        svO2 in 30.0..50.0 -> textoSat="\n" +
                "\n" +
                "->Entrega de O2 critica → Extraccion de O2 depletada\nInicio del metabolismo anaerobio"
        svO2 < 25 -> textoSat="\n" +"Muerte celular"
        else -> textoSat="\n" +"Valor invalido"
    }
    return textoSat
}
