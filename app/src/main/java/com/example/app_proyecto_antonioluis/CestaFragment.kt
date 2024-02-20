package com.example.app_proyecto_antonioluis

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class CestaFragment : Fragment(R.layout.fragment_cesta) {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvArticulosCesta)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        //boton de comprar y de vaciar cesta
        val btnComprar: Button = view.findViewById(R.id.btnComprar)
        val btnVaciarCesta: Button = view.findViewById(R.id.btnVaciarCesta)


        firestore.collection("Usuarios").document(userId).collection("cesta")
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                if (querySnapshot != null) {
                    val articulosCesta = mutableListOf<Articulo>()

                    for (document: QueryDocumentSnapshot in querySnapshot) {
                        val articulo = document.toObject(Articulo::class.java)
                        articulosCesta.add(articulo)
                    }

                    if (articulosCesta.isNotEmpty()) {
                        val adapter = ArticuloAdapter(articulosCesta)
                        recyclerView.adapter = adapter
                    } else {
                        // Si la lista está vacía, muestra el texto de lista vacía
                        view.findViewById<TextView>(R.id.lista_vacia).visibility = View.VISIBLE
                        // Si la lista está vacía, se ocultan la lista y los botones
                        recyclerView.visibility = View.GONE
                        btnComprar.visibility = View.GONE
                        btnVaciarCesta.visibility = View.GONE
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error al cargar los artículos: $exception")
            }

        //que vacie la cesta compleetamente
        btnVaciarCesta.setOnClickListener {
            firestore.collection("Usuarios").document(userId).collection("cesta")
                .get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                    if (querySnapshot != null) {
                        for (document: QueryDocumentSnapshot in querySnapshot) {
                            val articulo = document.toObject(Articulo::class.java)
                            firestore.collection("Usuarios").document(userId).collection("cesta").document(articulo.id).delete()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    println("Error al cargar los artículos: $exception")
                }
            Toast.makeText(
                context,
                "Cesta vaciada",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_CestaFragment_to_InicioFragment)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_ArticulosFragment_to_inicioFragment)
        }
    }
}
