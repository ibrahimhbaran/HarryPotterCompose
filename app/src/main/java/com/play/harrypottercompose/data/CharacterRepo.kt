package com.play.harrypottercompose.data

import com.play.harrypottercompose.data.services.CharacterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepo @Inject constructor(private val characterService: CharacterService) {

    suspend fun getCharacterListResponse() = withContext(Dispatchers.IO) {
        try {
            val apiResult = characterService.getCharactersResponse()
            when {
                apiResult.isSuccessful -> {
                    Result.Success(apiResult.body())
                }
                else -> {
                    Result.Failure(Error(apiResult.errorBody().toString()))
                }
            }
        } catch (e: Exception) {
            Result.Failure(Error(e.message))
        }
    }

}