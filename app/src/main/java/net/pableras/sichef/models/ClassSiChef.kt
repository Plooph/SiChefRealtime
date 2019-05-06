package net.pableras.sichef.models

import java.io.Serializable

/*
El usuario tiene que tener un id distinto del de firebase
Ese id estará relacionado con la receta(idUser) para saber a quien corresponde

OTRA OPCION si no es posible añadir un id al usuario (parece lo más probable)
Utilizar una clase extra en la que añada el usuario a la receta
 */

data class User(
    var email: String = "",
    var pasw: String = "",
    var uid: String = ""): Serializable

data class Receta(
    var id: String = "",
    var uid: String = "",
    var title: String = "",
    var ingredientes: String = "",
    var comensales: String = "",
    var preparacion: String = ""):Serializable

data class RecetaAux(
    var id: String = "",
    var user: User = User(),
    var title: String = "",
    var ingredientes: String = "",
    var comensales: String = "",
    var preparacion: String = ""):Serializable