package com.nazlinurbudak.dictionary.data.repository

import com.nazlinurbudak.dictionary.common.NetworkResult
import com.nazlinurbudak.dictionary.data.api.DictionaryApi
import com.nazlinurbudak.dictionary.data.model.WordResponse
import java.lang.Exception
import javax.inject.Inject

interface WordRepository {
    suspend fun getWord(word: String): NetworkResult<WordResponse>
}

class WordRepositoryImpl @Inject constructor(
    private val api: DictionaryApi
) : WordRepository {
    override suspend fun getWord(word: String): NetworkResult<WordResponse> {

        val response = api.getWord(word)
        return if (response.isSuccessful) {
            val data = response.body()
            NetworkResult.Success(data)
        }else {
            NetworkResult.Error(response.errorBody().toString())
        }


    }


}