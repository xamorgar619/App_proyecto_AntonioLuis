package com.example.app_proyecto_antonioluis

data class Articulo (
    val nombre: String,
    val precio: String,
    val descripcion: String,
    val imagenUrl: String
) {
    // Constructor vacío para que funcione la carga de datos de Firestore
    constructor() : this("", "", "", "")
}
