package com.example.myapplication

import android.annotation.SuppressLint
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

class TarjetaPaymentActivity : AppCompatActivity() {

    private lateinit var etCardNumber: EditText
    private lateinit var etExpiryDate: EditText
    private lateinit var etCvv: EditText
    private lateinit var etCardHolder: EditText
    private lateinit var etVoucherEmail: EditText
    private lateinit var btnPagarTarjeta: Button

    private lateinit var nombre: String
    private lateinit var apellido: String
    private lateinit var dni: String
    private lateinit var telefono: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjeta_payment)

        // ✅ Root para los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        recibirDatosUsuario()
        setupListeners()
    }

    private fun recibirDatosUsuario() {
        nombre = intent.getStringExtra("nombre") ?: getString(R.string.dato_no_disponible)
        apellido = intent.getStringExtra("apellido") ?: getString(R.string.dato_no_disponible)
        dni = intent.getStringExtra("dni") ?: getString(R.string.dato_no_disponible)
        telefono = intent.getStringExtra("telefono") ?: getString(R.string.dato_no_disponible)
        email = intent.getStringExtra("email") ?: getString(R.string.dato_no_disponible)

        etVoucherEmail.setText(email)
    }

    private fun initViews() {
        etCardNumber = findViewById(R.id.etCardNumber)
        etExpiryDate = findViewById(R.id.etExpiryDate)
        etCvv = findViewById(R.id.etCvv)
        etCardHolder = findViewById(R.id.etCardHolder)
        etVoucherEmail = findViewById(R.id.etVoucherEmail)
        btnPagarTarjeta = findViewById(R.id.btnPagarTarjeta)
    }

    private fun setupListeners() {
        btnPagarTarjeta.setOnClickListener { processCardPayment() }
        etCardNumber.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) formatCardNumber() }
        etExpiryDate.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) formatExpiryDate() }
    }

    private fun processCardPayment() {
        val cardNumber = etCardNumber.text.toString().replace(" ", "")
        val expiryDate = etExpiryDate.text.toString()
        val cvv = etCvv.text.toString()
        val cardHolder = etCardHolder.text.toString().trim()
        val voucherEmail = etVoucherEmail.text.toString().trim()

        when {
            cardNumber.isEmpty() -> { showToast(getString(R.string.error_card_number)); etCardNumber.requestFocus(); return }
            !isCardNumberValid(cardNumber) -> { showToast(getString(R.string.error_card_invalid)); etCardNumber.requestFocus(); return }
            expiryDate.isEmpty() -> { showToast(getString(R.string.error_expiry_empty)); etExpiryDate.requestFocus(); return }
            !isExpiryDateValid(expiryDate) -> { showToast(getString(R.string.error_expiry_invalid)); etExpiryDate.requestFocus(); return }
            cvv.isEmpty() -> { showToast(getString(R.string.error_cvv_empty)); etCvv.requestFocus(); return }
            !isCvvValid(cvv) -> { showToast(getString(R.string.error_cvv_invalid)); etCvv.requestFocus(); return }
            cardHolder.isEmpty() -> { showToast(getString(R.string.error_cardholder)); etCardHolder.requestFocus(); return }
            voucherEmail.isEmpty() || !isEmailValid(voucherEmail) -> { showToast(getString(R.string.error_email_invalid)); etVoucherEmail.requestFocus(); return }
        }

        showToast(getString(R.string.payment_processing))
        processPaymentWithCard(voucherEmail)
    }

    private fun isCardNumberValid(card: String) = card.length in 13..19
    private fun isCvvValid(cvv: String) = cvv.all { it.isDigit() } && (cvv.length in 3..4)
    private fun isExpiryDateValid(expiry: String) = expiry.matches(Regex("^(0[1-9]|1[0-2])/\\d{2}$"))
    private fun isEmailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun processPaymentWithCard(voucherEmail: String) {
        btnPagarTarjeta.isEnabled = false
        btnPagarTarjeta.text = getString(R.string.processing)

        Handler(Looper.getMainLooper()).postDelayed({
            showToast(getString(R.string.payment_success, voucherEmail))
            btnPagarTarjeta.isEnabled = true
            btnPagarTarjeta.text = getString(R.string.pay_amount)
            navigateToTicketActivity(voucherEmail)
        }, 2000)
    }

    private fun navigateToTicketActivity(voucherEmail: String) {
        val intent = Intent(this, TicketActivity::class.java).apply {
            putExtra("nombre", nombre)
            putExtra("apellido", apellido) // ← agregado
            putExtra("dni", dni)
            putExtra("telefono", telefono)
            putExtra("email", voucherEmail)
        }
        startActivity(intent)
        finish()
    }

    private fun formatCardNumber() {
        val text = etCardNumber.text.toString().replace(" ", "")
        if (text.isNotEmpty()) {
            etCardNumber.setText(text.chunked(4).joinToString(" "))
            etCardNumber.setSelection(etCardNumber.text.length)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun formatExpiryDate() {
        val text = etExpiryDate.text.toString().replace("/", "")
        if (text.length >= 2) {
            etExpiryDate.setText("${text.take(2)}/${text.drop(2)}")
            etExpiryDate.setSelection(etExpiryDate.text.length)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
