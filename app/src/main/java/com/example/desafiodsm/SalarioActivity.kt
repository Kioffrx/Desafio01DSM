package com.example.desafiodsm

import android.content.Intent
import android.os.Bundle
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

        findViewById<Button>(R.id.btnRegresarMenu2).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}