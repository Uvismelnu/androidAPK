package com.numel.abvcalculator.data

/**
 * Clasificación de discalcemia según literatura médica actualizada basada en evidencia.
 *
 * UNIDADES: mmol/L para calcio total sérico (estándar internacional).
 *   Conversión: 1 mmol/L = 4 mg/dL = 2 mEq/L
 *
 * Referencias:
 * - Bilezikian JP. Primary hyperparathyroidism. NEJM 2023; 388(21):1971-1980.
 * - Marcocci C, Cetani F. Primary hyperparathyroidism. NEJM 2011; 365:2389-2397.
 * - Fong J, Khan A. Hypocalcemia: updates in diagnosis and management.
 *   Can Fam Physician 2012; 58(2):158-162.
 * - Bollerslev J et al. European Society of Endocrinology Clinical Guideline:
 *   Treatment of chronic hypoparathyroidism in adults. EES 2015; 173:G1-G20.
 * - Shane E et al. Atypical subtrochanteric and diaphyseal femoral fractures.
 *   J Bone Miner Res 2014; 29(1):1-23.
 * - Pepe J et al. Sporadic and hereditary primary hyperparathyroidism.
 *   J Endocrinol Invest 2021; 44(10):2195-2210.
 * - Goldner W. Cancer-related hypercalcemia. J Oncol Pract 2016; 12(5):426-432.
 * - Payne RB et al. Interpretation of serum total calcium: effects of adjustment
 *   for albumin concentration on frequency of abnormal values.
 *   J Clin Pathol 1973; 26(12):861-863.
 * - Dickerson RN et al. Accuracy of methods to estimate ionized and "corrected"
 *   serum calcium concentrations in critically ill multiple trauma patients.
 *   J Trauma 2004; 56(5):1075-81.
 *
 * ─── Hipocalcemia (Ca total < 2.10 mmol/L) ──────────────────────────────────
 *  Leve     : 2.00–2.09 mmol/L  → síntomas mínimos; Chvostek/Trousseau leves
 *  Moderada : 1.75–1.99 mmol/L  → parestesias, calambres, tetania latente
 *  Grave    : < 1.75 mmol/L     → tetania manifiesta, laringoespasmo, convulsiones
 *
 * ─── Hipercalcemia (Ca total > 2.55 mmol/L) ─────────────────────────────────
 *  Leve     : 2.56–2.99 mmol/L  → habitualmente asintomática
 *  Moderada : 3.00–3.49 mmol/L  → síntomas neurológicos y GI moderados
 *  Grave    : ≥ 3.50 mmol/L     → crisis hipercalcémica, coma, arritmias
 *
 * NOTA CRÍTICA sobre unidades:
 *   Esta función recibe Ca en mmol/L (rango esperado: 0.5–5.0).
 *   Si el laboratorio reporta en mg/dL, dividir por 4 antes de llamar esta función.
 *   Si el laboratorio reporta en mEq/L, dividir por 2.
 *
 * @param ca         Calcio total sérico en mmol/L
 * @param albumina   Albúmina sérica en g/dL (default 4.0). Si es < 4.0 se aplica
 *                   corrección de Payne: Ca_corr = Ca_medido + 0.02 × (40 − Alb_g/L)
 * @param caIonico   Calcio iónico medido directamente (mmol/L), si disponible.
 *                   Rango normal: 1.15–1.30 mmol/L. Tiene prioridad clínica sobre el total.
 */
