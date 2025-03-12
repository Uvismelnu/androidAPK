package com.numel.abvcalculator.data

fun potassium(k1: Double): String {
    var textoK = ""
    if (k1 in 3.5..5.5) {
        textoK = "\n->Existe normocaliemia. K=${
            k1
        } mmol/L."


    } else if (k1 > 5.5 && k1 <= 7.0
    ) {
        textoK = "\n->Hay hiperpotasemia ligera. K=${
            k1
        } mmol/L."


    } else if (k1 > 7.0 && k1 <= 8.5
    ) {
        textoK = "\n->Hiperpotasemia grave. K=${
            k1
        } mmol/L."


    } else if (k1 > 8.5) {
        textoK = "\n->Hiperpotasemia muy grave. K=${
            k1
        } mmol/L."


    } else if (k1 >= 2.6 && k1 < 3.5
    ) {
        textoK = "\n->Hipopotasemia ligera. K=${
            k1
        } mmol/L."

    } else if (k1 >= 1.6 && k1 < 2.6
    ) {
        textoK = "\n->Hipopotasemia grave. K=${
            k1
        } mmol/L."


    } else if (k1 < 1.6) {
        textoK = "\n->Hipopotasemia muy grave. K=${
            k1
        } mmol/L."


    }
    return textoK
}