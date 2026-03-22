package com.numel.abvcalculator.data

import android.annotation.SuppressLint
import kotlin.math.abs
import kotlin.math.log10

/**
 * Análisis completo del equilibrio ácido-base
 *
 * Referencias:
 * - Narins RG, Emmett M. Simple and mixed acid-base disorders. Medicine 1980.
 * - Winters RW. Terminology of acid-base disorders. Ann Intern Med 1965.
 * - Seifter JL. Integration of acid-base and electrolyte disorders. NEJM 2014.
 * - Berend K, de Vries AP, Gans RO. Physiological approach to assessment of acid-base
 *   disturbances. NEJM 2014.
 * - Kamel KS, Halperin ML. Fluid, Electrolyte and Acid-Base Physiology, 5th ed. 2017.
 * - DuBose TD. Acid-Base Disorders. Brenner & Rector's The Kidney, 11th ed. 2019.
 * - Palmer BF, Clegg DJ. Electrolyte and Acid-Base Disturbances in Patients with
 *   Diabetes Mellitus. NEJM 2015.
 * - Corey HE. Stewart and beyond: new models of acid-base balance. Kidney Int 2003.
 */

// ─── Constantes clínicas ───────────────────────────────────────────────────

private const val pH_MIN_NORMAL  = 7.35
private const val pH_MAX_NORMAL  = 7.45
private const val HCO3_NORMAL    = 24.0   // mEq/L
private const val PCO2_NORMAL    = 40.0   // mmHg
private const val AG_NORMAL_LOW  = 8.0    // mEq/L  (rango normal 8-12 sin K)
private const val AG_NORMAL_HIGH = 12.0   // mEq/L
private const val AG_ALBUMINA_REF = 4.0   // g/dL  (albúmina de referencia para AG)

// ─── Resultado estructurado ────────────────────────────────────────────────

data class AcidoBaseResultado(
    val lineas: List<String>,
    val alertas: List<String>,       // hallazgos que requieren atención inmediata
    val diagnosticos: List<String>,  // lista final de trastornos identificados
    val compensacionAdecuada: Boolean?  // null = no aplica / no calculable
) {
    fun textoCompleto(): String = buildString {
        lineas.forEach { appendLine(it) }
        if (alertas.isNotEmpty()) {
            appendLine()
            appendLine("⚠️ ALERTAS:")
            alertas.forEach { appendLine("  • $it") }
        }
        appendLine()
        appendLine("DIAGNÓSTICO(S) FINAL(ES):")
        diagnosticos.forEach { appendLine("  → $it") }
    }
}

// ─── Función pública principal ────────────────────────────────────────────

/**
 * @param pH    pH arterial
 * @param pco2  pCO2 arterial (mmHg)
 * @param hco3  HCO3- medido (mEq/L)  — usar el valor del gasómetro, no calculado
 * @param na    Sodio sérico (mEq/L)
 * @param cl    Cloro sérico (mEq/L)
 * @param k     Potasio sérico (mEq/L) — usar 0.0 si no disponible (AG sin K)
 * @param albumina  Albúmina sérica (g/dL) — usar 4.0 si no disponible
 */
