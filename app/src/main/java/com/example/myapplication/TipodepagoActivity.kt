package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TipodepagoActivity : AppCompatActivity() {

    private lateinit var rbTarjeta: RadioButton
    private lateinit var rbTransferencia: RadioButton
    private lateinit var rbYape: RadioButton
    private lateinit var btnContinuar: Button

    // Variables para almacenar los datos del usuario
    private var nombre: String = ""
    private var apellido: String = ""
    private var dni: String = ""
    private var telefono: String = ""
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipodepago)

        // Inicializar vistas PRIMERO
        initViews()

        // Recibir datos de IdentificacionActivity DESPUÉS
        recibirDatosUsuario()

        setupListeners()
    }

    private fun recibirDatosUsuario() {
        try {
            nombre = intent.getStringExtra("nombre") ?: ""
            apellido = intent.getStringExtra("apellido") ?: ""
            dni = intent.getStringExtra("dni") ?: ""
            telefono = intent.getStringExtra("telefono") ?: ""
            email = intent.getStringExtra("email") ?: ""

            // Debug: Verificar que los datos se recibieron
            Log.d("TIPOPAGO_DEBUG", "Datos recibidos:")
            Log.d("TIPOPAGO_DEBUG", "Nombre: $nombre")
            Log.d("TIPOPAGO_DEBUG", "Apellido: $apellido")
            Log.d("TIPOPAGO_DEBUG", "DNI: $dni")
            Log.d("TIPOPAGO_DEBUG", "Teléfono: $telefono")
            Log.d("TIPOPAGO_DEBUG", "Email: $email")

            if (nombre.isEmpty() || apellido.isEmpty()) {
                Toast.makeText(this, "Error: No se recibieron datos del usuario", Toast.LENGTH_LONG).show()
                Log.e("TIPOPAGO_DEBUG", "No se recibieron datos desde IdentificacionActivity")
            }

        } catch (e: Exception) {
            Log.e("TIPOPAGO_DEBUG", "Error al recibir datos: ${e.message}")
            Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        rbTarjeta = findViewById(R.id.rbTarjeta)
        rbTransferencia = findViewById(R.id.rbTransferencia)
        rbYape = findViewById(R.id.rbYape)
        btnContinuar = findViewById(R.id.btnContinuar)
    }

    private fun setupListeners() {
        rbTarjeta.setOnClickListener { seleccionarOpcion("tarjeta") }
        rbTransferencia.setOnClickListener { seleccionarOpcion("transferencia") }
        rbYape.setOnClickListener { seleccionarOpcion("yape") }

        btnContinuar.setOnClickListener {
            // Verificar que tenemos datos antes de continuar
            if (nombre.isEmpty() || apellido.isEmpty()) {
                Toast.makeText(this, "Error: Datos del usuario no disponibles", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            when {
                rbTarjeta.isChecked -> {
                    Log.d("TIPOPAGO_DEBUG", "Navegando a TarjetaPaymentActivity")
                    val intent = Intent(this, TarjetaPaymentActivity::class.java)
                    pasarDatosAIntent(intent)
                    startActivity(intent)
                }
                rbTransferencia.isChecked -> {
                    Log.d("TIPOPAGO_DEBUG", "Navegando a TransferenciaActivity")
                    val intent = Intent(this, TransferenciaActivity::class.java)
                    pasarDatosAIntent(intent)
                    startActivity(intent)
                }
                rbYape.isChecked -> {
                    Log.d("TIPOPAGO_DEBUG", "Navegando a YapePaymentActivity")
                    val intent = Intent(this, YapePaymentActivity::class.java)
                    pasarDatosAIntent(intent)
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this, "Selecciona un método de pago", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pasarDatosAIntent(intent: Intent) {
        try {
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido", apellido)
            intent.putExtra("dni", dni)
            intent.putExtra("telefono", telefono)
            intent.putExtra("email", email)

            Log.d("TIPOPAGO_DEBUG", "Datos pasados a intent: $nombre $apellido, $dni, $telefono, $email")
        } catch (e: Exception) {
            Log.e("TIPOPAGO_DEBUG", "Error al pasar datos: ${e.message}")
        }
    }

    private fun seleccionarOpcion(metodo: String) {
        rbTarjeta.isChecked = false
        rbTransferencia.isChecked = false
        rbYape.isChecked = false

        when (metodo) {
            "tarjeta" -> rbTarjeta.isChecked = true
            "transferencia" -> rbTransferencia.isChecked = true
            "yape" -> rbYape.isChecked = true
        }

        btnContinuar.isEnabled = true
    }
}
