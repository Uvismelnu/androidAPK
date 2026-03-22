package com.numel.abvcalculator.data

/**
 * Clasificación de discloremia según medicina basada en evidencia actualizada.
 *
 * Referencias:
 * - Berend K, van Hulsteijn LH, Gans RO. Chloride: the queen of electrolytes?
 *   Eur J Intern Med 2012; 23(3):203-11.
 * - Nagami GT. Hyperchloremia — why and how. Nefrología 2016; 36(4):347-353.
 * - Yunos NM et al. Association between a chloride-liberal vs chloride-restrictive
 *   intravenous fluid administration strategy and kidney injury in critically ill adults.
 *   JAMA 2012; 308(15):1566-72.
 * - Semler MW et al. Balanced Crystalloids versus Saline in Critically Ill Adults
 *   (SMART trial). NEJM 2018; 378(9):829-839.
 * - Neyra JA, Leaf DE. Choice of Crystalloid Fluid in the Critically Ill Patient.
 *   Crit Care Clin 2015; 31(4):659-82.
 * - Sterns RH. Disorders of plasma sodium. NEJM 2015; 372(1):55-65.
 * - Palmer BF, Clegg DJ. Electrolyte disturbances in patients with chronic acid-base
 *   disorders. Am J Kidney Dis 2017; 69(1):133-144.
 * - Mount DB. Fluid and Electrolyte Disturbances. Harrison's Principles, 20th ed. 2018.
 * - Seifter JL. Integration of acid-base and electrolyte disorders. NEJM 2014; 371:1821.
 *
 * ─── Rango normal: 98–106 mEq/L ──────────────────────────────────────────────
 * (El original usaba 95–106, sobreestimando el límite inferior)
 *
 * ─── Hipocloremia (Cl < 98 mEq/L) ────────────────────────────────────────────
 *  Leve     : 90–97 mEq/L  → alcalosis metabólica hipoclorémica incipiente
 *  Moderada : 80–89 mEq/L  → alcalosis mantenida, depleción de volumen frecuente
 *  Grave    : < 80 mEq/L   → alcalosis grave, tetania, arritmias
 *
 * ─── Hipercloremia (Cl > 106 mEq/L) ──────────────────────────────────────────
 *  Leve     : 107–114 mEq/L → acidosis hiperclorémica leve (AG normal)
 *  Moderada : 115–124 mEq/L → acidosis metabólica significativa
 *  Grave    : ≥ 125 mEq/L   → acidosis grave, riesgo de daño renal agudo
 *
 * CONCEPTO CLAVE (literatura reciente):
 * El Cl no se interpreta aislado. Se debe calcular el Cl CORREGIDO por Na:
 *   Cl_corregido = Cl_medido × (140 / Na_real)
 * Si Cl_corregido es normal → la discloremia es proporcional a la natremia (no primaria).
 * Si Cl_corregido es anormal → existe un trastorno primario del Cl.
 *
 * @param cl  Cloro sérico medido (mEq/L)
 * @param na  Sodio sérico (mEq/L) — opcional. Si se provee, se calcula el Cl corregido.
 */
