package com.example.app_proyecto_antonioluis

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class PerfilFragment : Fragment(R.layout.fragment_perfil){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailTextView : TextView = view.findViewById(R.id.email)
        val providerTextView : TextView = view.findViewById(R.id.provider)

        val usuario = FirebaseAuth.getInstance().currentUser

        if (usuario != null) {
            val email = usuario.email
            val provider = usuario.providerData[1].providerId

            if (provider != "google.com") {
                emailTextView.text = email
                providerTextView.text = "Email y contraseña"
            } else {
                emailTextView.text = email
                providerTextView.text = provider
            }


        }

        val btnLogout: Button = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            logout()
        }

    }

    private fun logout() {

        val preferences = requireActivity().getSharedPreferences(getString(R.string.preferences_file), MODE_PRIVATE).edit()
        preferences.clear()
        preferences.apply()

        FirebaseAuth.getInstance().signOut()

        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        // Finalizar la actividad actual
        requireActivity().finish()
    }

}