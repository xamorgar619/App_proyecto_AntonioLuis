package com.example.app_proyecto_antonioluis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app_proyecto_antonioluis.databinding.ArticuloBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ArticuloAdapter(private val articulos: List<Articulo>) : RecyclerView.Adapter<ArticuloAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ArticuloBinding.bind(view)

        fun bind(articulo: Articulo) {
            Picasso.get().load(articulo.imagenUrl).into(binding.imagenView)
            binding.nombreView.text = articulo.nombre
            binding.descripcionView.text = articulo.descripcion
            binding.precioView.text = articulo.precio

            // Añadir evento de clic para añadir a favoritos
            binding.btnFavoritos.setOnClickListener {
                añadirAFavoritos(articulo)
            }
        }

        private fun añadirAFavoritos(articulo: Articulo) {
            val idUsuario = FirebaseAuth.getInstance().currentUser?.uid.toString() // Usamos el ID del usuario actual para el id del documento
            val db = FirebaseFirestore.getInstance()
            val favoritosRef = db.collection("Usuarios").document(idUsuario).collection("favoritos")
            val docRef = favoritosRef.document(articulo.id)

            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        // El artículo ya está en favoritos
                        Toast.makeText(itemView.context, "Este artículo ya está en favoritos", Toast.LENGTH_SHORT).show()
                    } else {
                        // El artículo no está en favoritos, así que lo añadimos
                        val favorito = hashMapOf(
                            "id" to articulo.id,
                            "nombre" to articulo.nombre,
                            "descripcion" to articulo.descripcion,
                            "precio" to articulo.precio,
                            "imagenUrl" to articulo.imagenUrl
                        )

                        docRef.set(favorito).addOnSuccessListener {
                            Toast.makeText(itemView.context, "Artículo añadido a favoritos", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e ->
                            Toast.makeText(itemView.context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
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
