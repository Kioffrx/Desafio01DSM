# Desafío 01 - Desarrollo de Software para Móvil (DSM)

Universidad Don Bosco — Escuela de Ingeniería en Computación
Primer Desafío Práctico (10%)

Aplicación Android desarrollada en **Kotlin** que resuelve tres ejercicios prácticos:

1. **Promedio del Estudiante** — cálculo de promedio ponderado con notificación de resultado.
2. **Descuentos al Salario** — cálculo de Renta, AFP e ISSS con feedback háptico en errores.
3. **Calculadora Básica** — seis operaciones matemáticas con historial persistente.

---

## 🎥 Video de defensa

🎥 [Ver video explicativo del proyecto](https://drive.google.com/drive/folders/1E4r2XmaNGAWwkKPMVa-5OZvnvmJO7yKw?usp=sharing)

---

## 📱 Descargar el APK

El APK compilado está disponible en la carpeta [`apk/DesafioDSM.apk`](apk/DesafioDSM.apk) de este repositorio.

**Para instalarlo en tu celular:**
1. Descarga el archivo `DesafioDSM.apk` desde GitHub.
2. Habilita "Instalar apps de fuentes desconocidas" si tu Android lo solicita.
3. Abre el archivo descargado para instalarlo.

---

## 🧮 Ejercicio 1: Promedio del Estudiante

- Solicita nombre del estudiante y 5 notas con ponderaciones distintas (20%, 20%, 20%, 15%, 25%).
- Valida que cada nota esté entre 0 y 10.
- Calcula el promedio final con `DecimalFormat` (2 decimales) e indica si el estudiante **aprobó** (≥6.0) o **reprobó**.
- Envía una notificación push con el resultado (solicitando el permiso `POST_NOTIFICATIONS` en tiempo de ejecución).

## 💵 Ejercicio 2: Descuentos al Salario

- Solicita nombre del empleado y salario base.
- Calcula los descuentos legales:
  - **Renta (ISR)** — según tabla de tramos oficial.
  - **AFP** — 7.25% del salario.
  - **ISSS** — 3%, con tope de cálculo en $1000.
- Muestra el detalle completo: salario bruto, cada descuento, total de descuentos y salario neto.
- Si el salario es inválido (vacío o negativo), el dispositivo vibra además de mostrar el error (permiso `VIBRATE`).

## ➗ Ejercicio 3: Calculadora Básica

- Seis operaciones seleccionables por Spinner: suma, resta, multiplicación, división, exponente y raíz cuadrada.
- Valida división entre cero y raíz de números negativos.
- Guarda cada operación en un historial persistente usando almacenamiento interno (`openFileOutput` / `openFileInput`).

---

## 🛠️ Tecnologías

- Kotlin
- Android SDK (minSdk 24, targetSdk 36)
- Material Design 3
- ConstraintLayout / LinearLayout
- View Binding con `findViewById`
- Notificaciones (`NotificationCompat`) y permisos en tiempo de ejecución (`ActivityResultContracts`)

## 📂 Estructura del proyecto

```
app/src/main/java/com/example/desafiodsm/
├── MainActivity.kt          → Menú principal
├── PromedioActivity.kt      → Ejercicio 1
├── SalarioActivity.kt       → Ejercicio 2
└── CalculadoraActivity.kt   → Ejercicio 3
```

---

**Autor:** Kioffrx
**Materia:** Desarrollo de Software para Móvil — CII 2026
