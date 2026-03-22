package com.numel.abvcalculator.data

/**
 * Clasificación de discaliemia según literatura médica actualizada.
 *
 * Referencias:
 * - Kardalas E et al. Hypokalemia: a clinical update. Endocr Connect 2018; 7(4):R135-R146.
 * - Mount DB. Fluid and Electrolyte Disturbances. Harrison's Principles, 20th ed. 2018.
 * - Unwin RJ, Luft FC, Shirley DG. Pathophysiology and management of hypokalemia.
 *   Nat Rev Nephrol 2011; 7(2):75-84.
 * - Weir MR, Rolfe M. Potassium homeostasis and renin-angiotensin-aldosterone system
 *   inhibitors. Clin J Am Soc Nephrol 2010; 5(3):531-48.
 * - Rossignol P et al. Hyperkalemia: a frequent, under-recognised and dangerous event.
 *   ESC position paper. Eur Heart J 2020; 41(36):3451-3463.
 * - Palmer BF, Clegg DJ. Diagnosis and treatment of hyperkalemia.
 *   Cleve Clin J Med 2017; 84(12):934-942.
 * - KDIGO Controversies Conference on Potassium in Chronic Kidney Disease. 2020.
 * - Kovesdy CP. Management of hyperkalaemia in chronic kidney disease.
 *   Nat Rev Nephrol 2014; 10(11):653-62.
 *
 * ─── Hipopotasemia (K < 3.5 mEq/L) ──────────────────────────────────────────
 *  Leve     : 3.0–3.4 mEq/L  → síntomas mínimos; riesgo en cardiopatía o digital
 *  Moderada : 2.5–2.9 mEq/L  → debilidad muscular, calambres, cambios ECG
 *  Grave    : < 2.5 mEq/L    → riesgo de rabdomiólisis, parálisis, arritmias fatales
 *
 * ─── Hiperpotasemia (K > 5.5 mEq/L) ─────────────────────────────────────────
 *  Leve     : 5.5–5.9 mEq/L  → habitualmente asintomática; monitorizar
 *  Moderada : 6.0–6.4 mEq/L  → cambios ECG posibles (T picudas); tratar activamente
 *  Grave    : ≥ 6.5 mEq/L    → EMERGENCIA: riesgo de fibrilación ventricular y paro
 *
 * NOTA CRÍTICA: el umbral de TRATAMIENTO DE EMERGENCIA es ≥ 6.5 mEq/L
 * (o menor si hay cambios ECG). El original usaba 7.0 como corte de "grave",
 * lo cual retrasa peligrosamente la intervención.
 */
