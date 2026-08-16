package com.example.desafiodsm

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class SalarioActivity : AppCompatActivity() {

    private lateinit var etNombreEmpleado: EditText
    private lateinit var etSalarioBase: EditText
    private lateinit var tvTituloResultado: TextView
    private lateinit var tvSalarioBruto: TextView
    private lateinit var tvRenta: TextView
    private lateinit var tvAfp: TextView
    private lateinit var tvIsss: TextView
    private lateinit var tvTotalDescuentos: TextView
    private lateinit var tvSalarioNeto: TextView

    private val formato = DecimalFormat("#,##0.00")
    private val porcentajeAfp = 0.0725
    private val porcentajeIsss = 0.03
    private val topeIsss = 1000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        etNombreEmpleado = findViewById(R.id.etNombreEmpleado)
        etSalarioBase = findViewById(R.id.etSalarioBase)
        tvTituloResultado = findViewById(R.id.tvTituloResultadoSalario)
        tvSalarioBruto = findViewById(R.id.tvSalarioBruto)
        tvRenta = findViewById(R.id.tvRenta)
        tvAfp = findViewById(R.id.tvAfp)
        tvIsss = findViewById(R.id.tvIsss)
        tvTotalDescuentos = findViewById(R.id.tvTotalDescuentos)
        tvSalarioNeto = findViewById(R.id.tvSalarioNeto)

        findViewById<Button>(R.id.btnCalcularSalario).setOnClickListener {
            calcularDescuentos()
        }

        findViewById<Button>(R.id.btnRegresarMenu2).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularDescuentos() {
        val nombre = etNombreEmpleado.text.toString().trim()
        if (nombre.isEmpty()) {
            etNombreEmpleado.error = getString(R.string.salario_error_nombre)
            vibrarDispositivo()
            return
        }

        val textoSalario = etSalarioBase.text.toString().trim()
        if (textoSalario.isEmpty()) {
            etSalarioBase.error = getString(R.string.salario_error_vacio)
            vibrarDispositivo()
            return
        }

        val salarioBase = textoSalario.toDoubleOrNull()
        if (salarioBase == null || salarioBase <= 0.0) {
            etSalarioBase.error = getString(R.string.salario_error_negativo)
            vibrarDispositivo()
            return
        }

        val renta = calcularRenta(salarioBase)
        val afp = salarioBase * porcentajeAfp
        val baseIsss = if (salarioBase > topeIsss) topeIsss else salarioBase
        val isss = baseIsss * porcentajeIsss

        val totalDescuentos = renta + afp + isss
        val salarioNeto = salarioBase - totalDescuentos

        tvTituloResultado.text = getString(R.string.salario_resultado_titulo, nombre)
        tvSalarioBruto.text = getString(R.string.salario_bruto, formato.format(salarioBase))
        tvRenta.text = getString(R.string.salario_renta, formato.format(renta))
        tvAfp.text = getString(R.string.salario_afp, formato.format(afp))
        tvIsss.text = getString(R.string.salario_isss, formato.format(isss))
        tvTotalDescuentos.text = getString(R.string.salario_total_descuentos, formato.format(totalDescuentos))
        tvSalarioNeto.text = getString(R.string.salario_neto, formato.format(salarioNeto))
    }

    /**
     * Calcula el descuento de Renta (ISR) según la tabla de tramos.
     */
    private fun calcularRenta(salario: Double): Double {
        return when {
            salario <= 472.00 -> 0.0
            salario <= 895.24 -> (salario - 472.00) * 0.10 + 17.67
            salario <= 2038.10 -> (salario - 895.24) * 0.20 + 60.00
            else -> (salario - 2038.10) * 0.30 + 288.57
        }
    }

    private fun vibrarDispositivo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @Suppress("DEPRECATION")
            val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
            @Suppress("DEPRECATION")
            vibrator.vibrate(300)
        }
    }
}