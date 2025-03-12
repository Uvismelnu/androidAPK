package com.numel.arancel

fun sodium(na1: Double): String {
    var textoNa = ""
    if (na1 in 135.0..145.0) {
        textoNa = "\n->Eunatremia. Na=${
            na1
        } mmol/L."
    } else if (na1 > 145 && na1 <= 155
    ) {
        textoNa = "\n->La hipernatremia es ligera. Na=${
            na1
        } mmol/L."

    } else if (na1 > 155 && na1 <= 175
    ) {
        textoNa = "\n->La hipernatremia es severa. Na=${
            na1
        } mmol/L."

    } else if (na1 > 175) {
        textoNa = "\n->La hipernatremia es muy grave. Na=${
            na1
        } mmol/L."


    } else if (na1 < 135 && na1 >= 126
    ) {
        textoNa = "\n->La hiponatremia es ligera. Na=${
            na1
        } mmol/L."


    } else if (na1 >= 111 && na1 < 126
    ) {
        textoNa = "\n->La hiponatremia es severa. Na=${
            na1
        } mmol/L."

    } else if (na1 < 111) {
        textoNa = "\n->La hiponatremia es muy grave. Na=${
            na1
        } mmol/L."

    }
    return textoNa


}