fun acidobase(
    pH: Double,
    pco2: Double,
    hco3: Double,
    na: Double,
    cl: Double,
    k: Double = 0.0,
    albumina: Double = AG_ALBUMINA_REF
): AcidoBaseResultado {

    val info    = mutableListOf<String>()
    val alertas = mutableListOf<String>()
    val dx      = mutableListOf<String>()

    // ── 1. Verificación de consistencia (ecuación de Henderson-Hasselbalch) ──
    val pHCalculado = 6.1 + log10(hco3 / (0.03 * pco2))
    val delta_pH_calc = abs(pH - pHCalculado)
    val datosConsistentes = delta_pH_calc < 0.05
    info += "Consistencia (Henderson-Hasselbalch): ${if (datosConsistentes) "✓ Aceptable" else "✗ Discordancia de ${String.format("%.2f", delta_pH_calc)} unidades — verificar muestra"}"
    if (!datosConsistentes) {
        alertas += "Inconsistencia pH-HCO3-pCO2 > 0.05: posible error de laboratorio, muestra venosa, o efecto de dilución"
    }

    // ── 2. Anion Gap ──────────────────────────────────────────────────────────
    //    AG = Na - (Cl + HCO3)            [sin K — fórmula estándar en la mayoría de laboratorios]
    //    AGk = Na + K - (Cl + HCO3)       [con K — poco usado en clínica]
    val ag  = na - (cl + hco3)
    val agK = na + k - (cl + hco3)
    val agCorregido = if (albumina < AG_ALBUMINA_REF) {
        // Corrección de Figge: por cada 1 g/dL de descenso de albúmina, el AG cae ~2.5 mEq/L
        ag + 2.5 * (AG_ALBUMINA_REF - albumina)
    } else ag

    val agEtiqueta = when {
        agCorregido > AG_NORMAL_HIGH -> "ELEVADO"
        agCorregido < AG_NORMAL_LOW  -> "BAJO"
        else -> "Normal"
    }
    info += "Anion Gap (sin K): ${String.format("%.1f", ag)} mEq/L"
    if (albumina < AG_ALBUMINA_REF) {
        info += "Anion Gap corregido por albúmina (${String.format("%.1f", albumina)} g/dL): ${String.format("%.1f", agCorregido)} mEq/L → $agEtiqueta"
        if (agCorregido - ag >= 5.0) {
            alertas += "Hipoalbuminemia significativa: el AG sin corregir subestima la brecha real en ${String.format("%.1f", agCorregido - ag)} mEq/L"
        }
    } else {
        info += "Anion Gap: ${String.format("%.1f", ag)} mEq/L → $agEtiqueta"
    }
    if (agCorregido < AG_NORMAL_LOW) {
        info += "AG bajo: considerar hiponatremia, intoxicación por bromuro/litio, hipercalcemia, hipermagnesemia, o mieloma IgG"
    }
    if (k > 0.0) info += "Anion Gap con K: ${String.format("%.1f", agK)} mEq/L"

    // ── 3. Determinación del trastorno primario ───────────────────────────────
    //    Según dirección de pH, pCO2 y HCO3
    val acidemia   = pH < pH_MIN_NORMAL
    val alcalemia  = pH > pH_MAX_NORMAL
    val pHNormal   = pH in pH_MIN_NORMAL..pH_MAX_NORMAL

    val acidosisResp  = pco2 > PCO2_NORMAL
    val alcalosisResp = pco2 < PCO2_NORMAL
    val acidosisMetab = hco3 < HCO3_NORMAL
    val alcalosisMetab = hco3 > HCO3_NORMAL

    info += ""
    info += "pH: ${String.format("%.3f", pH)}  pCO2: ${String.format("%.1f", pco2)} mmHg  HCO3: ${String.format("%.1f", hco3)} mEq/L"
    info += "Estado de pH: ${when { acidemia -> "Acidemia" ; alcalemia -> "Alcalemia" ; else -> "Normal (7.35-7.45)" }}"

    // ── 4. Cálculo de compensación esperada y verificación ──────────────────
    //
    //    Las fórmulas de compensación esperada son rangos, no valores únicos.
    //    Si la compensación REAL está FUERA del rango esperado, existe un
    //    trastorno MIXTO. Si está DENTRO, es trastorno SIMPLE (bien compensado).
    //
    //    Referencias para fórmulas:
    //    • Acidosis metabólica  → Regla de Winter: pCO2 = 1.5×HCO3 + 8 ± 2
    //      (Winter SD et al. Ann Intern Med 1990)
    //    • Alcalosis metabólica → pCO2 esperada = 0.7×HCO3 + 21 ± 2
    //      (Javaheri S, Shore NS, Rose B, Kazemi H. J Appl Physiol 1982)
    //      NOTA: La fórmula clásica "0.9×HCO3 + 9" es menos precisa.
    //      La literatura reciente (Kellum 2000, Kamel 2017) respalda 0.7×HCO3+21.
    //    • Acidosis resp. aguda  → ΔHCO3 = 0.1 × ΔpCO2    (±1.5)
    //    • Acidosis resp. crónica → ΔHCO3 = 0.35 × ΔpCO2  (±3)
    //    • Alcalosis resp. aguda  → ΔHCO3 = 0.2 × ΔpCO2   (±2.5)
    //    • Alcalosis resp. crónica → ΔHCO3 = 0.5 × ΔpCO2  (±2.5)
    //
    //    Referencia general: Seifter JL, NEJM 2014; Berend K, NEJM 2014.

    val deltaPco2 = pco2 - PCO2_NORMAL
    val deltaHco3 = hco3 - HCO3_NORMAL

    // Compensación para ACIDOSIS METABÓLICA (Regla de Winter)
    val pco2EsperadaAcM_central = 1.5 * hco3 + 8.0
    val pco2EsperadaAcM_bajo    = pco2EsperadaAcM_central - 2.0
    val pco2EsperadaAcM_alto    = pco2EsperadaAcM_central + 2.0

    // Compensación para ALCALOSIS METABÓLICA (Javaheri / fórmula moderna)
    val pco2EsperadaAlM_central = 0.7 * hco3 + 21.0
    val pco2EsperadaAlM_bajo    = pco2EsperadaAlM_central - 2.0
    val pco2EsperadaAlM_alto    = pco2EsperadaAlM_central + 2.0
    // pCO2 raramente cae bajo 15 mmHg por compensación metabólica pura
    val pco2EsperadaAlM_piso    = maxOf(pco2EsperadaAlM_bajo, 15.0)

    // Compensación para ACIDOSIS RESPIRATORIA AGUDA
    val hco3EsperadaARA_central = HCO3_NORMAL + 0.1 * deltaPco2
    val hco3EsperadaARA_bajo    = hco3EsperadaARA_central - 1.5
    val hco3EsperadaARA_alto    = hco3EsperadaARA_central + 1.5

    // Compensación para ACIDOSIS RESPIRATORIA CRÓNICA
    val hco3EsperadaARC_central = HCO3_NORMAL + 0.35 * deltaPco2
    val hco3EsperadaARC_bajo    = hco3EsperadaARC_central - 3.0
    val hco3EsperadaARC_alto    = hco3EsperadaARC_central + 3.0

    // Compensación para ALCALOSIS RESPIRATORIA AGUDA (pco2 cae → deltaPco2 negativo)
    val hco3EsperadaALRA_central = HCO3_NORMAL + 0.2 * deltaPco2   // deltaPco2 negativo → HCO3 baja
    val hco3EsperadaALRA_bajo    = hco3EsperadaALRA_central - 2.5
    val hco3EsperadaALRA_alto    = hco3EsperadaALRA_central + 2.5

    // Compensación para ALCALOSIS RESPIRATORIA CRÓNICA
    val hco3EsperadaALRC_central = HCO3_NORMAL + 0.5 * deltaPco2
    val hco3EsperadaALRC_bajo    = hco3EsperadaALRC_central - 2.5
    val hco3EsperadaALRC_alto    = hco3EsperadaALRC_central + 2.5
    // HCO3 raramente baja de 12 mEq/L por compensación renal en alcalosis resp crónica
    val hco3EsperadaALRC_piso    = maxOf(hco3EsperadaALRC_bajo, 12.0)

    // ── 5. Ratio Delta-Delta (para acidosis metabólica con AG elevado) ────────
    //
    //    Delta-Delta = (AG_obs - AG_normal) / (HCO3_normal - HCO3_obs)
    //    = ΔAG / ΔHCO3
    //
    //    Interpretación (Kamel 2017, DuBose 2019):
    //    < 0.4  : AG elevado con acidosis hiperclorémica coexistente (mixta)
    //    0.4-1.0: acidosis metabólica mixta (AG alto + hiperclorémica)
    //    1.0-2.0: acidosis metabólica pura con AG elevado (lo esperado)
    //    > 2.0  : alcalosis metabólica coexistente (el HCO3 no bajó tanto como debería)
    //
    //    Solo tiene sentido cuando AG corregido > 12

    val deltaAG    = agCorregido - AG_NORMAL_HIGH  // exceso de AG sobre el límite superior
    val deltaHco3Calc = HCO3_NORMAL - hco3         // caída de HCO3 desde normal

    var deltaDeltaMsg: String? = null
    var deltaDeltaValor: Double? = null

    if (agCorregido > AG_NORMAL_HIGH && deltaHco3Calc > 0) {
        val dd = deltaAG / deltaHco3Calc
        deltaDeltaValor = dd
        deltaDeltaMsg = when {
            dd < 0.4  -> "Delta-Delta: ${String.format("%.2f", dd)} → Probable acidosis hiperclorémica CONCOMITANTE (el HCO3 cayó más de lo explicado por el AG)"
            dd < 1.0  -> "Delta-Delta: ${String.format("%.2f", dd)} → Acidosis metabólica MIXTA (AG elevado + componente hiperclorémico)"
            dd <= 2.0 -> "Delta-Delta: ${String.format("%.2f", dd)} → Acidosis metabólica pura con AG elevado (esperado: 1-2)"
            else      -> "Delta-Delta: ${String.format("%.2f", dd)} → Posible ALCALOSIS METABÓLICA oculta (el HCO3 no bajó tanto como esperado)"
        }
    } else if (agCorregido > AG_NORMAL_HIGH && deltaHco3Calc <= 0) {
        deltaDeltaMsg = "Delta-Delta: HCO3 ≥ normal con AG elevado → ALCALOSIS METABÓLICA + ACIDOSIS METABÓLICA CON AG ELEVADO (trastorno mixto de alta certeza)"
    }

    deltaDeltaMsg?.let { info += it }

    // ── 6. Clasificación principal ────────────────────────────────────────────

    info += ""
    info += "── ANÁLISIS ──"

    when {

        // ══════════════════════════════════════════════════════
        //  ACIDEMIA (pH < 7.35)
        // ══════════════════════════════════════════════════════
        acidemia -> {

            when {

                // ── Acidosis RESPIRATORIA ──────────────────────────
                acidosisResp && !acidosisMetab -> {
                    // Determinar aguda vs crónica según la compensación real del HCO3

                    val enRangoAguda   = hco3 in hco3EsperadaARA_bajo..hco3EsperadaARA_alto
                    val enRangoCronica = hco3 in hco3EsperadaARC_bajo..hco3EsperadaARC_alto
                    val hco3AltoParaAguda   = hco3 > hco3EsperadaARA_alto
                    val hco3BajoParaAguda   = hco3 < hco3EsperadaARA_bajo
                    val hco3AltoParaCronica = hco3 > hco3EsperadaARC_alto
                    val hco3BajoParaCronica = hco3 < hco3EsperadaARC_bajo

                    info += "Trastorno primario: ACIDOSIS RESPIRATORIA"
                    info += "  pCO2 esperada aguda:   HCO3 ${fmt1(hco3EsperadaARA_bajo)}–${fmt1(hco3EsperadaARA_alto)} mEq/L (real: ${fmt1(hco3)})"
                    info += "  pCO2 esperada crónica: HCO3 ${fmt1(hco3EsperadaARC_bajo)}–${fmt1(hco3EsperadaARC_alto)} mEq/L"

                    when {
                        enRangoAguda && !enRangoCronica -> {
                            dx += "Acidosis respiratoria AGUDA con compensación metabólica adecuada"
                        }
                        enRangoCronica && !enRangoAguda -> {
                            dx += "Acidosis respiratoria CRÓNICA con compensación metabólica adecuada"
                        }
                        enRangoAguda && enRangoCronica -> {
                            // Los rangos se solapan (pCO2 cercana a 40): indistinguible sin clínica
                            dx += "Acidosis respiratoria: aguda vs crónica indistinguible por gasometría — correlacionar con cuadro clínico (bicarbonato basal previo)"
                        }
                        hco3AltoParaCronica -> {
                            dx += "Acidosis respiratoria CRÓNICA + ALCALOSIS METABÓLICA superpuesta"
                            dx += "  (HCO3 ${fmt1(hco3)} > esperado crónico máx ${fmt1(hco3EsperadaARC_alto)})"
                            alertas += "HCO3 excesivamente elevado para acidosis resp. crónica: descartar alcalosis metabólica sobreañadida (vómitos, diuréticos, hiperaldosteronismo)"
                        }
                        hco3BajoParaAguda -> {
                            dx += "Acidosis respiratoria AGUDA + ACIDOSIS METABÓLICA superpuesta"
                            dx += "  (HCO3 ${fmt1(hco3)} < esperado agudo mín ${fmt1(hco3EsperadaARA_bajo)})"
                            alertas += "HCO3 insuficientemente elevado para acidosis resp. aguda: acidosis mixta respiratoria + metabólica"
                        }
                        hco3BajoParaCronica && !hco3BajoParaAguda -> {
                            // HCO3 entre rango agudo y crónico: solapamiento probable
                            dx += "Acidosis respiratoria AGUDA SOBRE CRÓNICA (compensación parcial)"
                            dx += "  (HCO3 real ${fmt1(hco3)} entre agudo ${fmt1(hco3EsperadaARA_bajo)}–${fmt1(hco3EsperadaARA_alto)} y crónico ${fmt1(hco3EsperadaARC_bajo)}–${fmt1(hco3EsperadaARC_alto)})"
                        }
                        else -> {
                            dx += "Acidosis respiratoria con compensación no clasificable (valores limítrofes)"
                        }
                    }
                }

                // ── Acidosis METABÓLICA ────────────────────────────
                acidosisMetab && !acidosisResp -> {
                    info += "Trastorno primario: ACIDOSIS METABÓLICA"
                    info += "  Regla de Winter → pCO2 esperada: ${fmt1(pco2EsperadaAcM_bajo)}–${fmt1(pco2EsperadaAcM_alto)} mmHg (real: ${fmt1(pco2)})"

                    val compensacionOk  = pco2 in pco2EsperadaAcM_bajo..pco2EsperadaAcM_alto
                    val hiperventilacion = pco2 < pco2EsperadaAcM_bajo
                    val hipoventilacion  = pco2 > pco2EsperadaAcM_alto

                    // Tipo de acidosis metabólica por AG
                    val tipoAcM = when {
                        agCorregido > AG_NORMAL_HIGH -> {
                            when {
                                deltaDeltaValor != null && deltaDeltaValor!! > 2.0 ->
                                    "AG elevado con ALCALOSIS METABÓLICA oculta (delta-delta > 2)"
                                deltaDeltaValor != null && deltaDeltaValor!! < 1.0 ->
                                    "AG elevado + componente HIPERCLORÉMICO (delta-delta < 1)"
                                else ->
                                    "AG elevado (brecha aniónica elevada)"
                            }
                        }
                        else -> "AG normal (hiperclorémica / pérdida de HCO3)"
                    }

                    when {
                        compensacionOk -> {
                            dx += "Acidosis metabólica SIMPLE con compensación respiratoria adecuada"
                            dx += "  Tipo: $tipoAcM"
                        }
                        hiperventilacion -> {
                            dx += "Acidosis metabólica + ALCALOSIS RESPIRATORIA superpuesta"
                            dx += "  (pCO2 ${fmt1(pco2)} < esperado mín ${fmt1(pco2EsperadaAcM_bajo)})"
                            dx += "  Tipo acidosis: $tipoAcM"
                            alertas += "pCO2 menor al esperado por Regla de Winter: alcalosis respiratoria superpuesta (sepsis, EAP, cirrosis, salicilatos)"
                        }
                        hipoventilacion -> {
                            dx += "Acidosis metabólica + ACIDOSIS RESPIRATORIA superpuesta (trastorno mixto GRAVE)"
                            dx += "  (pCO2 ${fmt1(pco2)} > esperado máx ${fmt1(pco2EsperadaAcM_alto)})"
                            dx += "  Tipo acidosis: $tipoAcM"
                            alertas += "ACIDOSIS MIXTA METABÓLICA + RESPIRATORIA: riesgo vital elevado — el paciente no está compensando la acidosis metabólica"
                        }
                    }
                }

                // ── Acidosis MIXTA (ambos componentes primarios) ──
                acidosisResp && acidosisMetab -> {
                    dx += "ACIDOSIS MIXTA: acidosis respiratoria + acidosis metabólica"
                    dx += "  (pCO2 elevado Y HCO3 bajo → no puede ser compensación)"
                    alertas += "ACIDOSIS MIXTA: pCO2 y HCO3 ambos apuntan a acidemia — situación crítica"
                    if (agCorregido > AG_NORMAL_HIGH) {
                        dx += "  Componente metabólico con AG elevado"
                        deltaDeltaMsg?.let { dx += "  $it" }
                    } else {
                        dx += "  Componente metabólico hiperclorémico (AG normal)"
                    }
                }

                else -> {
                    // Acidemia con pCO2 y HCO3 normales → inconsistente
                    dx += "Acidemia con pCO2 y HCO3 dentro de rangos normales: verificar datos"
                    alertas += "Datos inconsistentes: pH ácido con pCO2 y HCO3 normales"
                }
            }
        }

        // ══════════════════════════════════════════════════════
        //  ALCALEMIA (pH > 7.45)
        // ══════════════════════════════════════════════════════
        alcalemia -> {

            when {

                // ── Alcalosis RESPIRATORIA ─────────────────────────
                alcalosisResp && !alcalosisMetab -> {
                    val absDeltaPco2 = abs(deltaPco2)  // positivo (pCO2 cayó)

                    val enRangoAguda   = hco3 in hco3EsperadaALRA_bajo..hco3EsperadaALRA_alto
                    val enRangoCronica = hco3 in hco3EsperadaALRC_piso..hco3EsperadaALRC_alto
                    val hco3BajoParaAguda   = hco3 < hco3EsperadaALRA_bajo
                    val hco3AltoParaAguda   = hco3 > hco3EsperadaALRA_alto
                    val hco3BajoParaCronica = hco3 < hco3EsperadaALRC_piso
                    val hco3AltoParaCronica = hco3 > hco3EsperadaALRC_alto

                    info += "Trastorno primario: ALCALOSIS RESPIRATORIA"
                    info += "  HCO3 esperado agudo:   ${fmt1(hco3EsperadaALRA_bajo)}–${fmt1(hco3EsperadaALRA_alto)} mEq/L (real: ${fmt1(hco3)})"
                    info += "  HCO3 esperado crónico: ${fmt1(hco3EsperadaALRC_piso)}–${fmt1(hco3EsperadaALRC_alto)} mEq/L"

                    when {
                        enRangoAguda && !enRangoCronica -> {
                            dx += "Alcalosis respiratoria AGUDA con compensación metabólica adecuada"
                        }
                        enRangoCronica && !enRangoAguda -> {
                            dx += "Alcalosis respiratoria CRÓNICA con compensación metabólica adecuada"
                        }
                        enRangoAguda && enRangoCronica -> {
                            dx += "Alcalosis respiratoria: aguda vs crónica indistinguible — correlacionar con clínica"
                        }
                        hco3BajoParaCronica -> {
                            dx += "Alcalosis respiratoria CRÓNICA + ACIDOSIS METABÓLICA superpuesta"
                            dx += "  (HCO3 ${fmt1(hco3)} < esperado crónico mín ${fmt1(hco3EsperadaALRC_piso)})"
                            alertas += "HCO3 excesivamente bajo para alcalosis resp. crónica: descartar acidosis metabólica sobreañadida"
                        }
                        hco3AltoParaAguda -> {
                            dx += "Alcalosis respiratoria AGUDA + ALCALOSIS METABÓLICA superpuesta"
                            dx += "  (HCO3 ${fmt1(hco3)} > esperado agudo máx ${fmt1(hco3EsperadaALRA_alto)})"
                            alertas += "HCO3 elevado con alcalosis respiratoria aguda: alcalosis mixta"
                        }
                        hco3AltoParaCronica -> {
                            dx += "Alcalosis respiratoria CRÓNICA + ALCALOSIS METABÓLICA superpuesta"
                            dx += "  (HCO3 ${fmt1(hco3)} > esperado crónico máx ${fmt1(hco3EsperadaALRC_alto)})"
                            alertas += "HCO3 excesivamente elevado para alcalosis resp. crónica: alcalosis metabólica sobreañadida"
                        }
                        else -> {
                            dx += "Alcalosis respiratoria (compensación en rangos limítrofes — correlacionar con clínica)"
                        }
                    }
                }

                // ── Alcalosis METABÓLICA ───────────────────────────
                alcalosisMetab && !alcalosisResp -> {
                    info += "Trastorno primario: ALCALOSIS METABÓLICA"
                    info += "  pCO2 esperada: ${fmt1(pco2EsperadaAlM_piso)}–${fmt1(pco2EsperadaAlM_alto)} mmHg (real: ${fmt1(pco2)})"

                    val compensacionOk  = pco2 in pco2EsperadaAlM_piso..pco2EsperadaAlM_alto
                    val hipoventilacion  = pco2 > pco2EsperadaAlM_alto
                    val hiperventilacion = pco2 < pco2EsperadaAlM_piso

                    when {
                        compensacionOk -> {
                            dx += "Alcalosis metabólica SIMPLE con compensación respiratoria adecuada"
                        }
                        hipoventilacion -> {
                            dx += "Alcalosis metabólica + ACIDOSIS RESPIRATORIA superpuesta"
                            dx += "  (pCO2 ${fmt1(pco2)} > esperado máx ${fmt1(pco2EsperadaAlM_alto)})"
                            alertas += "pCO2 excesivamente elevada para alcalosis metabólica: acidosis respiratoria superpuesta (EPOC, obesidad, sedación)"
                        }
                        hiperventilacion -> {
                            dx += "Alcalosis metabólica + ALCALOSIS RESPIRATORIA superpuesta (trastorno mixto)"
                            dx += "  (pCO2 ${fmt1(pco2)} < esperado mín ${fmt1(pco2EsperadaAlM_piso)})"
                            alertas += "Alcalosis mixta metabólica + respiratoria: riesgo de arritmias graves y convulsiones"
                        }
                    }
                }

                // ── Alcalosis MIXTA ────────────────────────────────
                alcalosisResp && alcalosisMetab -> {
                    dx += "ALCALOSIS MIXTA: alcalosis respiratoria + alcalosis metabólica"
                    dx += "  (pCO2 bajo Y HCO3 alto → no puede ser compensación)"
                    alertas += "ALCALOSIS MIXTA: ambos componentes elevan el pH — arritmias y convulsiones son riesgo inmediato"
                }

                else -> {
                    dx += "Alcalemia con pCO2 y HCO3 dentro de rangos normales: verificar datos"
                    alertas += "Datos inconsistentes: pH alcalino con pCO2 y HCO3 normales"
                }
            }
        }

        // ══════════════════════════════════════════════════════
        //  pH NORMAL (7.35-7.45)
        // ══════════════════════════════════════════════════════
        pHNormal -> {
            info += "Estado: pH EN RANGO NORMAL"

            when {

                // ── Todo normal: equilibrio real o trastornos que se cancelan ──
                !acidosisResp && !alcalosisResp && !acidosisMetab && !alcalosisMetab -> {
                    dx += "Equilibrio ácido-base normal"
                    if (agCorregido > AG_NORMAL_HIGH) {
                        dx += "⚠ EXCEPCIÓN: AG elevado (${fmt1(agCorregido)}) con pH y HCO3 normales"
                        dx += "  → Probable acidosis metabólica con AG elevado + alcalosis metabólica compensadora"
                        dx += "     (p. ej. cetoacidosis + vómitos, o intoxicación por salicilatos fase tardía)"
                        alertas += "AG elevado con pH y HCO3 normales: TRASTORNO MIXTO OCULTO — el pH normal NO descarta patología grave"
                    }
                }

                // ── pCO2 y HCO3 ambos alterados en mismo sentido: trastornos opuestos ──
                acidosisResp && alcalosisMetab -> {
                    // Acidosis resp crónica bien compensada O acidosis resp + alcalosis met opuesta
                    val enRangoCronica = hco3 in hco3EsperadaARC_bajo..hco3EsperadaARC_alto
                    if (enRangoCronica) {
                        dx += "Acidosis respiratoria CRÓNICA completamente compensada"
                        dx += "  (pH normal por compensación renal esperada)"
                    } else if (hco3 > hco3EsperadaARC_alto) {
                        dx += "Acidosis respiratoria crónica + ALCALOSIS METABÓLICA superpuesta"
                        dx += "  El pH normal resulta del equilibrio entre ambos trastornos"
                        alertas += "pH normal no implica ausencia de trastorno: acidosis resp crónica + alcalosis metabólica detectadas"
                    } else {
                        dx += "Acidosis respiratoria (probablemente crónica) con HCO3 inferior al esperado"
                        dx += "  Posible acidosis metabólica parcialmente compensando la alcalosis sistémica"
                    }
                }

                alcalosisResp && acidosisMetab -> {
                    // Alcalosis resp crónica bien compensada O trastornos opuestos
                    val enRangoCronica = hco3 in hco3EsperadaALRC_piso..hco3EsperadaALRC_alto
                    if (enRangoCronica) {
                        dx += "Alcalosis respiratoria CRÓNICA completamente compensada"
                    } else if (hco3 < hco3EsperadaALRC_piso) {
                        dx += "Alcalosis respiratoria + ACIDOSIS METABÓLICA superpuesta"
                        dx += "  El pH normal resulta del equilibrio entre ambos trastornos primarios"
                        alertas += "pH normal enmascarando trastorno mixto: alcalosis respiratoria + acidosis metabólica"
                        if (agCorregido > AG_NORMAL_HIGH) {
                            dx += "  Acidosis metabólica con AG elevado (causas: cetoacidosis, uremia, intoxicaciones)"
                        } else {
                            dx += "  Acidosis metabólica hiperclorémica (AG normal)"
                        }
                    } else {
                        dx += "Alcalosis respiratoria con compensación metabólica dentro de rangos normales"
                    }
                }

                // ── pCO2 alto, HCO3 alto: acidosis resp crónica vs alcalosis met + acidosis resp ──
                acidosisResp && alcalosisMetab -> {
                    // Ya cubierto arriba — este brazo nunca se alcanza,
                    // pero se mantiene por exhaustividad
                    dx += "Ver rama acidosisResp && alcalosisMetab"
                }

                // ── pCO2 bajo, HCO3 bajo: alcalosis resp crónica vs alcalosis resp + acidosis met ──
                alcalosisResp && acidosisMetab -> {
                    dx += "Ver rama alcalosisResp && acidosisMetab"
                }

                // ── pCO2 normal, HCO3 alterado ──
                !acidosisResp && !alcalosisResp && acidosisMetab -> {
                    dx += "HCO3 bajo con pCO2 normal y pH normal: probable alcalosis respiratoria leve + acidosis metabólica (se compensan mutuamente)"
                    alertas += "Desequilibrio ácido-base enmascarado: HCO3 bajo con pH normal — investigar causa"
                }
                !acidosisResp && !alcalosisResp && alcalosisMetab -> {
                    dx += "HCO3 alto con pCO2 normal y pH normal: probable acidosis respiratoria leve + alcalosis metabólica"
                    alertas += "Desequilibrio ácido-base enmascarado: HCO3 alto con pH normal — investigar causa"
                }

                // ── pCO2 alterado, HCO3 normal ──
                acidosisResp && !acidosisMetab && !alcalosisMetab -> {
                    dx += "pCO2 elevada con HCO3 normal y pH normal: inicio de acidosis respiratoria sin compensación renal aún (< 12-24h) O trastorno mixto en equilibrio"
                }
                alcalosisResp && !acidosisMetab && !alcalosisMetab -> {
                    dx += "pCO2 baja con HCO3 normal y pH normal: inicio de alcalosis respiratoria sin compensación renal aún O trastorno mixto en equilibrio"
                }

                else -> {
                    dx += "pH normal con alteraciones ácido-base: correlacionar con cuadro clínico"
                }
            }

            // Verificación adicional con AG en pH normal
            if (agCorregido > AG_NORMAL_HIGH && dx.none { it.contains("AG") }) {
                dx += "⚠ AG elevado (${fmt1(agCorregido)}) con pH normal: trastorno oculto — calcular Delta-Delta"
                alertas += "AG elevado con pH normal: NUNCA ignorar — puede ser trastorno triple (acidosis AG + alcalosis met + alcalosis resp)"
            }
        }
    }

    // ── 7. Mensajes informativos finales ──────────────────────────────────────

    if (agCorregido > AG_NORMAL_HIGH) {
        info += ""
        info += "Causas frecuentes de AG elevado (MUDPILES ampliado):"
        info += "  Metanol, Uremia, Cetoacidosis (DM/ayuno/OH), Propilen-glicol,"
        info += "  Isoniazida/Hierro, Láctico, Etilenglicol, Salicilatos"
        info += "  + D-lactato, 5-oxoprolina (paracetamol crónico), tolueno"
    }

    if (pH < 7.20) {
        alertas += "pH < 7.20: ACIDOSIS GRAVE — riesgo de depresión miocárdica, vasodilatación, arritmias"
    }
    if (pH > 7.60) {
        alertas += "pH > 7.60: ALCALOSIS GRAVE — riesgo de tetania, convulsiones, arritmias ventriculares"
    }
    if (pco2 < 20.0) {
        alertas += "pCO2 < 20 mmHg: hiperventilación severa — vasocontricción cerebral relevante"
    }
    if (pco2 > 80.0) {
        alertas += "pCO2 > 80 mmHg: hipercapnia severa — riesgo de narcosis hipercápnica"
    }
    if (hco3 < 8.0) {
        alertas += "HCO3 < 8 mEq/L: acidosis metabólica grave — reserva tampón prácticamente agotada"
    }

    return AcidoBaseResultado(
        lineas = info,
        alertas = alertas,
        diagnosticos = dx,
        compensacionAdecuada = null  // reflejado en el diagnóstico textual
    )
}

// ─── Helpers ───────────────────────────────────────────────────────────────

@SuppressLint("DefaultLocale")
private fun fmt1(v: Double) = String.format("%.1f", v)

// ─── Función de compatibilidad con la firma anterior (si se necesita) ──────

