package com.example.desafiodsm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

class PromedioActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etNota1: EditText
    private lateinit var etNota2: EditText
    private lateinit var etNota3: EditText
    private lateinit var etNota4: EditText
    private lateinit var etNota5: EditText
    private lateinit var tvResultado: TextView

    private val formatoDecimal = DecimalFormat("#.##")
    private val notaMinima = 0.0
    private val notaMaxima = 10.0
    private val promedioAprobatorio = 6.0

    private val ponderacion1 = 0.20
    private val ponderacion2 = 0.20
    private val ponderacion3 = 0.20
    private val ponderacion4 = 0.15
    private val ponderacion5 = 0.25

    private var promedioPendiente = 0.0
    private var nombrePendiente = ""
    private var aprobadoPendiente = false

    private val solicitarPermisoNotificacion =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
            if (concedido) {
                mostrarNotificacion(nombrePendiente, promedioPendiente, aprobadoPendiente)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio)

        etNombre = findViewById(R.id.etNombre)
        etNota1 = findViewById(R.id.etNota1)
        etNota2 = findViewById(R.id.etNota2)
        etNota3 = findViewById(R.id.etNota3)
        etNota4 = findViewById(R.id.etNota4)
        etNota5 = findViewById(R.id.etNota5)
        tvResultado = findViewById(R.id.tvResultadoPromedio)

        crearCanalNotificacion()

        findViewById<Button>(R.id.btnCalcularPromedio).setOnClickListener {
            calcularPromedio()
        }

        findViewById<Button>(R.id.btnRegresarMenu1).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularPromedio() {
        val nombre = etNombre.text.toString().trim()
        if (nombre.isEmpty()) {
            etNombre.error = getString(R.string.promedio_error_nombre)
            return
        }

        val nota1 = validarNota(etNota1) ?: return
        val nota2 = validarNota(etNota2) ?: return
        val nota3 = validarNota(etNota3) ?: return
        val nota4 = validarNota(etNota4) ?: return
        val nota5 = validarNota(etNota5) ?: return

        val promedio = (nota1 * ponderacion1) + (nota2 * ponderacion2) +
                (nota3 * ponderacion3) + (nota4 * ponderacion4) +
                (nota5 * ponderacion5)

        val aprobado = promedio >= promedioAprobatorio
        val promedioTexto = formatoDecimal.format(promedio)

        val estadoTexto = if (aprobado) {
            getString(R.string.promedio_aprobado, nombre)
        } else {
            getString(R.string.promedio_reprobado, nombre)
        }

        tvResultado.text = "${getString(R.string.promedio_resultado, promedioTexto)}\n$estadoTexto"
        tvResultado.setTextColor(
            if (aprobado) ContextCompat.getColor(this, R.color.resultado_aprobado)
            else ContextCompat.getColor(this, R.color.resultado_reprobado)
        )

        promedioPendiente = promedio
        nombrePendiente = nombre
        aprobadoPendiente = aprobado
        enviarNotificacionConPermiso(nombre, promedio, aprobado)
    }

    private fun validarNota(editText: EditText): Double? {
        val texto = editText.text.toString().trim()
        if (texto.isEmpty()) {
            editText.error = getString(R.string.promedio_error_vacio)
            return null
        }
        val valor = texto.toDoubleOrNull()
        if (valor == null || valor < notaMinima || valor > notaMaxima) {
            editText.error = getString(R.string.promedio_error_rango)
            return null
        }
        return valor
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                getString(R.string.notificacion_canal_id),
                getString(R.string.notificacion_canal_nombre),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }

    private fun enviarNotificacionConPermiso(nombre: String, promedio: Double, aprobado: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val tienePermiso = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (tienePermiso) {
                mostrarNotificacion(nombre, promedio, aprobado)
            } else {
                solicitarPermisoNotificacion.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            mostrarNotificacion(nombre, promedio, aprobado)
        }
    }

    private fun mostrarNotificacion(nombre: String, promedio: Double, aprobado: Boolean) {
        val estado = if (aprobado) getString(R.string.promedio_aprobado, nombre)
        else getString(R.string.promedio_reprobado, nombre)

        val mensaje = "${getString(R.string.promedio_resultado, formatoDecimal.format(promedio))} - $estado"

        val builder = NotificationCompat.Builder(this, getString(R.string.notificacion_canal_id))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notificacion_titulo))
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(this).notify(1001, builder.build())
    }
}