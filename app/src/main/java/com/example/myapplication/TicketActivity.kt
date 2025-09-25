package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import androidx.core.graphics.createBitmap
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.graphics.set


class TicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_ticket)

        // Referencias UI
        val ivQrCode = findViewById<ImageView>(R.id.ivQrCode)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvApellido = findViewById<TextView>(R.id.tvApellido)
        val tvDni = findViewById<TextView>(R.id.tvDni)
        val tvTelefono = findViewById<TextView>(R.id.tvCelular)
        val tvEmail = findViewById<TextView>(R.id.tvCorreo)
        val tvContador = findViewById<TextView>(R.id.tvContador)
        val tvInstruccion = findViewById<TextView>(R.id.tvInstruccion)
        val tvFechaHora = findViewById<TextView>(R.id.tvFechaHora)

        // Datos recibidos por Intent
        val nombre = intent.getStringExtra("nombre") ?: "Javier"
        val apellido = intent.getStringExtra("apellido") ?: "Obregón Urbino"
        val dni = intent.getStringExtra("dni") ?: "12345678"
        val telefono = intent.getStringExtra("telefono") ?: "987654321"
        val email = intent.getStringExtra("email") ?: "javier@gmail.com"

        // Asignar con placeholders desde strings.xml
        tvNombre.text = getString(R.string.ticket_nombre, nombre)
        tvApellido.text = getString(R.string.ticket_apellido, apellido)
        tvDni.text = getString(R.string.ticket_dni, dni)
        tvTelefono.text = getString(R.string.ticket_telefono, telefono)
        tvEmail.text = getString(R.string.ticket_email, email)
        tvContador.text = getString(R.string.ticket_tiempo)
        tvInstruccion.text = getString(R.string.ticket_instruccion)
        tvFechaHora.text = getString(R.string.ticket_fecha_hora, obtenerFechaHoraActual())

        // Generar QR con datos
        val qrData = """
            Nombre: $nombre
            Apellido: $apellido
            DNI: $dni
            Teléfono: $telefono
            Correo: $email
            Generado el: ${obtenerFechaHoraActual()}
        """.trimIndent()

        ivQrCode.setImageBitmap(generateQrCode(qrData))
    }

    // Generar código QR con KTX
    private fun generateQrCode(text: String): Bitmap {
        val size = 512
        val bitMatrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size)
        val bmp = createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bmp[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        return bmp
    }

    // Obtener fecha y hora actual (solo una vez)
    private fun obtenerFechaHoraActual(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formato.format(Date())
    }
}
