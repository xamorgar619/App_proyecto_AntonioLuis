package com.example.app_proyecto_antonioluis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recogida de datos del usuario con la sesión iniciada
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        // Guardado de datos del usuario con la sesión iniciada con Google, preferences_file esta en strings.xml
        val preferences = getSharedPreferences(getString(R.string.preferences_file), MODE_PRIVATE).edit()
        preferences.putString("email", email)
        preferences.putString("provider", provider)
        preferences.apply()

        //MENU
        val menu = findViewById<BottomNavigationView>(R.id.menu)

        menu.selectedItemId = R.id.btnInicio // Está seleccionado por defecto el botón de inicio

        menu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnAtras -> {
                    onBackPressed()
                    true
                }

                R.id.btnInicio -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.inicioFragment)
                    true
                }

                R.id.btnMiCuenta -> {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.PerfilFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setup(email : String, provider : String) {
        title = "Inicio"
        val bundle = Bundle()
        bundle.putString("email", email)
        bundle.putString("provider", provider)
    }
}
