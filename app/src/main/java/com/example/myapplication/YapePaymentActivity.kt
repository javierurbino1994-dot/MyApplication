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

class YapePaymentActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var etApprovalCode: EditText
    private lateinit var btnPayYape: Button

    // Variables para almacenar los datos del usuario
    private lateinit var nombre: String
    private lateinit var dni: String
    private lateinit var telefono: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_yape_payment)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir datos de TipodepagoActivity
        recibirDatosUsuario()

        initViews()
        setupListeners()
    }

    private fun recibirDatosUsuario() {
        nombre = intent.getStringExtra("nombre") ?: "Nombre no disponible"
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

        showToast("Procesando pago Yape...")
        processPaymentWithYape(phoneNumber, approvalCode)
    }

    private fun processPaymentWithYape(phoneNumber: String, approvalCode: String) {
        btnPayYape.isEnabled = false
        btnPayYape.text = "PROCESANDO..."

        android.os.Handler().postDelayed({
            showToast("¡Pago exitoso! S/ 87.20")
            btnPayYape.isEnabled = true
            btnPayYape.text = "YAPEAR S/ 87.20"

            // Navegar a TicketActivity con los datos del usuario
            navigateToTicketActivity()
        }, 2000)
    }

    private fun navigateToTicketActivity() {
        val intent = Intent(this, TicketActivity::class.java).apply {
            putExtra("nombre", nombre)
            putExtra("dni", dni)
            putExtra("telefono", telefono)
            putExtra("email", email)
        }

        startActivity(intent)
        // finish() // Opcional: cierra esta actividad
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}