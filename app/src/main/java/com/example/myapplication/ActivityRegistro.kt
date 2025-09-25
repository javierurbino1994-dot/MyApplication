package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


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
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, HU0004Activity::class.java)
            startActivity(intent)
            finish() // Cierra ActivityRegistro para que no se pueda volver atrás
        }
    }
}

