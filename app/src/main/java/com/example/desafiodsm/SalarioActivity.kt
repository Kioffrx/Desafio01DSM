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
            validarCampos()
        }

        findViewById<Button>(R.id.btnRegresarMenu2).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun validarCampos() {
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

        tvTituloResultado.text = "Salario válido: $salarioBase"
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