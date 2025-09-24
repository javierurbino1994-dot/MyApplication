package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class listabebidas : AppCompatActivity() {
    // MODELO (representa cada bebida)
    data class Bebida(
        val imagen: Int,
        val nombre: String,
        val volumen: String,
        val precio: String,
        var cantidad: Int = 0
    )
    // ADAPTER (conecta los datos con el RecyclerView)
    class BebidaAdapter(private val bebidas: List<com.example.myapplication.listabebidas.Bebida>) :
        RecyclerView.Adapter<BebidaAdapter.BebidaViewHolder>() {

        class BebidaViewHolder(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebidaViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bebida, parent, false)
            return BebidaViewHolder(view)
        }

        override fun onBindViewHolder(holder: BebidaViewHolder, position: Int) {
            val bebida = bebidas[position]

            val img = holder.view.findViewById<ImageView>(R.id.imgBebida)
            val nombre = holder.view.findViewById<TextView>(R.id.txtNombre)
            val volumen = holder.view.findViewById<TextView>(R.id.txtVolumen)
            val precio = holder.view.findViewById<TextView>(R.id.txtPrecio)
            val cantidad = holder.view.findViewById<TextView>(R.id.txtCantidad)
            val btnMas = holder.view.findViewById<Button>(R.id.btnMas)
            val btnMenos = holder.view.findViewById<Button>(R.id.btnMenos)

            img.setImageResource(bebida.imagen)
            nombre.text = bebida.nombre
            volumen.text = bebida.volumen
            precio.text = bebida.precio
            cantidad.text = bebida.cantidad.toString()

            // Botón +
            btnMas.setOnClickListener {
                bebida.cantidad++
                cantidad.text = bebida.cantidad.toString()
            }

            // Botón -
            btnMenos.setOnClickListener {
                if (bebida.cantidad > 0) {
                    bebida.cantidad--
                    cantidad.text = bebida.cantidad.toString()
                }
            }
        }

        override fun getItemCount(): Int = bebidas.size
    }
    // ACTIVITY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listabebidas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerBebidas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista con los 4 productos
        val bebidas = listOf(
            Bebida(R.drawable.sanluis, "Agua San Luis", "750 ml", "S/ 5.00"),
            Bebida(R.drawable.cocacola, "Coca Cola", "500 ml", "S/ 2.50"),
            Bebida(R.drawable.sprit, "Sprit", "355 ml", "S/ 6.00"),
            Bebida(R.drawable.trescruces, "Tres Cruces", "500 ml", "S/ 3.50"),
            Bebida(R.drawable.redbull, "Red Bull", "250 ml", "S/ 7.20"),
            Bebida(R.drawable.monster, "Monster Energy", "473 ml", "S/ 8.00"),
            Bebida(R.drawable.tampico, "Tampico", "500 ml", "S/ 2.10")
        )

        val adapter = BebidaAdapter(bebidas)
        recyclerView.adapter = adapter
        // === SOLO ESTO SE AGREGA ===
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        btnContinuar.setOnClickListener {
            val intent = Intent(this, IdentificacionActivity::class.java)
            startActivity(intent)
        }

    }
}