fun potassium(k: Double): String = buildString {

    appendLine()

    when {

        // ── Normocaliemia ────────────────────────────────────────────────
        k in 3.5..5.5 -> {
            appendLine("→ NORMOCALIEMIA")
            appendLine("   K = $k mEq/L  (rango normal: 3.5–5.5 mEq/L)")
            appendLine("   • Homeostasis del K conservada.")
        }

        // ── Hiperpotasemia ────────────────────────────────────────────────
        k in 5.5..5.99 -> {
            appendLine("→ HIPERPOTASEMIA LEVE  (K = $k mEq/L)")
            appendLine("   Rango: 5.5–5.9 mEq/L")
            appendLine("   • Habitualmente asintomática.")
            appendLine("   • Descartar PSEUDOHIPERPOTASEMIA:")
            appendLine("     – Leucocitosis > 100 000/mm³")
            appendLine("     – Trombocitosis > 1 000 000/mm³")
            appendLine("     – Hemólisis en la muestra")
            appendLine("     → Repetir con muestra sin hemólisis y procesar rápido.")
            appendLine("   • Buscar causa: IECA/ARA-II, diuréticos ahorradores de K,")
            appendLine("     insuficiencia renal, insuficiencia adrenal, acidosis.")
            appendLine("   • Realizar ECG (buscar ondas T picudas).")
            appendLine("   • Medidas: restricción dietética de K (< 40–60 mEq/día),")
            appendLine("     revisar fármacos causales, tratar acidosis subyacente.")
        }

        k in 6.0..6.49 -> {
            appendLine("→ HIPERPOTASEMIA MODERADA  (K = $k mEq/L)")
            appendLine("   Rango: 6.0–6.4 mEq/L")
            appendLine("   • Riesgo de alteraciones ECG: ondas T altas y simétricas,")
            appendLine("     acortamiento del QT.")
            appendLine("   • ECG OBLIGATORIO e inmediato.")
            appendLine("   • Si hay cambios ECG → tratar como GRAVE.")
            appendLine("   • Tratamiento activo:")
            appendLine("     1. Estabilización de membrana si hay cambios ECG:")
            appendLine("        Gluconato cálcico 10%: 10 mL IV en 2–3 min")
            appendLine("        (efecto en 1–3 min, dura 30–60 min).")
            appendLine("     2. Desplazamiento intracelular de K:")
            appendLine("        – Insulina 10 UI + glucosa 50% 50 mL IV")
            appendLine("          (baja K 0.5–1.5 mEq/L en 15–30 min).")
            appendLine("        – Salbutamol nebulizado 10–20 mg")
            appendLine("          (baja K 0.5–1.0 mEq/L adicional).")
            appendLine("        – Bicarbonato sódico 1 mEq/kg si acidosis metabólica.")
            appendLine("     3. Eliminación de K:")
            appendLine("        – Furosemida IV si diuresis conservada.")
            appendLine("        – Resinas de intercambio (patirómero, ciclosilicato")
            appendLine("          de sodio y circonio) — inicio de acción en horas.")
            appendLine("        – Diálisis si falla renal establecida.")
            appendLine("   • Monitorización continua de ECG y K cada 2–4 h.")
        }

        k >= 6.5 -> {
            appendLine("→ HIPERPOTASEMIA GRAVE  (K = $k mEq/L)  ⚠ EMERGENCIA")
            appendLine("   Umbral: ≥ 6.5 mEq/L  (o < 6.5 con cambios ECG)")
            appendLine("   • RIESGO VITAL INMEDIATO:")
            appendLine("     – Ensanchamiento del QRS, onda sinusoidal,")
            appendLine("       fibrilación ventricular, asistolia.")
            appendLine("   • PROTOCOLO DE EMERGENCIA:")
            appendLine()
            appendLine("   PASO 1 — Estabilización de membrana (SIEMPRE primero):")
            appendLine("     Gluconato cálcico 10%: 10–20 mL IV en 2–3 min.")
            appendLine("     Repetir si persisten cambios ECG (máx 3 dosis).")
            appendLine("     NO usar calcio si intoxicación digitálica.")
            appendLine()
            appendLine("   PASO 2 — Desplazamiento intracelular (efecto en minutos):")
            appendLine("     • Insulina 10 UI IV + glucosa 50% 50 mL")
            appendLine("       → baja K 0.5–1.5 mEq/L en 15–30 min.")
            appendLine("     • Salbutamol 10–20 mg nebulizado o 0.5 mg IV")
            appendLine("       → baja K 0.5–1.0 mEq/L adicional.")
            appendLine("     • Bicarbonato 1 mEq/kg IV si acidosis metabólica grave.")
            appendLine()
            appendLine("   PASO 3 — Eliminación definitiva de K (único tratamiento real):")
            appendLine("     • Furosemida IV si diuresis conservada.")
            appendLine("     • Patirómero o ciclosilicato de Na-Zr vía oral")
            appendLine("       (inicio en 1–6 h; preferibles a la resina de poliestireno).")
            appendLine("     • HEMODIÁLISIS de urgencia si:")
            appendLine("       – Oligoanuria / IRA / IRC avanzada")
            appendLine("       – K > 7.0 mEq/L refractario a tratamiento médico")
            appendLine("       – Inestabilidad hemodinámica")
            appendLine()
            appendLine("   • Monitorización ECG continua hasta K < 5.5 mEq/L.")
            appendLine("   • Control de K cada 1–2 h durante tratamiento activo.")
            if (k > 8.5) {
                appendLine()
                appendLine("   ⚠⚠ K > 8.5 mEq/L: riesgo extremo de paro cardíaco.")
                appendLine("      Activar código de emergencia / preparar diálisis urgente.")
            }
        }

        // ── Hipopotasemia ─────────────────────────────────────────────────
        k in 3.0..3.49 -> {
            appendLine("→ HIPOPOTASEMIA LEVE  (K = $k mEq/L)")
            appendLine("   Rango: 3.0–3.4 mEq/L")
            appendLine("   • Síntomas: habitualmente ausentes o leves")
            appendLine("     (fatiga, calambres leves).")
            appendLine("   • RIESGO AUMENTADO si: cardiopatía isquémica, uso de")
            appendLine("     digoxina, antiarrítmicos, QT largo — tratar aunque asintomático.")
            appendLine("   • Evaluar causa: diuréticos, vómitos, diarrea,")
            appendLine("     hiperaldosteronismo, alcalosis metabólica, hipomagnesemia.")
            appendLine("   • IMPORTANTE: el K sérico subestima el déficit total.")
            appendLine("     Cada 1 mEq/L de caída ≈ déficit de 200–400 mEq de K corporal.")
            appendLine("   • Reposición preferentemente oral:")
            appendLine("     KCl oral 40–100 mEq/día en dosis divididas.")
            appendLine("   • Si no tolera vía oral: KCl IV diluido en SSF,")
            appendLine("     ritmo máx 10–20 mEq/h por vía periférica.")
            appendLine("   • Corregir hipomagnesemia concomitante (impide corrección del K).")
        }

        k in 2.5..2.99 -> {
            appendLine("→ HIPOPOTASEMIA MODERADA  (K = $k mEq/L)")
            appendLine("   Rango: 2.5–2.9 mEq/L")
            appendLine("   • Síntomas frecuentes: debilidad muscular generalizada,")
            appendLine("     calambres, mialgias, íleo paralítico, poliuria.")
            appendLine("   • Cambios ECG posibles: depresión del ST, onda U prominente,")
            appendLine("     aplanamiento de onda T, QT prolongado.")
            appendLine("   • ECG recomendado.")
            appendLine("   • Déficit estimado total: 400–800 mEq de K.")
            appendLine("   • Reposición combinada oral + IV:")
            appendLine("     – KCl oral: 60–120 mEq/día si tolera.")
            appendLine("     – KCl IV: 20–40 mEq/h en vía central;")
            appendLine("       máx 10–20 mEq/h en vía periférica (irritante vascular).")
            appendLine("     – Diluir siempre: ≤ 40 mEq por cada 250 mL de SSF.")
            appendLine("     – NUNCA administrar K en bolo directo IV → paro cardíaco.")
            appendLine("   • Corregir hipomagnesemia (Mg < 0.7 mmol/L impide")
            appendLine("     la corrección del K por pérdida renal continua).")
            appendLine("   • Monitorizar K cada 4–6 h durante reposición.")
        }

        k < 2.5 -> {
            appendLine("→ HIPOPOTASEMIA GRAVE  (K = $k mEq/L)  ⚠ EMERGENCIA")
            appendLine("   Umbral: < 2.5 mEq/L")
            appendLine("   • Riesgo vital:")
            appendLine("     – Arritmias ventriculares fatales (FV, TV)")
            appendLine("     – Parálisis muscular (incluida parálisis respiratoria)")
            appendLine("     – Rabdomiólisis")
            appendLine("     – Íleo paralítico grave")
            appendLine("   • Cambios ECG graves: onda U marcada, T plana o invertida,")
            appendLine("     fusión T-U, QT prolongado, arritmias ventriculares.")
            appendLine("   • ECG URGENTE + monitorización continua.")
            appendLine()
            appendLine("   REPOSICIÓN IV URGENTE:")
            appendLine("     • Vía central preferida para tasas > 10 mEq/h.")
            appendLine("     • KCl IV: 10–20 mEq/h en vía central")
            appendLine("       (máx 40 mEq/h en situaciones críticas con monitoreo).")
            appendLine("     • Déficit estimado total: > 600–1000 mEq.")
            appendLine("     • La corrección es LENTA: puede requerir 24–72 h.")
            appendLine("     • Control de K cada 2–4 h durante reposición activa.")
            appendLine()
            appendLine("   PRECAUCIONES CRÍTICAS:")
            appendLine("     • NUNCA K en bolo IV directo → paro cardíaco inmediato.")
            appendLine("     • Corregir HIPOMAGNESEMIA antes/simultáneo: sin Mg")
            appendLine("       normal el riñón no retiene el K repuesto.")
            appendLine("     • Evitar soluciones glucosadas durante reposición")
            appendLine("       (la insulina endógena desplaza aún más K al intracelular).")
            appendLine("     • Vigilar función respiratoria (riesgo de parálisis diafragmática).")
            if (k < 1.5) {
                appendLine()
                appendLine("   ⚠⚠ K < 1.5 mEq/L: parálisis y paro cardíaco inminentes.")
                appendLine("      Ingreso en UCI con monitorización invasiva.")
            }
        }

        else -> {
            appendLine("→ Valor de K fuera del rango evaluable ($k mEq/L).")
            appendLine("   Verificar la muestra y repetir la determinación.")
        }
    }
}