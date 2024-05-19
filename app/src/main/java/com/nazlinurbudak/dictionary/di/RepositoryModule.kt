package com.nazlinurbudak.dictionary.di

import com.nazlinurbudak.dictionary.data.repository.WordRepository
import com.nazlinurbudak.dictionary.data.repository.WordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWordRepository(wordRepositoryImpl: WordRepositoryImpl): WordRepository
}