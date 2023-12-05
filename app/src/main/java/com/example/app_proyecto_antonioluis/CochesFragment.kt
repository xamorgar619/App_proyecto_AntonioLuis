package com.example.app_proyecto_antonioluis

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CochesFragment : Fragment(R.layout.fragment_coches) {

    val imagenes = listOf(R.drawable.coche1, R.drawable.coche2, R.drawable.coche3, R.drawable.coche4,
        R.drawable.coche5, R.drawable.coche6, R.drawable.coche7)
    val titulos = listOf("Ferrari 812 GTS", "McLaren 720S", "Mercedes EQS Eléctrico", "Mercedes Clase S",
        "Rolls-Royce Wraith", "Bentley Continental GTC", "Aston Martin DBX")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvCoches)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter = ImagenAdapter(imagenes, titulos)
        recyclerView.adapter = adapter

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_CochesFragment_to_inicioFragment)
        }

    }

    }