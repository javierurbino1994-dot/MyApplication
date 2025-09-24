package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TransferenciaActivity : AppCompatActivity() {

    private lateinit var etCipEmail: EditText
    private lateinit var btnPagarTransferencia: Button

    // Variables para almacenar los datos del usuario
    private lateinit var nombre: String
    private lateinit var dni: String
    private lateinit var telefono: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transferencia)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        recibirDatosUsuario()
        setupListeners()
    }

    private fun initViews() {
        etCipEmail = findViewById(R.id.etCipEmail)
        btnPagarTransferencia = findViewById(R.id.btnPagarTransferencia)
    }

    private fun recibirDatosUsuario() {
        nombre = intent.getStringExtra("nombre") ?: "Nombre no disponible"
        dni = intent.getStringExtra("dni") ?: "DNI no disponible"
        telefono = intent.getStringExtra("telefono") ?: "Teléfono no disponible"
        email = intent.getStringExtra("email") ?: "Email no disponible"

        // Prellenar el email para el CIP
        etCipEmail.setText(email)
    }

    private fun setupListeners() {
        btnPagarTransferencia.setOnClickListener {
            processTransferenciaPayment()
        }
    }

    private fun processTransferenciaPayment() {
        val cipEmail = etCipEmail.text.toString().trim()

        if (cipEmail.isEmpty()) {
            showToast("Ingresa el email para envío del código CIP")
            etCipEmail.requestFocus()
            return
        }

        if (!isEmailValid(cipEmail)) {
            showToast("Ingresa un email válido")
            etCipEmail.requestFocus()
            return
        }

        showToast("Generando código CIP de PagoEfectivo...")
        processPaymentWithTransferencia(cipEmail)
    }

    private fun processPaymentWithTransferencia(cipEmail: String) {
        btnPagarTransferencia.isEnabled = false
        btnPagarTransferencia.text = "GENERANDO CIP..."

        // Simular generación del código CIP
        android.os.Handler().postDelayed({
            showToast("¡Código CIP generado! Se envió a: $cipEmail")
            btnPagarTransferencia.isEnabled = true
            btnPagarTransferencia.text = "PAGAR S/ 99.75"

            // Navegar a TicketActivity con los datos del usuario
            navigateToTicketActivity(cipEmail)
        }, 3000)
    }

    private fun navigateToTicketActivity(cipEmail: String) {
        val intent = Intent(this, TicketActivity::class.java).apply {
            putExtra("nombre", nombre)
            putExtra("dni", dni)
            putExtra("telefono", telefono)
            putExtra("email", cipEmail)
        }

        startActivity(intent)
        finish()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}