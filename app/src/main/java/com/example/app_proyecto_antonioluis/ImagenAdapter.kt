package com.example.app_proyecto_antonioluis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_proyecto_antonioluis.databinding.ImagenBinding

class ImagenAdapter(private val imagenes: List<Int>, private val titulos: List<String>) : RecyclerView.Adapter<ImagenAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ImagenBinding.bind(view)

        fun bind(imagenResId: Int, titulo: String) {
            binding.imagenView.setImageResource(imagenResId)
            binding.tituloView.text = titulo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.imagen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagenResId = imagenes[position]
        val titulo = titulos[position]
        holder.bind(imagenResId, titulo)
    }

    override fun getItemCount(): Int {
        return imagenes.size
    }
}
