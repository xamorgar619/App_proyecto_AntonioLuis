package com.example.app_proyecto_antonioluis

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class InicioFragment : Fragment(R.layout.fragment_inicio) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCoches: Button = view.findViewById(R.id.btnCoches)
        val btnMotos: Button = view.findViewById(R.id.btnMotos)

        btnCoches.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_CochesFragment)
        }

        btnMotos.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_MotosFragment)
        }
    }
}
