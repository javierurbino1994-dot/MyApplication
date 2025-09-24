package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R.*

class activity_registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_registro)

        // Navegación de vuelta al login cuando se hace clic en "Iniciar sesión"
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener {
            finish() // Vuelve a LoginActivity
        }

        // Acción del botón Registrarse - va a la pantalla principal
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            // Aquí va la lógica de registro (validaciones, etc.)
            // Después del registro exitoso, ir a la pantalla principal
            val intent = Intent(this, HU0004Activity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad de registro para que no se pueda volver atrás
        }
    }
}