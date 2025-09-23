package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class YapePaymentActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var etApprovalCode: EditText
    private lateinit var btnPayYape: Button
    private val paymentAmount = "S/ 20"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yape_payment)

        initViews()
        setupViews()
        setupListeners()
    }

    private fun initViews() {
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etApprovalCode = findViewById(R.id.et_approval_code)
        btnPayYape = findViewById(R.id.btn_pay_yape)
    }

    private fun setupViews() {
        // Mostrar el monto en el botón
        btnPayYape.text = "YAPEAR $paymentAmount"
    }

    private fun setupListeners() {
        btnPayYape.setOnClickListener {
            processYapePayment()
        }
    }

    private fun processYapePayment() {
        val phoneNumber = etPhoneNumber.text.toString().trim()
        val approvalCode = etApprovalCode.text.toString().trim()

        // Validaciones
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Ingresa tu número de celular", Toast.LENGTH_SHORT).show()
            etPhoneNumber.requestFocus()
            return
        }

        if (phoneNumber.length != 9) {
            Toast.makeText(this, "El número debe tener 9 dígitos", Toast.LENGTH_SHORT).show()
            etPhoneNumber.requestFocus()
            return
        }

        if (!phoneNumber.startsWith("9")) {
            Toast.makeText(this, "El número debe empezar con 9", Toast.LENGTH_SHORT).show()
            etPhoneNumber.requestFocus()
            return
        }

        if (approvalCode.isEmpty()) {
            Toast.makeText(this, "Ingresa el código de aprobación", Toast.LENGTH_SHORT).show()
            etApprovalCode.requestFocus()
            return
        }

        if (approvalCode.length < 6) {
            Toast.makeText(this, "El código debe tener al menos 6 dígitos", Toast.LENGTH_SHORT).show()
            etApprovalCode.requestFocus()
            return
        }

        // Simular procesamiento de pago
        simulatePaymentProcess(phoneNumber, approvalCode)
    }

    private fun simulatePaymentProcess(phone: String, code: String) {
        // Deshabilitar botón y mostrar estado de procesamiento
        btnPayYape.isEnabled = false
        btnPayYape.text = "Procesando..."

        // Simular delay de procesamiento (2 segundos)
        btnPayYape.postDelayed({
            // Simular respuesta exitosa
            Toast.makeText(this, "¡Pago exitoso! Gracias por tu compra", Toast.LENGTH_LONG).show()

            // Mostrar resumen del pago
            showPaymentSummary(phone, paymentAmount)

            // Volver a habilitar el botón por si quiere hacer otro pago
            btnPayYape.isEnabled = true
            btnPayYape.text = "YAPEAR $paymentAmount"

            // Limpiar campos
            etPhoneNumber.text.clear()
            etApprovalCode.text.clear()

            // Opcional: cerrar la actividad después de 3 segundos
            btnPayYape.postDelayed({
                finish()
            }, 3000)

        }, 2000) // 2 segundos de delay
    }

    private fun showPaymentSummary(phone: String, amount: String) {
        val maskedPhone = "9****" + phone.takeLast(2)
        Toast.makeText(
            this,
            "Pago realizado desde $maskedPhone por $amount",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}