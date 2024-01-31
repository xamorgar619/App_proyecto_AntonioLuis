package com.example.app_proyecto_antonioluis

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioFragment : Fragment(R.layout.fragment_inicio) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnArticulos: Button = view.findViewById(R.id.btnArticulos)

        btnArticulos.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_ArticulosFragment)
        }

    }



}