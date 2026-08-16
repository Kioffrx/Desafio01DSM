package com.example.desafiodsm

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

class CalculadoraActivity : AppCompatActivity() {

    private lateinit var etNum1: EditText
    private lateinit var etNum2: EditText
    private lateinit var spinnerOperacion: Spinner
    private lateinit var btnOperar: Button
    private lateinit var tvResultadoCalc: TextView
    private lateinit var btnHistorial: Button

    private val NOMBRE_ARCHIVO = "historial.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)
        spinnerOperacion = findViewById(R.id.spinnerOperacion)
        btnOperar = findViewById(R.id.btnOperar)
        tvResultadoCalc = findViewById(R.id.tvResultadoCalc)
        btnHistorial = findViewById(R.id.btnHistorial)

        val adapter = ArrayAdapter.createFromResource(
            this, R.array.operaciones, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOperacion.adapter = adapter

        btnOperar.setOnClickListener { procesarOperacion() }
        btnHistorial.setOnClickListener { mostrarHistorial() }
    }
    private fun operar(op: String, n1: Double, n2: Double): Double? {
        return when (op) {
            "Suma" -> n1 + n2
            "Resta" -> n1 - n2
            "Multiplicación" -> n1 * n2
            "División" -> {
                if (n2 == 0.0) {
                    tvResultadoCalc.text = getString(R.string.error_division_cero)
                    return null
                }
                n1 / n2
            }
            "Exponente" -> n1.pow(n2)
            "Raíz cuadrada" -> {
                if (n1 < 0.0) {
                    tvResultadoCalc.text = getString(R.string.error_raiz_negativa)
                    return null
                }
                sqrt(n1)
            }
            else -> null
        }
    }
    private fun procesarOperacion() {
        val texto1 = etNum1.text.toString().trim()
        if (texto1.isEmpty()) {
            etNum1.error = getString(R.string.error_campo_vacio)
            return
        }
        val n1 = texto1.toDoubleOrNull()
        if (n1 == null) {
            etNum1.error = getString(R.string.error_nota_invalida)
            return
        }

        val operacion = spinnerOperacion.selectedItem.toString()

        // Raíz cuadrada solo usa un número
        var n2 = 0.0
        if (operacion != "Raíz cuadrada") {
            val texto2 = etNum2.text.toString().trim()
            if (texto2.isEmpty()) {
                etNum2.error = getString(R.string.error_campo_vacio)
                return
            }
            n2 = texto2.toDoubleOrNull() ?: run {
                etNum2.error = getString(R.string.error_nota_invalida)
                return
            }
        }

        val resultado = operar(operacion, n1, n2) ?: return

        val formato = DecimalFormat("#.####")
        val resultadoTexto = formato.format(resultado)
        val linea = if (operacion == "Raíz cuadrada")
            "√$n1 = $resultadoTexto"
        else
            "$n1 $operacion $n2 = $resultadoTexto"

        tvResultadoCalc.text = linea
        guardarEnHistorial(linea)
    }

}