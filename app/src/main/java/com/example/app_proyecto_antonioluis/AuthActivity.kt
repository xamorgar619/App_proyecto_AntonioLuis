package com.example.app_proyecto_antonioluis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

enum class ProviderType {
    BASIC,
    GOOGLE
}
class AuthActivity : AppCompatActivity() {


    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        setup()
        session()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun session() {
        val preferences = getSharedPreferences(getString(R.string.preferences_file), MODE_PRIVATE)
        val email = preferences.getString("email", null)
        val provider = preferences.getString("provider", null)

        if (email != null && provider != null) {
            showHome(email, ProviderType.valueOf(provider), EditText(this))
        }
    }

    private fun setup() {
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnAcceder: Button = findViewById(R.id.btnAcceder)
        val btnGoogle: Button = findViewById(R.id.btnGoogle)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)

        btnRegistrar.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(
                                it.result?.user?.email ?: "",
                                ProviderType.BASIC,
                                editTextEmail
                            )
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        btnAcceder.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(
                                it.result?.user?.email ?: "",
                                ProviderType.BASIC,
                                editTextEmail
                            )
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        btnGoogle.setOnClickListener {

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun showHome(email: String, provider: ProviderType, editTextEmail: EditText) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en el registro")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(account.email ?: "", ProviderType.GOOGLE, EditText(this))
                            } else {
                                showAlert()
                            }
                        }
                }
            } catch (e: ApiException) {
                showAlert()
            }
        }
    }
}
