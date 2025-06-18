package com.numel.abvcalculator.data

import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

fun acidobase1(bi: Double, co: Double, pH: Double, na: Double, k: Double, cl: Double): String {
    var textAcidoBasico = ""
    var acidosisType = ""
    var deltapHresult = ""


    val phcalculado =
        6.1f + log10(bi / (0.03f * co))
    val deltapH = abs(pH - phcalculado)
    if (deltapH > 0.05) {
        deltapHresult =
            " ¿¿¿Revisar el procedimiento de medición,la diferencia máxima recomendable entre el pH medido y el pH calculado es {±0.05 unidades de pH} ??? "
    }


    val hco3calculado =
        10.0.pow((pH - 6.1)) * (co * 0.03)
    val co2calculado =
        (10.0.pow((9 - pH)) * (bi)) / 23.829847f
    //hco3 esperado acidosis respiratoria aguda
    val hCO3esperadaARA = 24f + (co - 40f) / 10f

    //rango compensacion hCO3esperadaARA
    val hCO3esperadaARAsup = (21 * hCO3esperadaARA) / 20f

    val hCO3esperadaARAinf = (19 * hCO3esperadaARA) / 20f

    //hco3 esperado acidosis respiratoria cronica
    val hCO3esperadaARC = 24f + (co - 40f) / 10f

    //rango compensacion hCO3esperadaARC
    val hCO3esperadaARCsup = (21 * hCO3esperadaARC) / 20f

    val hCO3esperadaARCinf = (19 * hCO3esperadaARC) / 20f

    //hco3 esperado alcalosis respiratoria aguda
    val hCO3esperadaALRA = 24 - (2 * (40f - co)) / 10f

    //rango compensacion hCO3esperadaALRA
    val hCO3esperadaALRAsup = (21 * hCO3esperadaALRA) / 20f

    val hCO3esperadaALRAinf = (19 * hCO3esperadaALRA - 1f) / 20f

    //hco3 esperado alcalosis respiratoria cronica
    val hCO3esperadaALRC = 24 - (5 * (40f - co)) / 10f

    //rango compensacion hCO3esperadaALRC
    val hCO3esperadaALRCsup = (21 * hCO3esperadaALRC) / 20f

    val hCO3esperadaALRCinf = (19 * hCO3esperadaALRC) / 20f


    val deltaHco3 = abs((24.5 - bi))
    val deltaCo2 = abs((40 - co))
    //co2 esperado  acidosis metabolica
    val co2esperadaAcM = (1.5 * bi) + 8

    //rango de compensacion co2esperadaAcM
    val co2esperadaAcMsup = (21 * co2esperadaAcM) / 20f

    val co2esperadaAcMinf = (19 * co2esperadaAcM) / 20f

    //co2 esperado alcalosis metabolica
    val co2esperadaAlM = (0.9 * bi) + 9

    //rango de compensacion co2esperadaAlM
    val co2esperadaAlMsup = (21 * co2esperadaAlM) / 20f

    val co2esperadaAlMinf = (19 * co2esperadaAlM) / 20f


    //valorar ph 7.4
    if ((pH < 7.38) && (bi < 21.7) && (co > co2esperadaAcMsup) && (deltaHco3 > deltaCo2)) {
        textAcidoBasico =
            "acidosis metabolica predominante con acidosis respiratoria CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH < 7.38) && (bi < 21.7) && (co > co2esperadaAcMsup) && (deltaHco3 < deltaCo2) && (bi <= hCO3esperadaARAsup) && bi >= hCO3esperadaARAinf) {
        textAcidoBasico =
            "acidosis respiratoria aguda predominante con acidosis metabolica  CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH < 7.38) && (bi < 21.7) && (co > co2esperadaAcMsup) && (deltaHco3 < deltaCo2) && (bi <= hCO3esperadaARCsup) && bi >= hCO3esperadaARCinf) {
        textAcidoBasico =
            "acidosis respiratoria cronica predominante con acidosis metabolica  CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH < 7.38) && (bi < 21.7) && (co > co2esperadaAcMsup) && (deltaHco3 < deltaCo2)) {
        textAcidoBasico =
            "acidosis respiratoria predominante con acidosis metabolica CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    }

    //valorar ph 7.4
    else if ((pH < 7.38) && (bi < 21.7) && (co < co2esperadaAcMinf)) {
        textAcidoBasico =
            "acidosis metabolica con alcalosis respiratoria CO2=%.2f".format(co2calculado) + "\npH=%.2f".format(
                phcalculado
            )
    }
    //rango del 5%
    else if ((pH < 7.38) && (bi < 21.7) && (co in co2esperadaAcMinf..co2esperadaAcMsup) && (deltaHco3 > deltaCo2)) {
        textAcidoBasico =
            "acidosis metabolica simple CO2=%.2f".format(co2calculado) + "\npH=%.2f".format(
                phcalculado
            )
    } else if ((pH > 7.42) && (bi > 26.3) && (co > co2esperadaAlMsup) && (deltaHco3 > deltaCo2)) {
        textAcidoBasico =
            "Alcalosis metabolica con acidosis respiratoria CO2=%.2f".format(co2calculado) + "\npH=%.2f".format(
                phcalculado
            )
    } else if ((pH > 7.42) && (bi > 26.3) && (co < co2esperadaAlMinf) && (deltaHco3 < deltaCo2) && (bi in hCO3esperadaALRAinf..hCO3esperadaALRAsup)) {
        textAcidoBasico =
            "Alcalosis respiratoria aguda predominante con alcalosis metabolica CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH > 7.42) && (bi > 26.3) && (co < co2esperadaAlMinf) && (deltaHco3 < deltaCo2) && (bi in hCO3esperadaALRCinf..hCO3esperadaALRCsup)) {
        textAcidoBasico =
            "Alcalosis respiratoria cronica predominante con alcalosis metabolica CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH > 7.42) && (bi > 26.3) && (co < co2esperadaAlMinf) && (deltaHco3 > deltaCo2)) {
        textAcidoBasico =
            "Alcalosis metabolica predominante con alcalosis respiratoria CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH > 7.42) && (bi > 26.3) && (co in co2esperadaAlMinf..co2esperadaAlMsup) && (deltaHco3 > deltaCo2)) {
        textAcidoBasico =
            "Alcalosis metabolica simple CO2=%.2f".format(co2calculado) + "\npH=%.2f".format(
                phcalculado
            )
    } else if ((pH > 7.42) && (co < 38) && (bi > hCO3esperadaALRAsup) && (deltaCo2 > deltaHco3)) {

        textAcidoBasico =
            "alcalosis respiratoria aguda predominante con alcalosis metabolica asociada HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH > 7.42) && (co < 38) && (bi < hCO3esperadaALRCinf) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "alcalosis respiratoria cronica con acidosis metabolica asociada  HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH > 7.42) && (co < 38) && (bi in hCO3esperadaALRCinf..hCO3esperadaALRAsup)) {
        textAcidoBasico = "1-alteracion respiratoria pura con componente agudo y cronico.\n" +
                "2-alcalosis respiratoria aguda con una acidosis metabolica asociada.\n" +
                "3-alcalosis respiratoria cronica con una alcalosis metabolica aguda sobreagregada. HCO3=%.2f".format(
                    hco3calculado
                ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH > 7.42) && (co < 38) && (bi in hCO3esperadaALRAinf..hCO3esperadaALRAsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "alcalosis respiratoria aguda simple HCO3=%.2f".format(hco3calculado) + "\npH=%.2f".format(
                phcalculado
            )
    } else if ((pH > 7.42) && (co < 38) && (bi in hCO3esperadaALRCinf..hCO3esperadaALRCsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "alcalosis respiratoria cronica simple HCO3=%.2f".format(hco3calculado) + "\npH=%.2f".format(
                phcalculado
            )
    } else if ((pH < 7.38) && (co > 42) && (bi < hCO3esperadaARAinf) && (deltaCo2 > deltaHco3)) {

        textAcidoBasico =
            "acidosis respiratoria aguda con acidosis metabolica asociada HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH < 7.38) && (co > 42) && (bi > hCO3esperadaARCsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "acidosis respiratoria cronica con alcalosis metabolica asociada HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH < 7.38) && (co > 42) && (bi in hCO3esperadaARAinf..hCO3esperadaARCsup)) {
        textAcidoBasico =
            "1-alteracion respiratoria pura con componente agudo y cronico (EPOC [enfermedad pulmonar obstructiva cronica] \n" +
                    "descompensada por proceso respiratorio agudo no infeccioso).\n" +
                    "2-acidosis respiratoria cronica con una acidosis metabolica aguda sobreagregada (muy frecuente en la clínica en pacientes con EPOC que se presentan en las fases iniciales de procesos \n" +
                    "infecciosos o con alguna forma de isquemia visceral sobreagregados.\n" +
                    "3-acidosis respiratoria aguda con una alcalosis metabolica asociada (combinacion poco probable) HCO3=%.2f".format(
                        hco3calculado
                    ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH < 7.38) && (co > 42) && (bi in hCO3esperadaARCinf..hCO3esperadaARCsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "acidosis respiratoria cronica simple HCO3=%.2f".format(hco3calculado) + "\npH=%.2f".format(
                phcalculado
            )

    } else if ((pH < 7.38) && (co > 42) && (bi in hCO3esperadaARAinf..hCO3esperadaARAsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "acidosis respiratoria aguda simple HCO3=%.2f".format(hco3calculado) + "\npH=%.2f".format(
                phcalculado
            )

    } else if ((pH < 7.38) && (co in 38.0..42.0)) {
        textAcidoBasico =
            "acidosis metabolica con trastorno opuesto de igual valor(alcalosis respiratoria)"
    } else if ((pH > 7.42) && (co in 38.0..42.0)) {
        textAcidoBasico =
            "alcalosis metabolica con trastorno opuesto de igual valor(acidosis respiratoria)"
    } else if ((co > 42) && (pH in 7.38..7.42) && (bi in hCO3esperadaARAinf..hCO3esperadaARAsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "acidosis respiratoria aguda con trastorno opuesto de igual valor(alcalosis metabolica)"
    } else if ((co > 42) && (pH in 7.38..7.42) && (bi in hCO3esperadaARCinf..hCO3esperadaARCsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "acidosis respiratoria cronica con trastorno opuesto de igual valor(alcalosis metabolica)"
    } else if ((co > 42) && (pH in 7.38..7.42)) {
        textAcidoBasico =
            "acidosis respiratoria con trastorno opuesto de igual valor(alcalosis metabolica)"
    } else if ((co < 38) && (pH in 7.38..7.42) && (bi in hCO3esperadaALRAinf..hCO3esperadaALRAsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "alcalosis respiratoria aguda con trastorno opuesto de igual valor(acidosis metabolica)"
    } else if ((co < 38) && (pH in 7.38..7.42) && (bi in hCO3esperadaALRCinf..hCO3esperadaALRCsup) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "alcalosis respiratoria cronica con trastorno opuesto de igual valor(acidosis metabolica)"
    } else if ((co < 38) && (pH in 7.38..7.42)) {
        textAcidoBasico =
            "alcalosis respiratoria con trastorno opuesto de igual valor(acidosis metabolica)"
    } else if ((pH < 7.38) && (co > 42) && (bi < 21.7) && (deltaCo2 > deltaHco3) && (bi in hCO3esperadaARAinf..hCO3esperadaARAsup)) {
        textAcidoBasico =
            "Acidosis respiratoria aguda predominante con acidosis metabolica HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH < 7.38) && (co > 42) && (bi < 21.7) && (deltaCo2 > deltaHco3) && (bi in hCO3esperadaARCinf..hCO3esperadaARCsup)) {
        textAcidoBasico =
            "Acidosis respiratoria cronica predominante con acidosis metabolica HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH < 7.38) && (co > 42) && (bi < 21.7) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "Acidosis respiratoria predominante con acidosis metabolica HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH < 7.38) && (co > 42) && (bi < 21.7) && (deltaCo2 < deltaHco3)) {
        textAcidoBasico =
            "acidosis metabolica predominante con acidosis respiratoria HCO3=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH < 7.38) && (co > 42) && (bi < 21.7) && (deltaCo2 == deltaHco3)) {
        textAcidoBasico =
            "acidosis metabolica con acidosis respiratoria(ambas de igual valor) HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH < 7.38) && (co > 42) && (bi in 21.7..26.3)) {
        textAcidoBasico =
            "acidosis respiratoria no compensada HCO3=%.2f".format(hco3calculado) + "\npH=%.2f".format(
                phcalculado
            )

    } else if ((pH < 7.38) && (bi < 21.7) && (co in 38.0..42.0)) {
        textAcidoBasico =
            "acidosis metabolica no compensada HCO3=%.2f".format(hco3calculado) + "\npH=%.2f".format(
                phcalculado
            )

    } else if ((pH > 7.42) && (co < 38) && (bi > 26.3) && (deltaCo2 > deltaHco3) && ((bi in hCO3esperadaALRAinf..hCO3esperadaALRAsup))) {
        textAcidoBasico =
            "Alcalosis respiratoria aguda predominante con alcalosis metabolica HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH > 7.42) && (co < 38) && (bi > 26.3) && (deltaCo2 > deltaHco3) && ((bi in hCO3esperadaALRCinf..hCO3esperadaALRCsup))) {
        textAcidoBasico =
            "Alcalosis respiratoria cronica predominante con alcalosis metabolica HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH > 7.42) && (co < 38) && (bi > 26.3) && (deltaCo2 > deltaHco3)) {
        textAcidoBasico =
            "Alcalosis respiratoria predominante con alcalosis metabolica HCO3=%.2f".format(
                hco3calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH > 7.42) && (bi > 26.3) && (co < co2esperadaAlMinf) && (deltaHco3 < deltaCo2)) {
        textAcidoBasico =
            "Alcalosis respiratoria predominante con alcalosis metabolica CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)
    } else if ((pH > 7.42) && (co < 38) && (bi > 26.3) && (deltaHco3 > deltaCo2)) {
        textAcidoBasico =
            "Alcalosis metabolica predominante con alcalosis respiratoria CO2=%.2f".format(
                co2calculado
            ) + "\npH=%.2f".format(phcalculado)

    } else if ((pH > 7.42) && (bi > 26.3) && (co in 38.0..42.0)) {
        textAcidoBasico =
            "Alcalosis metabolica no compensada CO2=%.2f".format(co2calculado) + "\npH=%.2f".format(
                phcalculado
            )

    } else if ((pH > 7.42) && (co < 38.0) && (bi in 21.7..26.3)) {
        textAcidoBasico =
            "Alcalosis respiratoria no compensada CO2=%.2f".format(co2calculado) + "\npH=%.2f".format(
                phcalculado
            )

    }
    else if(pH > 7.42 && bi > 26.3){


        textAcidoBasico = "Alcalosis metabolica simple.Rango de compensacion: CO2=%.2f".format(co2esperadaAlMinf) + "mmHg - %.2f".format(co2esperadaAlMsup) + "mmHg"

    }

    else if ((pH > 7.42) || (pH < 7.38) && ((bi > 26.3) || (bi < 21.7))) {
        textAcidoBasico = "Posible error en calibracion del gasometro"

    }


    //Acidosis type

    val anionGAP = (na + k) - (bi + cl)
    val deita = 24 - bi
    val co2esp = 40.0 - (1.2 * deita)
    val co2rangoMayor = co2esp + (co2esp / 20.0)
    val co2rangoMenor = co2esp - (co2esp / 20.0)
    var hiatico = 0.0

    if (deita != 0.0) {
        hiatico = (anionGAP - 12) / deita
    }

    if (pH < 7.35 && co < 35.0) {
        acidosisType =
            "\n->Existe acidosis metabólica con:\n ->pH medido=${pH}\n->HCO3 medido=${bi} mmol/L. \n ->PCO2 medido=${co} mmHg.\n->PCO2 esperada= %.2f".format(
                co2esp
            ) + " mmHg." + "\n->PCO2_Limite Mayor de Compensacion =%.2f".format(
                co2rangoMayor
            ) + " mmHg." + "\n->PCO2_Limite Menor de Compensación=%.2f".format(
                co2rangoMenor
            ) + " mmHg."

        acidosisType += when {
            co2esp > co2rangoMayor -> {
                "\n->Acidosis respiratoria secundaria."
            }

            co2esp < co2rangoMenor -> {
                "\n->Alcalosis respiratoria secundaria."
            }

            else -> {
                "\n->Acidosis metabólica simple."
            }
        }

        acidosisType += if (deita == 0.0) {
            "\n->Anion GAP=%.2f".format(
                anionGAP
            ) + " mmol/L." + "\n->El cociente exceso de GAP/deficit de Bicarbonato no se puede calcular, puesto que el deficit de Bicarbonato es Cero." + "\n->PCO2 esperada= %.2f".format(
                co2esp
            ) + " mmHg"
        } else {
            "\n->Anion GAP=%.2f".format(
                anionGAP
            ) + "\n->Cociente exceso de GAP/deficit de Bicarbonato=%.2f".format(
                hiatico
            )
        }

        if (anionGAP > 16.0 && hiatico in 0.0..1.0) {
            acidosisType +=
                "\n->Posible coexistencia de una segunda acidosis metabólica con Anion GAP normal acorde al cociente exceso de GAP/deficit de Bicarbonato."
        } else if (anionGAP > 16.0 && hiatico > 1.0) {
            acidosisType +=
                "\n->Posible coexistencia de una alcalosis metabólica acorde al cociente exceso de GAP/deficit de Bicarbonato."
        }
    }
    val textResult = deltapHresult + textAcidoBasico + acidosisType




    return textResult
}

fun acidobase(
    bi: Double, co: Double, pH: Double, na: Double, k: Double, cl: Double
): String {
    // Verificación de consistencia de los datos
    val pHCalculado = (6.1 + log10((bi / (0.03 * co)).toDouble())).toFloat()
    val datosVerificados = abs(pH - pHCalculado) < 0.05

    val resultado = StringBuilder()
    resultado.append("Datos verificados: ${if (datosVerificados) "Sí" else "No"}\n")

    // Cálculo del anion Gap
    val anionGap = na - (cl + bi)
    val anionGapNormal = 8f..16f
    val anionGapElevado = anionGap > 16
    val anionGapLow = anionGap < 8
    resultado.append("Anion Gap: $anionGap (${if (anionGap in anionGapNormal) "Normal" 
    else if (anionGapLow) "Bajo" else "Elevado"})\n")

    //hco3 esperado acidosis respiratoria aguda
    val hCO3esperadaARA = 24f + ((co - 40f) / 10f) // [HCO3] esperado = 24 + [(pCO2medida - 40) / 10]
    //rango compensacion hCO3esperadaARA
    val hCO3esperadaARAsup = (21 * hCO3esperadaARA) / 20f
    val hCO3esperadaARAinf = (19 * hCO3esperadaARA) / 20f

    //hco3 esperado acidosis respiratoria cronica
    val hCO3esperadaARC = 24f + 4*((co - 40f) / 10f) // [HCO3] esperado = 24 + 4 [(pCO2medida - 40) / 10]
    // La base exceso se
    //modifica como compensación de los trastornos respiratorios crónicos, no
    //en los agudos
    //rango compensacion hCO3esperadaARC
    val hCO3esperadaARCsup = (21 * hCO3esperadaARC) / 20f
    val hCO3esperadaARCinf = (19 * hCO3esperadaARC) / 20f

    //hco3 esperado alcalosis respiratoria aguda
    val hCO3esperadaALRA = 24 - 2 * ((40f - co) / 10f)//HCO3] esperado = 24 - 2 [(40 - pCO2medida) / 10]
    //rango compensacion hCO3esperadaALRA
    val hCO3esperadaALRAsup = (21 * hCO3esperadaALRA) / 20f
    val hCO3esperadaALRAinf = (19 * hCO3esperadaALRA) / 20f

    //hco3 esperado alcalosis respiratoria cronica
    val hCO3esperadaALRC = 24 - 5 * ((40f - co) / 10f) //[HCO3] esperado = 24 - 5 [(40- pCO2medida) / 10]
    //rango compensacion hCO3esperadaALRC
    val hCO3esperadaALRCsup = (21 * hCO3esperadaALRC) / 20f
    val hCO3esperadaALRCinf = (19 * hCO3esperadaALRC) / 20f


    val deltaHco3 = abs((24.5 - bi))
    val deltaCo2 = abs((40 - co))

    //co2 esperado  acidosis metabolica
    val co2esperadaAcM = (1.5 * bi) + 8 // pCO2 = 1,5 x HCO3 + 8 (+/-) 2
    //rango de compensacion co2esperadaAcM
    if(co in (co2esperadaAcM-2)..(co2esperadaAcM+2)){

    val co2esperadaAcMsup = (21 * co2esperadaAcM) / 20f
    val co2esperadaAcMinf = (19 * co2esperadaAcM) / 20f}

    //co2 esperado alcalosis metabolica
    val co2esperadaAlM = (0.9 * bi) + 9  //pCO2 esperada = 0,9 x [HCO3] + 9 (+/- 2) 
    //rango de compensacion co2esperadaAlM
    if(co in (co2esperadaAlM-2)..(co2esperadaAlM+2)){

    val co2esperadaAlMsup = (21 * co2esperadaAlM) / 20f
    val co2esperadaAlMinf = (19 * co2esperadaAlM) / 20f}

    // Clasificación de trastornos ácido-base
    if (pH < 7.38) {
        if (co > 38 && bi < 21.7) {
            resultado.append("Acidosis mixta: acidosis respiratoria con acidosis metabólica\n")
        } else if (co > 38) {
            //Acute respiratory acidosis:
            //ΔH+/ΔCO2 = >0.7   h= 24 (PaCO2/HCO3
            val h=24 * (co/bi)
            val delta= (h-40)/(co-40)
            resultado.append("Acidosis respiratoria ")
            resultado.append(
                when {
                    delta > 0.7 -> "aguda\n"
                    delta in 0.3..0.7 -> "aguda sobre cronica\n"
                    delta < 0.3 -> "cronica\n"
                    else -> "" // Add a default case to handle unexpected values of delta
                }
            )
            resultado.append(if (bi > 26.3) "crónica predominante con alcalosis metabólica\n"  else "aguda")
        } else if (bi < 21.7) {
            resultado.append("Acidosis metabólica ")
            resultado.append(if (anionGapElevado) "con anion gap elevado\n" else "con anion gap normal\n")
        }
    } else if (pH > 7.42) {
        if (co < 38 && bi > 26.3) {
            resultado.append("Alcalosis mixta: alcalosis respiratoria con alcalosis metabólica\n")
        } else if (co < 38) {
            resultado.append("Alcalosis respiratoria ")
            resultado.append(if (bi < 21.7) "aguda predominante con acidosis metabólica\n" else "crónica\n")
        } else if (bi > 26.3) {
            resultado.append("Alcalosis metabólica ")
            resultado.append(if (co > 42) "con acidosis respiratoria\n" else "simple\n")
        }
    }
    else if (pH in 7.38..7.42 && co < 38 && bi < 21.7) {
        resultado.append("1-Un trastorno acido-basico simple totalmente compensado"+"\na) Alcalosis respiratoria compensada"+"\nb) Acidosis metabolica compensada "+"\n2-Al menos dos trastornos ácido-base coexisten (una acidemia primaria se compensa con una alcalemia primaria)." +
                "-> Alcalosis respiratoria primaria + acidosis metabolica primaria" )

    }
    else if (pH in 7.38..7.42 && co > 42 && bi > 26.3) {
        resultado.append("1-Un trastorno acido-basico simple totalmente compensado"+"\na) Acidosis respiratoria compensada"+"\nb) Alcalosis metabolica compensada "+"\n2-Al menos dos trastornos ácido-base coexisten (una acidemia primaria se compensa con una alcalemia primaria)."+"-> Alcalosis respiratoria primaria + acidosis metabolica primaria")

    }
    else if (pH in 7.38..7.42 && co in 38.0..42.0 && bi in 21.7..26.3) {
        resultado.append("1- Equilibrio ácido-base normal"+"\n2- Al menos dos trastornos ácido-base coexisten (una acidemia primaria se compensa con una alcalemia primaria)."+" -> Una acidosis metabólica primaria compensa una alcalosis metabólica primaria.")
    }


    else {
        resultado.append("Equilibrio ácido-base normal\n")
    }

    return resultado.toString()
}




