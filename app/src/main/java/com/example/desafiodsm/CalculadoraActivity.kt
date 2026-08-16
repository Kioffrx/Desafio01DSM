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

}