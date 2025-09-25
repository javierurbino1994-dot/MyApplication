package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class HU0004Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hu0004)

        // Navegación para categorías del primer cuadro
        findViewById<View>(R.id.category_sanguches).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("categoria", "Sanguches")
            startActivity(intent)
        }

        findViewById<View>(R.id.category_bebidas).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("categoria", "Bebidas")
            startActivity(intent)
        }

        findViewById<View>(R.id.category_snacks).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("categoria", "Snacks")
            startActivity(intent)
        }

        findViewById<View>(R.id.category_merch).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("categoria", "Merch")
            startActivity(intent)
        }

        findViewById<View>(R.id.category_promociones).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("categoria", "Promociones")
            startActivity(intent)
        }

        // Navegación para productos recomendados del segundo cuadro
        findViewById<View>(R.id.recommendation_hamburguesa).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("producto", "Hamburguesa")
            startActivity(intent)
        }

        findViewById<View>(R.id.recommendation_pilsen).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("producto", "Pilsen")
            startActivity(intent)
        }

        findViewById<View>(R.id.recommendation_cocacola).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("producto", "Coca-Cola")
            startActivity(intent)
        }

        findViewById<View>(R.id.recommendation_redbull).setOnClickListener {
            val intent = Intent(this, ListaBebidasActivity::class.java)
            intent.putExtra("producto", "Redbull")
            startActivity(intent)
        }

        // Navegación para la barra inferior
        findViewById<View>(R.id.nav_inicio).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.nav_tickets).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.nav_perfil).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
