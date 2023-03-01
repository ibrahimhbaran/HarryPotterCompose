package com.play.harrypottercompose.data.services

import com.play.harrypottercompose.data.entites.Character
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {
   @GET("api/characters")
  suspend  fun getCharactersResponse() : Response<List<Character>>
}