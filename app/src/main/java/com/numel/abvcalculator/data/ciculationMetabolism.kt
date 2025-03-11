package com.numel.abvcalculator.data

import kotlin.math.pow

fun circulationMetabolism(
    pO2a: Double,
    cO2a: Double,
    cO2v: Double,
    hb: Double,
    sO2a: Double,
    sO2v: Double
): String {
    var textoCirclMetab: String

    val cCO240: Double
    val cCO2Vena: Double
    val difVAcO2: Double
    val contAO2: Double
    val contVO2: Double
    val difAVO2: Double
    val cociente: Double



    if (pO2a > 40.0) {
        cCO240 =
            -31.56 + (4.328 * cO2a) - (0.07637 * cO2a.pow(
                2.0
            )) + (0.000476 * cO2a.pow(
                3.0
            )) - ((pO2a - 40) * (1.8879 / 60))
        cCO2Vena =
            -31.56 + (4.328 * cO2v) - (0.07637 * cO2v.pow(
                2.0
            )) + (0.000476 * cO2v.pow(
                3.0
            )) - ((cO2v - 40) * (1.8879 / 60))
        difVAcO2 = cCO2Vena - cCO240
        contAO2 =
            (1.38 * hb * sO2a / 100) + 0.0031 * pO2a
        contVO2 =
            (1.38 * hb * sO2v / 100) + 0.0031 * pO2a//ver pvO2
        difAVO2 = contAO2 - contVO2
        cociente = difVAcO2 / difAVO2
        textoCirclMetab = "\n->Contenido arterial estimado de CO2=%.2f".format(
            cCO240
        ) + " ml/dL." + "\n->Contenido venoso estimado de CO2=%.2f ".format(
            cCO2Vena
        ) + " ml/dL." + "\n->Delta c[CO2v-a]=%.2f".format(
            difVAcO2
        ) + " ml/dL." +
                "\n->Contenido arterial de O2=%.2f".format(
                    contAO2
                ) + " ml/dL." + "\n->Contenido venoso de O2=%.2f".format(
            contVO2
        ) + " ml/dL." + "\n->Diferencia A_V de O2=%.2f".format(
            difAVO2
        ) + " ml/dL." + "\n->Delta c[CO2]/Delta c[O2]=%.2f".format(
            cociente
        ) + "."
        if (cociente > 1.4) {
            textoCirclMetab +=
                "\n->Signo de hipoxia >> cociente (Delta c[CO2]/Delta c[O2])=%.2f".format(
                    cociente
                ) + " (Mayor que 1.4)."
            if (sO2v < 65 && difVAcO2 < 6) {
                textoCirclMetab += "\n->Posible:\nSaturación Baja-->Mejorar oxigenación.\nAnemia-->Transfundir.\n->Aumento del consumo de Oxigeno(\nConvulsiones,\nTremor,\nAgitación).\nSedación,antipireticos,antiepilepticos."


            } else if (sO2v < 65 && difVAcO2 > 6) {
                textoCirclMetab += "\n->Gasto cardiaco bajo."


            } else if (sO2v > 80 && difVAcO2 < 6) {
                textoCirclMetab += "\n->Fallo de la microcirculación y el metabolismo celular.(\n-->Eliminar sepsis\n-->Otros fallos de la microcirculación)."


            } else if (sO2v > 80 && difVAcO2 > 6) {
                textoCirclMetab += "\n->Gasto cardiaco bajo.Evaluar >>(\n-->Respuesta a fluidos.\n-->Sin respuesta a fluidos,iniciar inotropicos)."


            }
        } else if (cociente < 1.4) {
            textoCirclMetab += if (sO2v > 80 && difVAcO2 < 6) {
                "\n->Posible Transporte de O2 en exceso. (Si resto biomarcadores normales)."


            } else {
                "\n->Si solo lactato alto,puede indicar hipoxia local o persistencia de hiperlactacidemia en recuperación de un fallo en Extraccion de O2."

            }
        }


    } else if (pO2a < 40.0) {
        cCO240 =
            -31.56 + (4.328 * cO2a) - (0.07637 * cO2a.pow(
                2.0
            )) + (0.000476 * cO2a.pow(
                3.0
            )) + ((40 - pO2a) * (1.8879 / 60))
        cCO2Vena =
            -31.56 + (4.328 * cO2v) - (0.07637 * cO2v.pow(
                2.0
            )) + (0.000476 * cO2v.pow(
                3.0
            )) + ((40 - cO2v) * (1.8879 / 60))
        difVAcO2 = cCO2Vena - cCO240
        contAO2 =
            (1.38 * hb * sO2a / 100) + 0.0031 * pO2a
        contVO2 =
            (1.38 * hb * sO2v / 100) + 0.0031 * pO2a//ver pvO2
        difAVO2 = contAO2 - contVO2
        cociente = difVAcO2 / difAVO2
        textoCirclMetab = "\n->Contenido arterial estimado de CO2=%.2f".format(
            cCO240
        ) + " ml/dL." + "\n->Contenido venoso de CO2=%.2f ".format(
            cCO2Vena
        ) + " ml/dL." + "\n->Delta c[CO2v-a]=%.2f".format(
            difVAcO2
        ) + " ml/dL" +
                "\n->Contenido arterial de O2=%.2f".format(
                    contAO2
                ) + " ml/dL." + "\n Contenido venoso de O2=%.2f".format(
            contVO2
        ) + " ml/dL." + "\nDiferencia A_V de O2=%.2f".format(
            difAVO2
        ) + " ml/dL."
        if (cociente > 1.4) {
            textoCirclMetab +=
                "\n->Signo de hipoxia >> cociente (Delta c[CO2]/Delta c[O2])=%.2f".format(
                    cociente
                ) + " (Mayor que 1.4)."
            if (sO2v < 65 && difVAcO2 < 6) {
                textoCirclMetab += "\n->Posible:\nSaturación Baja-->Mejorar oxigenación.\nAnemia-->Transfundir.\n->Aumento del consumo de Oxigeno(\nConvulsiones,\nTremor,\nAgitación).\nSedación,antipireticos,antiepilepticos."


            } else if (sO2v < 65 && difVAcO2 > 6) {
                textoCirclMetab += "\n->Gasto cardiaco bajo."


            } else if (sO2v > 80 && difVAcO2 < 6) {
                textoCirclMetab += "\n->Fallo de la microcirculación y el metabolismo celular(\n-->Eliminar sepsis\n-->Otros fallos de la microcirculación)."


            } else if (sO2v > 80 && difVAcO2 > 6) {
                textoCirclMetab += "\n->Gasto cardiaco bajo.Evaluar >>(\n-->Respuesta a fluidos\n-->Sin respuesta a fluidos,iniciar inotropicos)."


            }
        } else if (cociente < 1.4) {
            textoCirclMetab += if (sO2v > 80 && difVAcO2 < 6) {
                "\n->Posible Transporte de O2 en exceso " +
                        "(Si resto biomarcadores normales)."

            } else {
                "\n->Si solo lactato alto,puede indicar hipoxia local o persistencia de hiperlactacidemia en recuperación de un fallo en Extraccion de O2."

            }
        }


    } else {
        cCO240 =
            -31.56 + (4.328 * cO2a) - (0.07637 * cO2a.pow(
                2.0
            )) + (0.000476 * cO2a.pow(
                3.0
            ))
        cCO2Vena =
            -31.56 + (4.328 * cO2v) - (0.07637 * cO2v.pow(
                2.0
            )) + (0.000476 * cO2v.pow(
                3.0
            ))
        difVAcO2 = cCO2Vena - cCO240
        contAO2 =
            (1.38 * hb * sO2a / 100) + 0.0031 * pO2a
        contVO2 =
            (1.38 * hb * sO2v / 100) + 0.0031 * pO2a//ver pvO2
        difAVO2 = contAO2 - contVO2
        cociente = difVAcO2 / difAVO2

        textoCirclMetab = "\n->Contenido arterial estimado de CO2=%.2f".format(
            cCO240
        ) + " ml/dL." + "\n->Contenido venoso estimado de CO2=%.2f ".format(
            cCO2Vena
        ) + " ml/dL." + "\n->Delta c[CO2v-a]=%.2f".format(
            difVAcO2
        ) + " ml/dL" +
                "\n->Contenido arterial de O2=%.2f".format(
                    contAO2
                ) + " ml/dL." + "\n->Contenido venoso de O2=%.2f".format(
            contVO2
        ) + " ml/dL." + "\nDiferencia A_V de O2=%.2f".format(
            difAVO2
        ) + " ml/dL."

        if (cociente > 1.4) {
            textoCirclMetab +=
                "\n->Signo de hipoxia >> cociente (Delta c[CO2]/Delta c[O2])=%.2f".format(
                    cociente
                ) + " (Mayor que 1.4)."
            if (sO2v < 65 && difVAcO2 < 6) {
                textoCirclMetab += "\n->Posible:\nSaturación Baja-->Mejorar oxigenación\nAnemia-->Transfundir.\n->Aumento del consumo de Oxigeno(\nConvulsiones,\nTremor,\nAgitación).\nSedación,antipireticos,antiepilepticos."


            } else if (sO2v < 65 && difVAcO2 > 6) {
                textoCirclMetab += "\n->Gasto cardiaco bajo."


            } else if (sO2v > 80 && difVAcO2 < 6) {
                textoCirclMetab += "\n->Fallo de la microcirculación y el metabolismo celular(\n-->Eliminar sepsis\n-->Otros fallos de la microcirculación)."


            } else if (sO2v > 80 && difVAcO2 > 6) {
                textoCirclMetab += "\n->Gasto cardiaco bajo.Evaluar >>(\n-->Respuesta a fluidos\n-->Sin respuesta a fluidos,iniciar inotropicos)."


            }
        } else if (cociente < 1.4) {
            textoCirclMetab += if (sO2v > 80 && difVAcO2 < 6) {
                "\n->Posible Transporte de O2 en exceso " +
                        "(Si resto biomarcadores normales)."

            } else {
                "\n->Si solo lactato alto,puede indicar hipoxia local o persistencia de hiperlactacidemia en recuperación de un fallo en Extraccion de O2."

            }
        }

    }
    return textoCirclMetab
}