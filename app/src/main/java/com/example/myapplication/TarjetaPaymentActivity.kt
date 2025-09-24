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

class TarjetaPaymentActivity : AppCompatActivity() {

    private lateinit var etCardNumber: EditText
    private lateinit var etExpiryDate: EditText
    private lateinit var etCvv: EditText
    private lateinit var etCardHolder: EditText
    private lateinit var etVoucherEmail: EditText
    private lateinit var btnPagarTarjeta: Button

    // Variables para almacenar los datos del usuario
    private lateinit var nombre: String
    private lateinit var dni: String
    private lateinit var telefono: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjeta_payment)

        // SOLUCIÓN 1: Remover o corregir el WindowInsetsListener
        // Opción A: Remover completamente (más simple)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // CORREGIR ORDEN: Primero inicializar vistas, luego recibir datos
        initViews()
        recibirDatosUsuario()
        setupListeners()
    }

    private fun recibirDatosUsuario() {
        nombre = intent.getStringExtra("nombre") ?: "Nombre no disponible"
        dni = intent.getStringExtra("dni") ?: "DNI no disponible"
        telefono = intent.getStringExtra("telefono") ?: "Teléfono no disponible"
        email = intent.getStringExtra("email") ?: "Email no disponible"

        // Prellenar el email para el voucher
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
        btnPagarTarjeta.setOnClickListener {
            processCardPayment()
        }

        // Formatear número de tarjeta (agregar espacios cada 4 dígitos)
        etCardNumber.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                formatCardNumber()
            }
        }

        // Formatear fecha de expiración (agregar / después de MM)
        etExpiryDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                formatExpiryDate()
            }
        }
    }

    private fun processCardPayment() {
        val cardNumber = etCardNumber.text.toString().replace(" ", "")
        val expiryDate = etExpiryDate.text.toString()
        val cvv = etCvv.text.toString()
        val cardHolder = etCardHolder.text.toString().trim()
        val voucherEmail = etVoucherEmail.text.toString().trim()

        // Validaciones
        if (cardNumber.isEmpty()) {
            showToast("Ingresa el número de tarjeta")
            etCardNumber.requestFocus()
            return
        }

        if (!isCardNumberValid(cardNumber)) {
            showToast("El número de tarjeta no es válido")
            etCardNumber.requestFocus()
            return
        }

        if (expiryDate.isEmpty()) {
            showToast("Ingresa la fecha de expiración")
            etExpiryDate.requestFocus()
            return
        }

        if (!isExpiryDateValid(expiryDate)) {
            showToast("La fecha de expiración debe tener el formato MM/AA y no estar vencida")
            etExpiryDate.requestFocus()
            return
        }

        if (cvv.isEmpty()) {
            showToast("Ingresa el código CVV")
            etCvv.requestFocus()
            return
        }

        if (!isCvvValid(cardNumber, cvv)) {
            showToast("El CVV no es válido para este tipo de tarjeta")
            etCvv.requestFocus()
            return
        }

        if (cardHolder.isEmpty()) {
            showToast("Ingresa el nombre del titular")
            etCardHolder.requestFocus()
            return
        }

        if (voucherEmail.isEmpty()) {
            showToast("Ingresa el email para envío del voucher")
            etVoucherEmail.requestFocus()
            return
        }

        if (!isEmailValid(voucherEmail)) {
            showToast("Ingresa un email válido para el voucher")
            etVoucherEmail.requestFocus()
            return
        }

        showToast("Procesando pago con tarjeta...")

        // SOLUCIÓN 2: Usar los parámetros o simplificar la función
        processPaymentWithCard(voucherEmail)
    }

    private fun isCardNumberValid(cardNumber: String): Boolean {
        val cleanedNumber = cardNumber.replace(" ", "")
        return when {
            cleanedNumber.startsWith("4") -> cleanedNumber.length in 13..16
            cleanedNumber.startsWith("5") -> cleanedNumber.length == 16
            cleanedNumber.startsWith("34") || cleanedNumber.startsWith("37") -> cleanedNumber.length == 15
            cleanedNumber.startsWith("36") || cleanedNumber.startsWith("38") ||
                    cleanedNumber.startsWith("300") || cleanedNumber.startsWith("301") ||
                    cleanedNumber.startsWith("302") || cleanedNumber.startsWith("303") ||
                    cleanedNumber.startsWith("304") || cleanedNumber.startsWith("305") ->
                cleanedNumber.length in 14..16
            cleanedNumber.startsWith("62") -> cleanedNumber.length in 16..19
            else -> cleanedNumber.length in 13..19
        }
    }

    private fun isCvvValid(cardNumber: String, cvv: String): Boolean {
        val cleanedNumber = cardNumber.replace(" ", "")
        return if (cleanedNumber.startsWith("34") || cleanedNumber.startsWith("37")) {
            cvv.length == 4 && cvv.all { it.isDigit() }
        } else {
            cvv.length == 3 && cvv.all { it.isDigit() }
        }
    }

    private fun isExpiryDateValid(expiryDate: String): Boolean {
        val pattern = Regex("^(0[1-9]|1[0-2])/([0-9]{2})\$")
        if (!pattern.matches(expiryDate)) return false

        val parts = expiryDate.split("/")
        val month = parts[0].toInt()
        val year = parts[1].toInt() + 2000

        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1

        return year > currentYear || (year == currentYear && month >= currentMonth)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // SOLUCIÓN 2: Simplificar la función para usar solo el parámetro necesario
    private fun processPaymentWithCard(voucherEmail: String) {
        btnPagarTarjeta.isEnabled = false
        btnPagarTarjeta.text = "PROCESANDO..."

        android.os.Handler().postDelayed({
            showToast("¡Pago con tarjeta exitoso! S/ 99.75\nVoucher enviado a: $voucherEmail")
            btnPagarTarjeta.isEnabled = true
            btnPagarTarjeta.text = "PAGAR S/ 99.75"

            navigateToTicketActivity(voucherEmail)
        }, 2000)
    }

    private fun navigateToTicketActivity(voucherEmail: String) {
        val intent = Intent(this, TicketActivity::class.java).apply {
            putExtra("nombre", nombre)
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
            val formatted = StringBuilder()
            for (i in text.indices) {
                if (i > 0 && i % 4 == 0) {
                    formatted.append(" ")
                }
                formatted.append(text[i])
            }
            etCardNumber.setText(formatted.toString())
            etCardNumber.setSelection(formatted.length)
        }
    }

    private fun formatExpiryDate() {
        val text = etExpiryDate.text.toString().replace("/", "")
        if (text.length >= 2) {
            val formatted = "${text.substring(0, 2)}/${text.substring(2)}"
            etExpiryDate.setText(formatted)
            etExpiryDate.setSelection(formatted.length)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}