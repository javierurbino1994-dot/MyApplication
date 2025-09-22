package com.example.myapplication


import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class TicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Oculta la barra superior
        setContentView(R.layout.activity_ticket) // asegúrate que tu xml se llame activity_ticket.xml

        // Referencias a los elementos del XML
        val ivQrCode = findViewById<ImageView>(R.id.ivQrCode)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvDni = findViewById<TextView>(R.id.tvDni)
        val tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvContador = findViewById<TextView>(R.id.tvContador)
        val tvInstruccion = findViewById<TextView>(R.id.tvInstruccion)

        // Supongamos que recibes los datos desde un Intent
        val nombre = intent.getStringExtra("nombre") ?: "Javier"
        val dni = intent.getStringExtra("dni") ?: "12345678"
        val telefono = intent.getStringExtra("telefono") ?: "987654321"
        val email = intent.getStringExtra("email") ?: "javier@gmail.com"

        // Asignar valores a los TextView
        tvNombre.text = "Nombre: $nombre"
        tvDni.text = "DNI: $dni"
        tvTelefono.text = "Teléfono: $telefono"
        tvEmail.text = "Correo: $email"
        tvContador.text = "Tiempo estimado para recoger: 00:20:00"
        tvInstruccion.text = "Muestra este ticket en caja para recoger tus productos."

        // Generar QR con los datos
        val qrData = "Nombre: $nombre\nDNI: $dni\nTeléfono: $telefono\nCorreo: $email"
        val qrBitmap = generateQrCode(qrData)
        ivQrCode.setImageBitmap(qrBitmap)
    }

    // Función para generar el QR Code
    private fun generateQrCode(text: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bmp
    }
}
