package com.example.app_proyecto_antonioluis

class Articulo {
    var id: String = ""
    var nombre: String = ""
    var precio: String = ""
    var descripcion: String = ""
    var imagenUrl: String = ""


    // Constructor vacío para que funcione la carga de datos de Firestore
    constructor()
    constructor(id: String, nombre: String, precio: String, descripcion: String, imagenUrl: String) {
        this.id = id
        this.nombre = nombre
        this.precio = precio
        this.descripcion = descripcion
        this.imagenUrl = imagenUrl
    }

}