fun chlorine(cl: Double, na: Double? = null): String = buildString {

    appendLine()

    // ── Cl corregido por Na (cuando se dispone del Na) ────────────────────
    val clCorregido: Double? = na?.takeIf { it > 0 }?.let { cl * (140.0 / it) }

    clCorregido?.let {
        appendLine("── Cl corregido por Na (Cl × 140/Na) ──")
        appendLine("   Na: $na mEq/L  →  Cl corregido: ${String.format("%.1f", it)} mEq/L")
        val interpretacion = when {
            it < 98.0  -> "Hipocloremia VERDADERA (no proporcional a la natremia)"
            it > 106.0 -> "Hipercloremia VERDADERA (no proporcional a la natremia)"
            else       -> "Cl corregido NORMAL → la discloremia es proporcional al Na"
        }
        appendLine("   Interpretación: $interpretacion")
        appendLine()
    }

    // ── Clasificación principal ───────────────────────────────────────────
    when {

        // ── Normocloremia ─────────────────────────────────────────────────
        cl in 98.0..106.0 -> {
            appendLine("→ NORMOCLOREMIA")
            appendLine("   Cl = $cl mEq/L  (rango normal: 98–106 mEq/L)")
            if (clCorregido != null && clCorregido !in 98.0..106.0) {
                appendLine("   ⚠ Aunque el Cl medido es normal, el Cl corregido")
                appendLine("     (${String.format("%.1f", clCorregido)} mEq/L) sugiere discloremia relativa.")
                appendLine("     Interpretar en contexto del Na y el estado ácido-base.")
            }
        }

        // ── Hipercloremia ─────────────────────────────────────────────────
        cl in 107.0..114.0 -> {
            appendLine("→ HIPERCLOREMIA LEVE  (Cl = $cl mEq/L)")
            appendLine("   Rango: 107–114 mEq/L")
            appendLine("   • Causa más frecuente actualmente: fluidoterapia con")
            appendLine("     SSF 0.9% (Cl = 154 mEq/L → aporta exceso de Cl).")
            appendLine("   • Otras causas: acidosis tubular renal tipo 1 y 2,")
            appendLine("     diarrea, fístulas intestinales, acetazolamida,")
            appendLine("     nutrición parenteral hiperclorémica.")
            appendLine("   • Consecuencia metabólica principal:")
            appendLine("     ACIDOSIS METABÓLICA HIPERCLORÉMICA (AG normal).")
            appendLine("     El exceso de Cl desplaza al HCO3 para mantener")
            appendLine("     la electroneutralidad plasmática.")
            appendLine("   • Anion Gap habitualmente normal (8–12 mEq/L).")
            appendLine("   • Verificar: gasometría, AG, HCO3.")
            appendLine("   • Si iatrogénica: considerar cambio a soluciones")
            appendLine("     balanceadas (Ringer Lactato, PlasmaLyte).")
        }

        cl in 115.0..124.0 -> {
            appendLine("→ HIPERCLOREMIA MODERADA  (Cl = $cl mEq/L)")
            appendLine("   Rango: 115–124 mEq/L")
            appendLine("   • Acidosis metabólica hiperclorémica establecida.")
            appendLine("   • Evidencia SMART/SALT-ED (NEJM 2018):")
            appendLine("     La hipercloremia moderada-grave se asocia con:")
            appendLine("     – Daño renal agudo (↑ creatinina, ↓ TFG)")
            appendLine("     – Vasoconstricción renal aferente")
            appendLine("     – Mayor mortalidad en UCI")
            appendLine("     – Acidosis dilucional en contexto de reanimación.")
            appendLine("   • Causas frecuentes: resucitación agresiva con SSF,")
            appendLine("     diarrea crónica grave, acidosis tubular renal,")
            appendLine("     hiperaldosteronismo, insuficiencia suprarrenal.")
            appendLine("   • Acción recomendada:")
            appendLine("     – Gasometría + electrolitos + AG urgentes.")
            appendLine("     – Suspender SSF 0.9% si es la causa.")
            appendLine("     – Cambiar a soluciones balanceadas.")
            appendLine("     – Tratar acidosis metabólica subyacente.")
            appendLine("     – Monitorizar función renal.")
        }

        cl >= 125.0 -> {
            appendLine("→ HIPERCLOREMIA GRAVE  (Cl = $cl mEq/L)  ⚠")
            appendLine("   Umbral: ≥ 125 mEq/L")
            appendLine("   • Acidosis hiperclorémica grave con riesgo de:")
            appendLine("     – Disfunción miocárdica por acidosis severa")
            appendLine("     – Daño renal agudo significativo")
            appendLine("     – Vasodilatación esplácnica e hipoperfusión renal")
            appendLine("   • Gasometría URGENTE.")
            appendLine("   • Descartar acidosis metabólica grave (pH < 7.20).")
            appendLine("   • Suspender TODA fuente de Cl en exceso.")
            appendLine("   • Considerar bicarbonato IV si pH < 7.20 y HCO3 < 10.")
            appendLine("   • Monitorización estrecha de función renal,")
            appendLine("     diuresis y parámetros hemodinámicos.")
        }

        // ── Hipocloremia ──────────────────────────────────────────────────
        cl in 90.0..97.0 -> {
            appendLine("→ HIPOCLOREMIA LEVE  (Cl = $cl mEq/L)")
            appendLine("   Rango: 90–97 mEq/L")
            appendLine("   • El Cl bajo se acompaña habitualmente de")
            appendLine("     ALCALOSIS METABÓLICA HIPOCLORÉMICA:")
            appendLine("     al caer el Cl, el riñón retiene HCO3 para")
            appendLine("     mantener electroneutralidad → sube el pH.")
            appendLine("   • Causas más frecuentes:")
            appendLine("     – Vómitos / aspiración nasogástrica (pérdida de HCl)")
            appendLine("     – Diuréticos de asa o tiazídicos")
            appendLine("     – Alcalosis metabólica por contracción de volumen")
            appendLine("     – Síndrome de Bartter / Gitelman")
            appendLine("   • La hipocloremia sostenida MANTIENE la alcalosis")
            appendLine("     (alcalosis hipoclorémica autoperpetuada).")
            appendLine("   • Tratamiento: reposición de Cl (NaCl oral o IV,")
            appendLine("     KCl si hay hipopotasemia concomitante).")
            appendLine("   • Verificar: K, HCO3, pH, Na urinario.")
        }

        cl in 80.0..89.0 -> {
            appendLine("→ HIPOCLOREMIA MODERADA  (Cl = $cl mEq/L)")
            appendLine("   Rango: 80–89 mEq/L")
            appendLine("   • Alcalosis metabólica hipoclorémica establecida.")
            appendLine("   • Síntomas posibles: náuseas, debilidad, confusión,")
            appendLine("     calambres musculares, letargia.")
            appendLine("   • Causas: vómitos prolongados, aspiración nasogástrica")
            appendLine("     mantenida, diuréticos crónicos, fibrosis quística,")
            appendLine("     síndrome de Bartter/Gitelman.")
            appendLine("   • Evaluar:")
            appendLine("     – Cl urinario: < 10 mEq/L → pérdidas extrarrenales")
            appendLine("       (vómitos, SNG). > 20 mEq/L → pérdidas renales")
            appendLine("       (diuréticos, Bartter).")
            appendLine("   • Tratamiento:")
            appendLine("     – Reposición activa de Cl: NaCl 0.9% IV.")
            appendLine("     – KCl si hipopotasemia asociada (muy frecuente).")
            appendLine("     – Sin corrección del Cl, la alcalosis NO se resuelve.")
            appendLine("   • Gasometría recomendada.")
        }

        cl < 80.0 -> {
            appendLine("→ HIPOCLOREMIA GRAVE  (Cl = $cl mEq/L)  ⚠")
            appendLine("   Umbral: < 80 mEq/L")
            appendLine("   • Alcalosis metabólica grave (pH frecuentemente > 7.55).")
            appendLine("   • Riesgo de:")
            appendLine("     – Tetania hipocalcémica funcional (el pH alcalino")
            appendLine("       reduce el Ca ionizado disponible)")
            appendLine("     – Arritmias cardíacas graves")
            appendLine("     – Hipoventilación compensadora (↑ pCO2)")
            appendLine("     – Convulsiones")
            appendLine("   • Gasometría URGENTE.")
            appendLine("   • Monitorización ECG.")
            appendLine("   • Reposición IV urgente de Cl:")
            appendLine("     – NaCl 0.9% IV a ritmo adecuado al estado de volemia.")
            appendLine("     – KCl IV si hipopotasemia grave concomitante.")
            appendLine("   • Si alcalosis muy grave (pH > 7.65) refractaria:")
            appendLine("     considerar acetazolamida o HCl diluido IV en UCI.")
            appendLine("   • Monitorizar Cl, K, pH y función renal cada 4–6 h.")
        }

        else -> {
            appendLine("→ Valor de Cl fuera del rango evaluable ($cl mEq/L).")
            appendLine("   Verificar la muestra y repetir la determinación.")
        }
    }
}