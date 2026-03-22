package com.numel.abvcalculator.data

/**
 * Clasificación de disnatremia según literatura médica actualizada.
 *
 * Referencias:
 * - Spasovski G et al. Clinical practice guideline on diagnosis and treatment of
 *   hyponatraemia. ESE / ERA-EDTA. Eur J Endocrinol 2014; 170(3):G1-G47.
 * - Verbalis JG et al. Diagnosis, evaluation, and treatment of hyponatremia:
 *   expert panel recommendations. AJKD 2013; 62(6 Suppl 2):S1-42.
 * - Sterns RH. Disorders of plasma sodium — causes, consequences, and correction.
 *   NEJM 2015; 372(1):55-65.
 * - Hoorn EJ, Zietse R. Diagnosis and treatment of hyponatremia: compilation of the
 *   guidelines. CJASN 2017; 12(12):2054-2062.
 * - Lindner G, Funk GC. Hypernatremia in critically ill patients.
 *   J Crit Care 2013; 28(2):216.e11-20.
 * - Adrogué HJ, Madias NE. Hypernatremia. NEJM 2000; 342(20):1493-9.
 *
 * ─── Hiponatremia (Na < 135 mEq/L) ───────────────────────────────────────────
 *  Leve     : 130–134 mEq/L  → síntomas mínimos o ausentes
 *  Moderada : 125–129 mEq/L  → síntomas moderados (náuseas, confusión leve)
 *  Grave    : < 125 mEq/L    → riesgo de edema cerebral, convulsiones, coma
 *
 *  NOTA: La gravedad clínica depende más de la velocidad de instauración
 *  (aguda < 48 h vs crónica ≥ 48 h) que del valor absoluto.
 *  Una hiponatremia grave AGUDA es una emergencia aunque el paciente esté asintomático.
 *
 * ─── Hipernatremia (Na > 145 mEq/L) ──────────────────────────────────────────
 *  Leve     : 146–149 mEq/L  → déficit hídrico leve, habitualmente tolerable
 *  Moderada : 150–169 mEq/L  → deshidratación significativa, requiere corrección activa
 *  Grave    : ≥ 170 mEq/L    → riesgo de trombosis venosa, hemorragia intracraneal
 *
 *  La hipernatremia SIEMPRE implica hiperosmolaridad efectiva.
 *  La corrección no debe superar 10–12 mEq/L en las primeras 24 h
 *  (riesgo de edema cerebral de rebote si se corrige demasiado rápido).
 */
