package com.koko.gesdi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.koko.gesdi.entidad.Usuario
import com.koko.gesdi.request.LoginRequest
import com.koko.gesdi.servicio.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var inputCorreo: EditText
    private lateinit var inputContrasena: EditText
    private lateinit var btnIngresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asignarReferencias()
    }

    private fun asignarReferencias() {
        inputCorreo = findViewById(R.id.inputCorreo)
        inputContrasena = findViewById(R.id.inputContrasena)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val email = inputCorreo.text.toString()
            val password = inputContrasena.text.toString()
            val loginRequest = LoginRequest(email, password)
            val call = RetrofitClient.webService.login(loginRequest)
            call.enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                        intent.putExtra("user_id", usuario?.user_id)
                        intent.putExtra("user_name", usuario?.user_name)
                        intent.putExtra("user_email", usuario?.user_email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}