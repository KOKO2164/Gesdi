package com.koko.gesdi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koko.gesdi.servicio.RetrofitClient
import com.koko.gesdi.servicio.TransactionsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionsListActivity : AppCompatActivity() {
    private lateinit var rvTransactions: RecyclerView
    private lateinit var btnDashboard: ConstraintLayout
    private var userId: Int = 0

    private var adaptador: AdaptadorTransacciones = AdaptadorTransacciones()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_list)
        asignarReferencias()
        cargarDatos()
    }

    private fun cargarDatos() {
        userId = intent.getIntExtra("user_id", 1)
        Log.d("TransactionsListActivity", "userId: $userId")
        val categoryId = intent.getIntExtra("category_id", 1)
        Log.d("TransactionsListActivity", "categoryId: $categoryId")
        val call = RetrofitClient.webService.getTransacciones(userId)
        call.enqueue(object: Callback<TransactionsResponse> {
            override fun onResponse(
                call: Call<TransactionsResponse>,
                response: Response<TransactionsResponse>
            ) {
                val transacciones = response.body()?.transactionsList
                Log.d("TransactionsListActivity", "transacciones: $transacciones")
                adaptador.setListaTransacciones(transacciones ?: ArrayList())
            }

            override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                Log.e("TransactionsListActivity", "Error al cargar las transacciones", t)
                adaptador.setListaTransacciones(ArrayList())
            }
        })
    }

    private fun asignarReferencias() {
        rvTransactions = findViewById(R.id.rvTransactions)
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = adaptador
        btnDashboard = findViewById(R.id.btnDashboard)

        btnDashboard.setOnClickListener {
            finish()
        }
    }
}