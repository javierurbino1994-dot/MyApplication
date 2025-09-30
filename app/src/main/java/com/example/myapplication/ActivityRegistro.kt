package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.example.myapplication.DAO.UserDAO


class ActivityRegistro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registro)

        // Navegación de vuelta al login
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener {
            finish() // Cierra esta actividad y regresa al login
        }

        // Botón de registro
        val btnRegistro = findViewById<Button>(R.id.btnRegister)
        btnRegistro.setOnClickListener {
            val intent = Intent(this, HU0004Activity::class.java)
            startActivity(intent)
            finish() // Cierra ActivityRegistro para que no se pueda volver atrás
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etRepeatPassword = findViewById<EditText>(R.id.etRepeatPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val userDAO = UserDAO(this)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()
            val repeatPass = etRepeatPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (pass != repeatPass) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else if (userDAO.existeEmail(email)) {
                Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
            } else {
                val inserted = userDAO.insertar(email, pass)
                if (inserted > 0) {
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}

