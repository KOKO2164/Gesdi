package com.koko.gesdi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koko.gesdi.entidad.Categoria
import com.koko.gesdi.servicio.CategoriesResponse
import com.koko.gesdi.servicio.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesListActivity : AppCompatActivity() {
    private lateinit var rvCategories: RecyclerView
    private lateinit var btnDashboard: ConstraintLayout
    private var userId: Int = 0

    private var adaptador: AdaptadorCategorias = AdaptadorCategorias()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_list)
        asignarReferencias()
        cargarDatos()
    }

    private fun cargarDatos() {
        userId = intent.getIntExtra("user_id", 1)
        Log.d("CategoriesListActivity", "userId: $userId")
        val call = RetrofitClient.webService.getCategorias(userId)
        call.enqueue(object: Callback<CategoriesResponse> {
            override fun onResponse(call: Call<CategoriesResponse>, response: Response<CategoriesResponse>) {
                val categorias = response.body()?.categoriesList
                Log.d("CategoriesListActivity", "categorias: $categorias")
                adaptador.setListaCategorias(categorias ?: ArrayList())
            }

            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                Log.e("CategoriesListActivity", "Error al cargar las categorías", t)
                adaptador.setListaCategorias(ArrayList())
            }
        })
    }

    private fun asignarReferencias() {
        rvCategories = findViewById(R.id.rvCategories)
        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.adapter = adaptador
        btnDashboard = findViewById(R.id.btnDashboard)

        btnDashboard.setOnClickListener {
            finish()
        }
    }
}