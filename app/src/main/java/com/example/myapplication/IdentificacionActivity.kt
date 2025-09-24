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

class IdentificacionActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDocumento: EditText
    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_identificacion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etEmail = findViewById(R.id.etEmail)
        etTelefono = findViewById(R.id.etTelefono)
        etDocumento = findViewById(R.id.etDocumento)
        btnContinuar = findViewById(R.id.btnContinuar)
    }

    private fun setupListeners() {
        btnContinuar.setOnClickListener {
            validarYContinuar()
        }
    }

    private fun validarYContinuar() {
        val nombre = etNombre.text.toString().trim()
        val apellido = etApellido.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val documento = etDocumento.text.toString().trim()

        when {
            nombre.isEmpty() -> {
                mostrarError("Ingresa tu nombre")
                etNombre.requestFocus()
                return
            }
            apellido.isEmpty() -> {
                mostrarError("Ingresa tu apellido")
                etApellido.requestFocus()
                return
            }
            email.isEmpty() -> {
                mostrarError("Ingresa tu email")
                etEmail.requestFocus()
                return
            }
            !isEmailValid(email) -> {
                mostrarError("Ingresa un email válido")
                etEmail.requestFocus()
                return
            }
            telefono.isEmpty() -> {
                mostrarError("Ingresa tu teléfono")
                etTelefono.requestFocus()
                return
            }
            !isPhoneValid(telefono) -> {
                mostrarError("El teléfono debe tener al menos 9 dígitos")
                etTelefono.requestFocus()
                return
            }
            documento.isEmpty() -> {
                mostrarError("Ingresa tu documento")
                etDocumento.requestFocus()
                return
            }
            !isDocumentValid(documento) -> {
                mostrarError("El documento debe tener al menos 8 dígitos")
                etDocumento.requestFocus()
                return
            }
        }

        // Pasar datos a TipodepagoActivity
        continuarConDatos(nombre, apellido, email, telefono, documento)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(phone: String): Boolean {
        return phone.length >= 9 && phone.all { it.isDigit() }
    }

    private fun isDocumentValid(document: String): Boolean {
        return document.length >= 8 && document.all { it.isDigit() }
    }

    private fun continuarConDatos(nombre: String, apellido: String, email: String, telefono: String, documento: String) {
        Toast.makeText(this, "Datos validados correctamente", Toast.LENGTH_SHORT).show()

        // Navegación a TipodepagoActivity con datos
        val intent = Intent(this, TipodepagoActivity::class.java).apply {
            putExtra("nombre", "$nombre $apellido")
            putExtra("dni", documento)
            putExtra("telefono", telefono)
            putExtra("email", email)
        }
        startActivity(intent)
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}