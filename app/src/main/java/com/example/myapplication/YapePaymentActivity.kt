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

class YapePaymentActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var etApprovalCode: EditText
    private lateinit var btnPayYape: Button

    // Datos del usuario
    private lateinit var nombre: String
    private lateinit var apellido: String
    private lateinit var dni: String
    private lateinit var telefono: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_yape_payment)

        // ✅ Usamos el root de la actividad
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recibirDatosUsuario()
        initViews()
        setupListeners()
    }

    private fun recibirDatosUsuario() {
        nombre = intent.getStringExtra("nombre") ?: "Nombre no disponible"
        apellido = intent.getStringExtra("apellido") ?: "Apellido no disponible"
        dni = intent.getStringExtra("dni") ?: "DNI no disponible"
        telefono = intent.getStringExtra("telefono") ?: "Teléfono no disponible"
        email = intent.getStringExtra("email") ?: "Email no disponible"
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

        if (phoneNumber.isEmpty()) {
            showToast("Ingresa tu número de celular Yape")
            return
        }

        if (phoneNumber.length != 9 || !phoneNumber.startsWith("9")) {
            showToast("Ingresa un número válido (9########)")
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

        showToast("Procesando pago Yape...")
        processPaymentWithYape(phoneNumber, approvalCode)
    }

    private fun processPaymentWithYape(phoneNumber: String, approvalCode: String) {
        btnPayYape.isEnabled = false
        btnPayYape.text = "PROCESANDO..."

        // ✅ Handler moderno con Looper principal
        Handler(Looper.getMainLooper()).postDelayed({
            showToast("¡Pago exitoso! S/ 87.20")
            btnPayYape.isEnabled = true
            btnPayYape.text = "YAPEAR S/ 87.20"
            navigateToTicketActivity()
        }, 2000)
    }

    private fun navigateToTicketActivity() {
        val intent = Intent(this, TicketActivity::class.java).apply {
            putExtra("nombre", nombre)
            putExtra("apellido", apellido)
            putExtra("dni", dni)
            putExtra("telefono", telefono)
            putExtra("email", email)
        }
        startActivity(intent)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
