package com.play.harrypottercompose.ui.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.play.harrypottercompose.data.CharacterRepo
import com.play.harrypottercompose.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val characterRepo: CharacterRepo) : ViewModel() {

    private val _result = MutableLiveData<Result<Any>>()
    val result : LiveData<Result<Any>>
        get() = _result

    init {
        loadCharacters()
    }

    private fun loadCharacters() = viewModelScope.launch {
        val response = characterRepo.getCharacterListResponse()
        _result.postValue(response)
    }
}
