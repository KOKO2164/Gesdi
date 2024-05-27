package com.koko.gesdi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koko.gesdi.entidad.Categoria
import com.koko.gesdi.entidad.Usuario
import com.koko.gesdi.servicio.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdaptadorCategorias:RecyclerView.Adapter<AdaptadorCategorias.MiViewHolder>() {
    private var listaCategorias:List<Categoria> = ArrayList()

    fun setListaCategorias(categorias:List<Categoria>) {
        this.listaCategorias = categorias ?: ArrayList()
        notifyDataSetChanged()
    }

    class MiViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        private var filaNombreCategoria = view.findViewById<TextView>(R.id.filaNombreCategoria)
        private var filaTipoCategoria = view.findViewById<TextView>(R.id.filaTipoCategoria)
        private var filaUsuarioCategoria = view.findViewById<TextView>(R.id.filaUsuarioCategoria)

        fun setValores(categoria: Categoria) {
            filaNombreCategoria.text = categoria.category_name
            filaTipoCategoria.text = categoria.category_type

            val call = RetrofitClient.webService.getUsuario(categoria.user_id)
            call.enqueue(object: Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    val usuario = response.body()
                    filaUsuarioCategoria.text = usuario?.user_name
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    filaUsuarioCategoria.text = "Error"
                    Log.e("AdaptadorCategorias", "Error al cargar el usuario", t)
                    Log.e("AdaptadorCategorias", "Error message: ${t.message}")
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila_categoria, parent, false)
    )

    override fun onBindViewHolder(holder: AdaptadorCategorias.MiViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        holder.setValores(categoria)
    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }
}