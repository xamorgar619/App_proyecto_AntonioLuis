package com.example.app_proyecto_antonioluis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

enum class ProviderType {
    BASIC
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}
