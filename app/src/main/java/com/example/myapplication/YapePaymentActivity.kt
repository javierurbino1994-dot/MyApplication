package com.yourpackage.foodfest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yourpackage.foodfest.databinding.ActivityYapePaymentBinding

class YapePaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYapePaymentBinding
    private val paymentAmount = "S/ 20"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_yape_payment)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        // Mostrar el monto en el botón
        binding.btnPayYape.text = "YAPEAR $paymentAmount"
    }

    private fun setupListeners() {
        binding.btnPayYape.setOnClickListener {
            processYapePayment()
        }

        // Listener para el botón de regresar (si lo tienes)
        binding.btnBack?.setOnClickListener {
            finish()
        }
    }

    private fun processYapePayment() {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val approvalCode = binding.etApprovalCode.text.toString().trim()

        // Validaciones
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Ingresa tu número de celular", Toast.LENGTH_SHORT).show()
            binding.etPhoneNumber.requestFocus()
            return
        }

        if (phoneNumber.length != 9) {
            Toast.makeText(this, "El número debe tener 9 dígitos", Toast.LENGTH_SHORT).show()
            binding.etPhoneNumber.requestFocus()
            return
        }

        if (approvalCode.isEmpty()) {
            Toast.makeText(this, "Ingresa el código de aprobación", Toast.LENGTH_SHORT).show()
            binding.etApprovalCode.requestFocus()
            return
        }

        if (approvalCode.length < 6) {
            Toast.makeText(this, "El código debe tener al menos 6 dígitos", Toast.LENGTH_SHORT).show()
            binding.etApprovalCode.requestFocus()
            return
        }

        // Simular procesamiento de pago
        simulatePaymentProcess(phoneNumber, approvalCode)
    }

    private fun simulatePaymentProcess(phone: String, code: String) {
        // Aquí irían las llamadas a la API de Yape o tu backend
        // Por ahora simulamos el proceso

        binding.btnPayYape.isEnabled = false
        binding.btnPayYape.text = "Procesando..."

        // Simular delay de procesamiento
        binding.btnPayYape.postDelayed({
            // Simular respuesta exitosa
            Toast.makeText(this, "¡Pago exitoso! Gracias por tu compra", Toast.LENGTH_LONG).show()

            // Aquí podrías navegar a una pantalla de confirmación
            // o regresar al inicio con el resultado
            finish()

        }, 2000) // 2 segundos de delay
    }
}