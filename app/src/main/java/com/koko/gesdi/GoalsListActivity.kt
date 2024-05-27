package com.koko.gesdi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koko.gesdi.servicio.GoalsResponse
import com.koko.gesdi.servicio.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalsListActivity : AppCompatActivity() {
    private lateinit var rvGoals: RecyclerView
    private lateinit var btnDashboard: ConstraintLayout
    private var userId: Int = 0

    private var adaptador: AdaptadorMetas = AdaptadorMetas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals_list)
        asignarReferencias()
        cargarDatos()
    }

    private fun cargarDatos() {
        userId = intent.getIntExtra("user_id", 1)
        Log.d("GoalsListActivity", "userId: $userId")
        val call = RetrofitClient.webService.getMetas(userId)
        call.enqueue(object: Callback<GoalsResponse> {
            override fun onResponse(call: Call<GoalsResponse>, response: Response<GoalsResponse>) {
                val metas = response.body()?.goalsList
                Log.d("GoalsListActivity", "metas: $metas")
                adaptador.setListaMetas(metas ?: ArrayList())
            }

            override fun onFailure(call: Call<GoalsResponse>, t: Throwable) {
                Log.e("GoalsListActivity", "Error al cargar las metas", t)
                adaptador.setListaMetas(ArrayList())
            }
        })
    }

    private fun asignarReferencias() {
        rvGoals = findViewById(R.id.rvGoals)
        rvGoals.layoutManager = LinearLayoutManager(this)
        rvGoals.adapter = adaptador
        btnDashboard = findViewById(R.id.btnDashboard)

        btnDashboard.setOnClickListener {
            finish()
        }
    }
}