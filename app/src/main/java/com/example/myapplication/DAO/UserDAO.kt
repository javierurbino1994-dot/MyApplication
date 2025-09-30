package com.example.myapplication.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.myapplication.Util.Tools
import com.example.myapplication.Model.usuario


class UserDAO(mycontext: Context) {

    private val dbHelper = DBHelper(mycontext)

    // Insertar usuario
    fun insertar(email: String, password: String): Long {
        Log.i(Tools.LOGTAG, "Ingresó al método insertar()")
        val indice: Long
        val values = ContentValues().apply {
            put("email", email)
            put("password", password)
        }
        val db = dbHelper.writableDatabase
        try {
            indice = db.insert(Tools.USUARIO, null, values)
            return indice
        } catch (e: Exception) {
            throw DAOException("UserDAO: Error al insertar: " + e.message)
        } finally {
            db.close()
        }
    }

    // Validar login
    fun validar(email: String, password: String): Boolean {
        Log.i(Tools.LOGTAG, "Ingresó al método validar()")
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${Tools.USUARIO} WHERE email=? AND password=?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    //  Verificar si un email ya existe
    fun existeEmail(email: String): Boolean {
        Log.i(Tools.LOGTAG, "Ingresó al método existeEmail()")
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${Tools.USUARIO} WHERE email=?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
}