fun calcium(
    ca: Double,
    albumina: Double = 4.0,
    caIonico: Double? = null
): String = buildString {

    appendLine()

    // ── 1. Calcio iónico (si disponible: tiene prioridad diagnóstica) ─────────
    caIonico?.let { cai ->
        appendLine("── Calcio iónico (Ca²⁺) ──")
        when {
            cai < 1.15 -> {
                appendLine("   Ca²⁺ = ${cai} mmol/L  →  HIPOCALCEMIA IÓNICA")
                appendLine("   (Normal: 1.15–1.30 mmol/L)")
                if (cai < 0.90) {
                    appendLine("   ⚠ Ca²⁺ < 0.90: riesgo de tetania y arritmias — emergencia.")
                }
            }
            cai > 1.30 -> {
                appendLine("   Ca²⁺ = ${cai} mmol/L  →  HIPERCALCEMIA IÓNICA")
                appendLine("   (Normal: 1.15–1.30 mmol/L)")
                if (cai > 1.60) {
                    appendLine("   ⚠ Ca²⁺ > 1.60: hipercalcemia iónica grave.")
                }
            }
            else -> appendLine("   Ca²⁺ = ${cai} mmol/L  →  Normal (1.15–1.30 mmol/L)")
        }
        appendLine("   NOTA: el pH modifica el Ca²⁺: alcalosis → Ca²⁺ baja;")
        appendLine("   acidosis → Ca²⁺ sube (sin cambio en Ca total).")
        appendLine()
    }

    // ── 2. Corrección de calcio total por albúmina (fórmula de Payne) ─────────
    //
    //   Ca_corregido (mmol/L) = Ca_medido + 0.02 × (40 − Albúmina_en_g/L)
    //   Equivalente: Ca_corr = Ca_medido + 0.02 × (4.0 − Albúmina_en_g/dL) × 10
    //              = Ca_medido + 0.2  × (4.0 − Albúmina_en_g/dL)   ← forma simplificada
    //
    //   Ref: Payne RB et al. J Clin Pathol 1973.
    //
    //   LIMITACIÓN: la corrección por albúmina es imprecisa en pacientes críticos
    //   (Dickerson 2004). En UCI, usar siempre Ca iónico directo.

    val albumina_gdL = albumina.coerceIn(0.5, 7.0)
    val caCorregido: Double
    val correccionAplicada: Boolean

    if (albumina_gdL < 3.5) {
        // Hipoalbuminemia: Ca total subestima el Ca real
        caCorregido = ca + 0.2 * (4.0 - albumina_gdL)
        correccionAplicada = true
        appendLine("── Corrección por hipoalbuminemia (Payne) ──")
        appendLine("   Albúmina: ${String.format("%.1f", albumina_gdL)} g/dL  (< 3.5 → corrección necesaria)")
        appendLine("   Ca total medido:    ${String.format("%.2f", ca)} mmol/L")
        appendLine("   Ca total corregido: ${String.format("%.2f", caCorregido)} mmol/L")
        if (caCorregido - ca >= 0.20) {
            appendLine("   ⚠ Diferencia significativa (≥ 0.20 mmol/L):")
            appendLine("     el Ca medido SUBESTIMA el estado real de calcemia.")
        }
        appendLine("   RECOMENDACIÓN: confirmar con Ca iónico directo.")
        appendLine()
    } else if (albumina_gdL > 4.5) {
        // Hiperalbuminemia (deshidratación, mieloma): Ca total sobrestima el Ca real
        caCorregido = ca + 0.2 * (4.0 - albumina_gdL)
        correccionAplicada = true
        appendLine("── Corrección por hiperalbuminemia (Payne) ──")
        appendLine("   Albúmina: ${String.format("%.1f", albumina_gdL)} g/dL  (> 4.5 → corrección aplicada)")
        appendLine("   Ca total medido:    ${String.format("%.2f", ca)} mmol/L")
        appendLine("   Ca total corregido: ${String.format("%.2f", caCorregido)} mmol/L")
        appendLine()
    } else {
        caCorregido = ca
        correccionAplicada = false
    }

    // ── 3. Clasificación usando el Ca corregido ────────────────────────────────

    val caLabel = if (correccionAplicada)
        "Ca corregido = ${String.format("%.2f", caCorregido)} mmol/L"
    else
        "Ca = ${String.format("%.2f", caCorregido)} mmol/L"

    when {

        // ── Normocalcemia ───────────────────────────────────────────────
        caCorregido in 2.10..2.55 -> {
            appendLine("→ NORMOCALCEMIA")
            appendLine("   $caLabel  (rango normal: 2.10–2.55 mmol/L)")
            appendLine("   • Homeostasis del calcio conservada.")
        }

        // ── Hipercalcemia ───────────────────────────────────────────────
        caCorregido in 2.56..2.99 -> {
            appendLine("→ HIPERCALCEMIA LEVE  ($caLabel)")
            appendLine("   Rango: 2.56–2.99 mmol/L")
            appendLine("   • Habitualmente asintomática o con síntomas inespecíficos:")
            appendLine("     poliuria, polidipsia, constipación, fatiga.")
            appendLine("   • Causas más frecuentes (> 90% de los casos):")
            appendLine("     – Hiperparatiroidismo primario (PTH elevada, Ca elevado)")
            appendLine("       → causa más común en pacientes ambulatorios.")
            appendLine("     – Hipercalcemia humoral maligna (PTHrP)")
            appendLine("       → causa más común en pacientes hospitalizados.")
            appendLine("   • Estudio inicial: PTH intacta, PTHrP, 25-OH vitamina D,")
            appendLine("     calcio urinario 24 h, fosfatemia.")
            appendLine("   • Descartar: sarcoidosis, tiazidas, litio, intoxicación vit. D.")
            appendLine("   • Hidratación oral abundante.")
        }

        caCorregido in 3.0..3.49 -> {
            appendLine("→ HIPERCALCEMIA MODERADA  ($caLabel)")
            appendLine("   Rango: 3.00–3.49 mmol/L")
            appendLine("   • Síntomas frecuentes: náuseas, vómitos, confusión,")
            appendLine("     debilidad muscular, letargia, acortamiento QT en ECG.")
            appendLine("   • Causas: neoplasias (mama, pulmón, mieloma, linfoma),")
            appendLine("     hiperparatiroidismo primario sintomático, intoxicación vit. D.")
            appendLine("   • TRATAMIENTO ACTIVO REQUERIDO:")
            appendLine("     1. Hidratación IV agresiva: SF 0.9% a 200–300 mL/h")
            appendLine("        (objetivo: diuresis > 100–150 mL/h).")
            appendLine("     2. Furosemida: SOLO si hay sobrecarga de volumen documentada")
            appendLine("        (no usar de rutina — agrava la hipercalcemia si hay")
            appendLine("        depleción de volumen).")
            appendLine("     3. Bisfosfonatos IV (si hipercalcemia maligna):")
            appendLine("        – Ácido zoledrónico 4 mg IV en 15 min (preferido)")
            appendLine("        – Pamidronato 60–90 mg IV en 2–4 h")
            appendLine("        – Inicio de acción: 48–72 h; pico: 4–7 días.")
            appendLine("     4. Calcitonina 4 UI/kg SC/IM c/12 h (efecto en 4–6 h,")
            appendLine("        pero taquifilaxia en 48 h — usar como puente).")
            appendLine("     5. Denosumab 120 mg SC si refractario a bisfosfonatos")
            appendLine("        (especialmente en IRC).")
            appendLine("   • ECG y monitorización cardíaca.")
            appendLine("   • Calcio urinario, función renal, ionograma cada 6–12 h.")
        }

        caCorregido >= 3.5 -> {
            appendLine("→ HIPERCALCEMIA GRAVE  ($caLabel)  ⚠ CRISIS HIPERCALCÉMICA")
            appendLine("   Umbral: ≥ 3.50 mmol/L")
            appendLine("   • EMERGENCIA ENDOCRINA con mortalidad elevada si no se trata.")
            appendLine("   • Manifestaciones graves: estupor, coma, insuficiencia renal")
            appendLine("     aguda, pancreatitis, arritmias (QT corto, bloqueos AV),")
            appendLine("     crisis psiquiátricas agudas.")
            appendLine()
            appendLine("   PROTOCOLO DE EMERGENCIA:")
            appendLine()
            appendLine("   PASO 1 — Hidratación IV MASIVA (prioridad absoluta):")
            appendLine("     SF 0.9%: 500–1000 mL en 1ª hora, luego 200–300 mL/h.")
            appendLine("     Objetivo: diuresis 100–150 mL/h.")
            appendLine("     Control estricto de balance hídrico.")
            appendLine()
            appendLine("   PASO 2 — Reducción de Ca (inicio simultáneo):")
            appendLine("     • Calcitonina salmón 4–8 UI/kg IM/SC c/6–12 h")
            appendLine("       → efecto más rápido (4–6 h), baja Ca 0.3–0.5 mmol/L.")
            appendLine("     • Ácido zoledrónico 4 mg IV en 15 min")
            appendLine("       → efecto duradero (efecto máximo a los 4–7 días).")
            appendLine("     • Glucocorticoides si causa: sarcoidosis, linfoma,")
            appendLine("       intoxicación vit. D: prednisona 40–60 mg/día.")
            appendLine()
            appendLine("   PASO 3 — Si refractario o falla renal grave:")
            appendLine("     • Denosumab 120 mg SC (de elección en IRC).")
            appendLine("     • Hemodiálisis con baño bajo en calcio (Ca < 1.25 mmol/L)")
            appendLine("       → único tratamiento definitivo en crisis refractaria.")
            appendLine()
            appendLine("   • Monitorización ECG continua.")
            appendLine("   • Ca sérico cada 4–6 h durante tratamiento.")
            appendLine("   • Buscar causa: PTH, PTHrP, imagen ósea, mieloma.")
            if (caCorregido >= 4.0) {
                appendLine()
                appendLine("   ⚠⚠ Ca ≥ 4.0 mmol/L: riesgo inmediato de coma y paro cardíaco.")
                appendLine("      Hemodiálisis urgente + UCI.")
            }
        }

        // ── Hipocalcemia ────────────────────────────────────────────────
        caCorregido in 2.0..2.09 -> {
            appendLine("→ HIPOCALCEMIA LEVE  ($caLabel)")
            appendLine("   Rango: 2.00–2.09 mmol/L")
            appendLine("   • Síntomas: mínimos o ausentes.")
            appendLine("     Parestesias peribucales y acras, hormigueos.")
            appendLine("   • Signo de Chvostek (percusión nervio facial → contracción")
            appendLine("     hemifacial) y Trousseau (espasmo carpopedal con manguito)")
            appendLine("     pueden estar presentes.")
            appendLine("   • Causas frecuentes: hipoparatiroidismo postquirúrgico,")
            appendLine("     déficit de vitamina D, hipomagnesemia, pancreatitis.")
            appendLine("   • IMPORTANTE: corregir SIEMPRE hipomagnesemia si presente")
            appendLine("     (el Mg es necesario para la secreción y acción de PTH).")
            appendLine("   • Tratamiento oral:")
            appendLine("     – Carbonato cálcico 1–2 g de Ca elemental/día en 2–3 tomas")
            appendLine("       (preferir con las comidas para mejor absorción).")
            appendLine("     – Vitamina D: colecalciferol 1000–2000 UI/día si déficit,")
            appendLine("       o calcitriol 0.25–0.5 μg/12h si hipoparatiroidismo.")
        }

        caCorregido in 1.75..1.99 -> {
            appendLine("→ HIPOCALCEMIA MODERADA  ($caLabel)")
            appendLine("   Rango: 1.75–1.99 mmol/L")
            appendLine("   • Síntomas: parestesias, calambres musculares, espasmo")
            appendLine("     carpopedal, ansiedad, irritabilidad.")
            appendLine("   • Signos de Chvostek y Trousseau habitualmente positivos.")
            appendLine("   • Cambios ECG: prolongación del QT (riesgo de torsade de pointes).")
            appendLine("   • ECG recomendado.")
            appendLine("   • Causas: hipoparatiroidismo (postquirúrgico, autoinmune,");
            appendLine("     genético), déficit grave de vitamina D, IRC, malabsorción,")
            appendLine("     pancreatitis aguda, sepsis, transfusiones masivas (citrato).")
            appendLine("   • Tratamiento:")
            appendLine("     – Si tolera vía oral: carbonato/citrato cálcico + calcitriol.")
            appendLine("     – Si sintomático o intolerancia oral: gluconato cálcico IV")
            appendLine("       (ver hipocalcemia grave).")
            appendLine("     – Corregir déficit de Mg antes/simultáneo:")
            appendLine("       sin Mg normal, la hipocalcemia no se corrige.")
        }

        caCorregido < 1.75 -> {
            appendLine("→ HIPOCALCEMIA GRAVE  ($caLabel)  ⚠ EMERGENCIA")
            appendLine("   Umbral: < 1.75 mmol/L")
            appendLine("   • Riesgo vital:")
            appendLine("     – Tetania manifiesta, laringoespasmo, broncoespasmo")
            appendLine("     – Convulsiones refractarias")
            appendLine("     – Arritmias graves (QT largo, torsade de pointes)")
            appendLine("     – Insuficiencia cardíaca (Ca iónico bajo deprime el miocardio)")
            appendLine()
            appendLine("   TRATAMIENTO IV URGENTE:")
            appendLine("     • Gluconato cálcico 10%: 1–2 ampollas (10–20 mL)")
            appendLine("       en 100 mL SF, infundir en 10–20 min.")
            appendLine("       → Sube Ca en 2–3 h; repetir si síntomas persisten.")
            appendLine("     • Infusión de mantenimiento: gluconato cálcico 10%")
            appendLine("       50–100 mL en 500 mL SF a 0.5–2 mg Ca elemental/kg/h.")
            appendLine("     • NUNCA administrar en bolo directo (riesgo de paro cardíaco")
            appendLine("       y necrosis tisular).")
            appendLine("     • NUNCA mezclar con bicarbonato o fosfato (precipita).")
            appendLine()
            appendLine("   CAUSAS URGENTES A DESCARTAR:")
            appendLine("     – Hipoparatiroidismo postoperatorio (cirugía tiroidea/")
            appendLine("       paratiroidea) → más frecuente")
            appendLine("     – Síndrome del hueso hambriento post-paratiroidectomía")
            appendLine("     – Pancreatitis aguda grave")
            appendLine("     – Sepsis / shock séptico")
            appendLine("     – Hipomagnesemia grave (Mg < 0.5 mmol/L)")
            appendLine()
            appendLine("   • Monitorización ECG continua hasta Ca > 2.0 mmol/L.")
            appendLine("   • Determinación de Ca cada 4–6 h durante tratamiento.")
            appendLine("   • Medir PTH, Mg, fósforo, 25-OH vitamina D.")
            if (caCorregido < 1.25) {
                appendLine()
                appendLine("   ⚠⚠ Ca < 1.25 mmol/L: tetania o convulsiones inminentes.")
                appendLine("      Ingreso en UCI, monitorización invasiva.")
            }
        }

        else -> {
            appendLine("→ Valor de Ca fuera del rango evaluable (${String.format("%.2f", caCorregido)} mmol/L).")
            appendLine("   Verificar la muestra y la unidad utilizada.")
            appendLine("   Rango esperado en mmol/L: 0.50–5.00")
            appendLine("   Si el valor está en mg/dL: dividir por 4.")
            appendLine("   Si el valor está en mEq/L: dividir por 2.")
        }
    }
}