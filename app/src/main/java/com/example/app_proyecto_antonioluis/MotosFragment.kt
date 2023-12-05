package com.example.app_proyecto_antonioluis

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MotosFragment : Fragment(R.layout.fragment_motos) {

    val imagenes = listOf(R.drawable.moto1, R.drawable.moto2, R.drawable.moto3, R.drawable.moto4, R.drawable.moto5,
        R.drawable.moto6, R.drawable.moto7)
    val titulos = listOf("Ducati Desert X", "Honda XL 750 Transalp", "Royal Enfield Hunter", "Vespa GTS HPE 125/300",
        "Yamaha MT-10 SP", "Honda CB750 Hornet", "Harley-Davidson Nightster 2022")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvMotos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter = ImagenAdapter(imagenes, titulos)
        recyclerView.adapter = adapter

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_CochesFragment_to_inicioFragment)
        }

    }
}