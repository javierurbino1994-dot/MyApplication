package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Botón "Iniciar sesión" → HU0004Activity
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            println("DEBUG: Clic en botón Login") // Para debug
            val intent = Intent(this, HU0004Activity::class.java)
            startActivity(intent)
            finish() // Cierra LoginActivity para que no se pueda volver
        }

        // Texto "Regístrate" → activity_registro
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        tvRegister.setOnClickListener {
            println("DEBUG: Clic en texto Registrarse") // Para debug
            val intent = Intent(this, activity_registro::class.java)
            startActivity(intent)
            // NO uses finish() aquí para que el usuario pueda volver al login
        }
    }
}