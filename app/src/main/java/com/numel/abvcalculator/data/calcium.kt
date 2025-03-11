package com.numel.abvcalculator.data

fun calcium(ca1: Double):String{
    var textoCalcio=""
    if (ca1 >= (1.87 / 2) && ca1 < (2.2 / 2)
    ) {

        textoCalcio= "\n->Hipocalcemia ligera. Ca=${
            ca1
        } mEq/L."

    } else if (ca1 >= (1.5 / 2) && ca1 < (1.87 / 2)
    ) {

        textoCalcio= "\n->Hipocalcemia grave. Ca=${
            ca1
        } mEq/L."


    } else if (ca1 < (1.5 / 2)) {
        textoCalcio= "\n->Hipocalcemia muy grave. Ca=${
            ca1
        } mEq/L."


    } else if (ca1 >= (2.2 / 2) && ca1 <= (2.5 / 2)
    ) {

        textoCalcio= "\n->Normocalcemia. Ca=${
            ca1
        } mEq/L."

    } else if (ca1 > (2.5 / 2) && ca1 < (3.0 / 2)
    ) {
        textoCalcio= "\n->Hipercalcemia ligera. Ca=${
            ca1
        } mEq/L."

    } else if (ca1 in (3.0 / 2)..(3.75 / 2)) {
        textoCalcio= "\n->Hipercalcemia grave. Ca=${
            ca1
        } mEq/L."


    } else if (ca1 > (3.75 / 2)) {
        textoCalcio= "\n->Hipercalcemia muy grave. Ca=${
            ca1
        } mEq/L."


    }
    return textoCalcio
}