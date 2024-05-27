package com.koko.gesdi.servicio

import com.google.gson.GsonBuilder
import com.koko.gesdi.entidad.Categoria
import com.koko.gesdi.entidad.Meta
import com.koko.gesdi.entidad.Transaccion
import com.koko.gesdi.entidad.Usuario
import com.koko.gesdi.request.LoginRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object AppConstants {
    const val URL_BASE = "http://192.168.18.6:3000"
}

interface WebService {
    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<Usuario>

    @GET("/users/{user_id}")
    fun getUsuario(@Path("user_id") user_id: Int): Call<Usuario>

    @GET("/categories/{user_id}")
    fun getCategorias(@Path("user_id") user_id: Int): Call<CategoriesResponse>

    @GET("/categories/{category_id}")
    fun getCategoria(@Path("category_id") category_id: Int): Call<Categoria>

    @GET("/transactions/{user_id}")
    fun getTransacciones(@Path("user_id") user_id: Int): Call<TransactionsResponse>

    @GET("/transactions/{transaction_id}")
    fun getTransaccion(@Path("transaction_id") transaction_id: Int): Call<Transaccion>

    @GET("/goals/{user_id}")
    fun getMetas(@Path("user_id") user_id: Int): Call<GoalsResponse>

    @GET("/goals/{goal_id}")
    fun getmeta(@Path("goal_id") goal_id: Int): Call<Meta>
}

object RetrofitClient {
    val webService:WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }
}