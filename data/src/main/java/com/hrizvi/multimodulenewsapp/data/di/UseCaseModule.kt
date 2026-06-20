package com.hrizvi.multimodulenewsapp.di

import com.hrizvi.multimodulenewsapp.domain.usecase.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideSearchNewsUseCase(): SearchNewsUseCase {
        return SearchNewsUseCase()
    }

}