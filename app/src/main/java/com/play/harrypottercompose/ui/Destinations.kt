package com.play.harrypottercompose.ui

import com.play.harrypottercompose.data.entites.Character

sealed class Destinations(val route: String) {

    object HomeDestination : Destinations("Home")

    object DetailDestination : Destinations("Detail") {
        fun buildRouteArgs(character: Character): StringBuilder {
            val routArgs = StringBuilder().apply {
                append("?name=${character.name}")
                append("&image=${character.image}")
                append("&info=Date of Birth:${character.dateOfBirth}, ")
                append("Ancestry: ${character.ancestry}, ")
                append("Eye colour:${character.eyeColour} ")
            }
            return routArgs
        }
    }
}