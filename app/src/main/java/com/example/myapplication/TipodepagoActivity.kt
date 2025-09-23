package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class TipodepagoActivity : AppCompatActivity() {

    private lateinit var cardTarjeta: CardView
    private lateinit var cardTransferencia: CardView
    private lateinit var cardYape: CardView
    private lateinit var rbTarjeta: RadioButton
    private lateinit var rbTransferencia: RadioButton
    private lateinit var rbYape: RadioButton
    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipodepago)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        // Inicializar las vistas principales
        cardTarjeta = findViewById(R.id.cardTarjeta)
        cardTransferencia = findViewById(R.id.cardTransferencia)
        cardYape = findViewById(R.id.cardYape)
        rbTarjeta = findViewById(R.id.rbTarjeta)
        rbTransferencia = findViewById(R.id.rbTransferencia)
        rbYape = findViewById(R.id.rbYape)
        btnContinuar = findViewById(R.id.btnContinuar)
    }

    private fun setupListeners() {
        // Listener para Tarjeta de crédito/débito
        cardTarjeta.setOnClickListener {
            seleccionarOpcion("tarjeta")
        }

        rbTarjeta.setOnClickListener {
            seleccionarOpcion("tarjeta")
        }

        // Listener para Transferencias Bancarias
        cardTransferencia.setOnClickListener {
            seleccionarOpcion("transferencia")
        }

        rbTransferencia.setOnClickListener {
            seleccionarOpcion("transferencia")
        }

        // Listener para Yape
        cardYape.setOnClickListener {
            seleccionarOpcion("yape")
        }

        rbYape.setOnClickListener {
            seleccionarOpcion("yape")
        }

        // Listener para el botón Continuar
        btnContinuar.setOnClickListener {
            when {
                rbTarjeta.isChecked -> {
                    val intent = Intent(this, TarjetaPaymentActivity::class.java)
                    startActivity(intent)
                }
                rbTransferencia.isChecked -> {
                    val intent = Intent(this, TransferenciaActivity::class.java)
                    startActivity(intent)
                }
                rbYape.isChecked -> {
                    val intent = Intent(this, YapePaymentActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun seleccionarOpcion(metodo: String) {
        // Deseleccionar todas las opciones primero
        rbTarjeta.isChecked = false
        rbTransferencia.isChecked = false
        rbYape.isChecked = false

        // Seleccionar la opción correspondiente
        when (metodo) {
            "tarjeta" -> rbTarjeta.isChecked = true
            "transferencia" -> rbTransferencia.isChecked = true
            "yape" -> rbYape.isChecked = true
        }

        // Habilitar el botón Continuar
        btnContinuar.isEnabled = true
    }
}