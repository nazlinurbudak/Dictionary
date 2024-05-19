package com.nazlinurbudak.dictionary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlinurbudak.dictionary.common.NetworkResult
import com.nazlinurbudak.dictionary.data.model.WordResponse
import com.nazlinurbudak.dictionary.data.repository.WordRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var wordRepository: WordRepository

) : ViewModel() {
    private val _searchWord = MutableStateFlow<WordUiState>(WordUiState.Loading(isLoading = false))
    val searchWord: StateFlow<WordUiState>
        get() = _searchWord


    fun searchWord(word: String) = viewModelScope.launch(Dispatchers.IO) {
        if (word.isNotBlank()) {
            val data = wordRepository.getWord(word)
            _searchWord.value = WordUiState.Loading(true)
            when (data) {
                is NetworkResult.Error -> {
                    _searchWord.value = WordUiState.Loading(false)
                    _searchWord.value = WordUiState.Error(data.exception)
                }

                is NetworkResult.Success -> {
                    _searchWord.value =  WordUiState.Loading(false)
                    _searchWord.value = data.data?.let { WordUiState.Success(data = it) }!!
                }
            }

        }

    }


}

sealed class WordUiState {
    data class Loading(val isLoading: Boolean = false) : WordUiState()
    data class Success(val data: WordResponse) : WordUiState()
    data class Error(val error: String) : WordUiState()
}