package com.example.app_proyecto_antonioluis

data class Usuario (

    var id: String = "",
    var email: String = "",

) {
    constructor() : this("", "")
}
