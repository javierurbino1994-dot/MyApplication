package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.example.myapplication.DAO.UserDAO

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        // Texto "Regístrate" → activity_registro
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        tvRegister.setOnClickListener {
            println("DEBUG: Clic en texto Registrarse") // Para debug
            val intent = Intent(this, ActivityRegistro::class.java)
            startActivity(intent)
            // NO uses finish() aquí para que el usuario pueda volver al login
        }
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val userDAO = UserDAO(this)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()

            if (userDAO.validar(email, pass)) {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                // Ir a la pantalla principal
                val intent = Intent(this, HU0004Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }


    }
}