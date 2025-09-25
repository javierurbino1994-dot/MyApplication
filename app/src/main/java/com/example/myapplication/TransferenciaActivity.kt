package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    private lateinit var nombre: String
    private lateinit var dni: String
    private lateinit var telefono: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transferencia)

        // ✅ Usamos android.R.id.content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
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
        nombre = intent.getStringExtra("nombre") ?: getString(R.string.dato_no_disponible)
        dni = intent.getStringExtra("dni") ?: getString(R.string.dato_no_disponible)
        telefono = intent.getStringExtra("telefono") ?: getString(R.string.dato_no_disponible)
        email = intent.getStringExtra("email") ?: getString(R.string.dato_no_disponible)
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
            showToast(getString(R.string.error_email_cip_empty))
            etCipEmail.requestFocus()
            return
        }

        if (!isEmailValid(cipEmail)) {
            showToast(getString(R.string.error_email_invalid))
            etCipEmail.requestFocus()
            return
        }

        showToast(getString(R.string.generating_cip))
        processPaymentWithTransferencia(cipEmail)
    }

    private fun processPaymentWithTransferencia(cipEmail: String) {
        btnPagarTransferencia.isEnabled = false
        btnPagarTransferencia.text = getString(R.string.generating_cip_button)

        Handler(Looper.getMainLooper()).postDelayed({
            showToast(getString(R.string.cip_generated, cipEmail))
            btnPagarTransferencia.isEnabled = true
            btnPagarTransferencia.text = getString(R.string.pay_amount)
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}

