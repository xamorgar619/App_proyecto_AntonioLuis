package com.example.app_proyecto_antonioluis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setup()
    }

    private fun setup() {

        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnAcceder : Button = findViewById(R.id.btnAcceder)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)

        btnRegistrar.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            //it.result?.user?.email ?: "" --> si no se ha introducido un email, no da error (siempre existirá un email)
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC, editTextEmail)
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        btnAcceder.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC, editTextEmail)
                        } else {
                            showAlert()
                        }
                    }
            }
        }

    }

    private fun showHome(email : String, provider : ProviderType, editTextEmail: EditText) {
        //Función que muestra la pantalla principal de la aplicación
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", editTextEmail.text.toString())
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showAlert() {
        //Función que muestra un mensaje de error en caso de que el usuario no se haya podido registrar
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en el registro")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}