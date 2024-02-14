package com.example.app_proyecto_antonioluis

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class ArticulosFragment : Fragment(R.layout.fragment_articulos) {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvArticulos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        firestore.collection("Articulos")
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                if (querySnapshot != null) {
                    val articulos = mutableListOf<Articulo>()

                    for (document: QueryDocumentSnapshot in querySnapshot) {
                        val articulo = document.toObject(Articulo::class.java)
                        articulos.add(articulo)
                    }

                    val adapter = ArticuloAdapter(articulos)
                    recyclerView.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                println("Error al cargar los artículos: $exception")
            }



        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_ArticulosFragment_to_inicioFragment)
        }
    }
}