fun sodium(na: Double): String = buildString {

    appendLine()

    when {

        // ── Eunatremia ────────────────────────────────────────────────────
        na in 135.0..145.0 -> {
            appendLine("→ EUNATREMIA")
            appendLine("   Na = $na mEq/L  (rango normal: 135–145 mEq/L)")
        }

        // ── Hipernatremia ─────────────────────────────────────────────────
        na in 145.0..149.0 -> {
            appendLine("→ HIPERNATREMIA LEVE  (Na = $na mEq/L)")
            appendLine("   Rango: 146–149 mEq/L")
            appendLine("   • Causa habitual: pérdidas insensibles no repuestas,")
            appendLine("     polidipsia primaria suspendida, fiebre.")
            appendLine("   • Conducta: reposición oral o IV con solución hipotónica.")
            appendLine("   • Velocidad de corrección: ≤ 10–12 mEq/L en 24 h.")
        }

        na in 150.0..169.0 -> {
            appendLine("→ HIPERNATREMIA MODERADA  (Na = $na mEq/L)")
            appendLine("   Rango: 150–169 mEq/L")
            appendLine("   • Manifestaciones: sed intensa, oliguria, confusión,")
            appendLine("     debilidad muscular, taquicardia.")
            appendLine("   • Causas frecuentes: diabetes insípida, pérdidas GI,")
            appendLine("     hiperaldosteronismo, alimentación hiperosmolar.")
            appendLine("   • Déficit de agua libre ≈ (Na/140 − 1) × ACT")
            appendLine("     [ACT = 0.6 × peso en hombres; 0.5 en mujeres]")
            appendLine("   • Corrección activa requerida: glucosado 5% o NaCl 0.45%.")
            appendLine("   • Velocidad máx: 10–12 mEq/L/24 h (no superar).")
        }

        na >= 170.0 -> {
            appendLine("→ HIPERNATREMIA GRAVE  (Na = $na mEq/L)")
            appendLine("   Umbral: ≥ 170 mEq/L")
            appendLine("   • EMERGENCIA OSMÓTICA: riesgo de:")
            appendLine("     – Trombosis venosa cerebral")
            appendLine("     – Hemorragia intracraneal por contracción neuronal")
            appendLine("     – Coma y muerte")
            appendLine("   • Ingreso en UCI / monitorización estrecha.")
            appendLine("   • Corrección muy gradual: ≤ 10 mEq/L en las primeras 24 h.")
            appendLine("     Riesgo de EDEMA CEREBRAL DE REBOTE si se corrige rápido.")
            appendLine("   • Calcular déficit de agua libre y reponer en 48–72 h.")
        }

        // ── Hiponatremia ──────────────────────────────────────────────────
        na in 130.0..134.9 -> {
            appendLine("→ HIPONATREMIA LEVE  (Na = $na mEq/L)")
            appendLine("   Rango: 130–134 mEq/L")
            appendLine("   • Síntomas: habitualmente ausentes o inespecíficos")
            appendLine("     (náuseas leves, malestar general).")
            appendLine("   • SIEMPRE distinguir:")
            appendLine("     – Hiponatremia VERDADERA (hipoosmolar) vs")
            appendLine("       pseudohiponatremia (hiperlipidemia, hiperproteinemia) vs")
            appendLine("       hiponatremia hiperosmolar (hiperglucemia: por cada")
            appendLine("       100 mg/dL de glucosa > 100, Na cae ~1.6 mEq/L).")
            appendLine("   • Evaluar: osmolaridad plasmática, osmolaridad urinaria,")
            appendLine("     Na urinario, volemia clínica.")
            appendLine("   • Restricción hídrica como medida inicial en SIADH.")
            appendLine("   • Velocidad de corrección: máx 10–12 mEq/L en 24 h.")
            appendLine("     (riesgo de mielinólisis osmótica si se corrige rápido).")
        }

        na in 125.0..129.9 -> {
            appendLine("→ HIPONATREMIA MODERADA  (Na = $na mEq/L)")
            appendLine("   Rango: 125–129 mEq/L")
            appendLine("   • Síntomas moderados frecuentes: náuseas y vómitos,")
            appendLine("     confusión, cefalea, marcha inestable.")
            appendLine("   • Causas principales: SIADH, ICC, cirrosis, hipotiroidismo,")
            appendLine("     insuficiencia suprarrenal, polidipsia, diuréticos.")
            appendLine("   • Evaluar osmolaridad plasmática y urinaria,")
            appendLine("     Na urinario y estado de volemia.")
            appendLine("   • Tratamiento según causa:")
            appendLine("     – SIADH: restricción hídrica ± tolvaptán ± urea oral")
            appendLine("     – Hipovolemia: NaCl 0.9% para restaurar volemia")
            appendLine("     – ICC/cirrosis: restricción hídrica + tratar causa base")
            appendLine("   • Velocidad de corrección: 4–8 mEq/L/día (máx 10 en 24 h).")
            appendLine("   • VIGILAR signos de corrección excesiva (SOM).")
        }

        na < 125.0 -> {
            appendLine("→ HIPONATREMIA GRAVE  (Na = $na mEq/L)")
            appendLine("   Umbral: < 125 mEq/L")
            appendLine("   • Riesgo vital: herniación cerebral, convulsiones, coma.")
            appendLine("   • Si AGUDA (< 48 h) o SINTOMÁTICA GRAVE:")
            appendLine("     → NaCl 3% HIPERTÓNICO en bolo IV:")
            appendLine("       150 mL en 20 min, repetir hasta 3 veces si persisten")
            appendLine("       síntomas graves (guía ESE/ERA-EDTA 2014).")
            appendLine("   • Objetivo inicial: subir Na 4–6 mEq/L en 1–2 h")
            appendLine("     para revertir edema cerebral agudo.")
            appendLine("   • Objetivo 24 h: NO superar 10–12 mEq/L de corrección total.")
            appendLine("   • Si CRÓNICA (≥ 48 h) o indeterminada: corregir aún más")
            appendLine("     lentamente (máx 8 mEq/L/24 h) — mayor riesgo de SOM.")
            appendLine("   • SÍNDROME DE OSMOSIS MALADAPTATIVA (SOM / mielinólisis):")
            appendLine("     riesgo máximo si: desnutrición, alcoholismo, K bajo,")
            appendLine("     trasplante hepático, hiponatremia crónica.")
            appendLine("   • Monitorizar Na cada 2–4 h durante corrección.")
        }

        else -> {
            appendLine("→ Valor de Na fuera del rango evaluable ($na mEq/L).")
            appendLine("   Verificar la muestra y repetir la determinación.")
        }
    }
}