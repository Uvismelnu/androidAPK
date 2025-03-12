package com.numel.abvcalculator.data

fun chlorine(cl1: Double): String {
    var textoCl = ""
    if (cl1 < 95) {
        textoCl = "\n->Hipocloremia. Cl=${
            cl1
        } mmol/L."


    } else if (cl1 > 106) {
        textoCl = "\n->Hipercloremia. Cl=${
            cl1
        } mmol/L."


    } else if (cl1 in 95.0..106.0) {
        textoCl = "\n->Normocloremia. Cl=${
            cl1
        } mmol/L."


    }
    return textoCl
    
}