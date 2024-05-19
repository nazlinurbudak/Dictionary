package com.nazlinurbudak.dictionary.data.api

import com.nazlinurbudak.dictionary.data.model.WordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word")word: String): Response<WordResponse>
}