package com.example.app_proyecto_antonioluis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_proyecto_antonioluis.databinding.ArticuloBinding
import com.squareup.picasso.Picasso

class ArticuloAdapter(private val articulos : List<Articulo>) : RecyclerView.Adapter<ArticuloAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ArticuloBinding.bind(view)

        fun bind(articulo: Articulo) {
            Picasso.get().load(articulo.imagenUrl).into(binding.imagenView)
            binding.nombreView.text = articulo.nombre
            binding.descripcionView.text = articulo.descripcion
            binding.precioView.text = articulo.precio

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.articulo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articulo = articulos[position]
        holder.bind(articulo)
    }

    override fun getItemCount(): Int {
        return articulos.size
    }
}
