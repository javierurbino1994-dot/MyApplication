

package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class YapePaymentActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var etApprovalCode: EditText
    private lateinit var btnPayYape: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_yape_payment)

        // Configurar insets para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        initViews()

        // Configurar listeners
        setupListeners()
    }

    private fun initViews() {
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etApprovalCode = findViewById(R.id.et_approval_code)
        btnPayYape = findViewById(R.id.btn_pay_yape)
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
            showToast("Ingresa tu número de celular Yape")
            return
        }

        if (phoneNumber.length != 9 || !phoneNumber.startsWith("9")) {
            showToast("Ingresa un número de celular válido (9########)")
            return
        }

        if (approvalCode.isEmpty()) {
            showToast("Ingresa el código de aprobación")
            return
        }

        if (approvalCode.length != 6) {
            showToast("El código de aprobación debe tener 6 dígitos")
            return
        }

        // Simular procesamiento de pago
        showToast("Procesando pago Yape...")

        // Aquí iría la lógica real de integración con Yape
        processPaymentWithYape(phoneNumber, approvalCode)
    }

    private fun processPaymentWithYape(phoneNumber: String, approvalCode: String) {
        // Simular llamada a API de Yape
        // En una implementación real, aquí harías la integración con el servicio de Yape

        // Simular éxito después de 2 segundos
        btnPayYape.isEnabled = false
        btnPayYape.text = "PROCESSED..."

        android.os.Handler().postDelayed({
            showToast("¡Pago exitoso! S/ 87.20")
            btnPayYape.isEnabled = true
            btnPayYape.text = "YAPEAR S/ 87.20"

            // Regresar a la actividad anterior
            finish()
        }, 2000)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}