package com.matteogav.rickappmorty.di

import androidx.room.Room
import com.matteogav.rickappmorty.app.viewmodels.CharacterViewModel
import com.matteogav.rickappmorty.app.viewmodels.EpisodesViewModel
import com.matteogav.rickappmorty.data.database.RickAppMortyDatabase
import com.matteogav.rickappmorty.domain.repositories.CharacterRepository
import com.matteogav.rickappmorty.data.repositories.CharacterRepositoryImpl
import com.matteogav.rickappmorty.domain.repositories.EpisodesRepository
import com.matteogav.rickappmorty.data.repositories.EpisodesRepositoryImpl
import com.matteogav.rickappmorty.domain.usecases.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val roomModule = module {
    // Define el módulo de Room
    single {
        Room.databaseBuilder(androidContext(), RickAppMortyDatabase::class.java, "rickAppMorty_database")
            .build()
    }
    single {
        get<RickAppMortyDatabase>().episodeDao()
    }
    single {
        get<RickAppMortyDatabase>().characterDao()
    }
}

val viewModelModule = module {
    viewModel {
        CharacterViewModel(get(), get(), get(), get())
    }
    viewModel {
        EpisodesViewModel(get())
    }
}

val useCaseModule = module {
    single {
        GetCharactersUseCase(get())
    }
    single {
        GetFilteredCharactersUseCase(get())
    }
    single {
        GetSearchedCharactersUseCase(get())
    }
    single {
        GetFilteredSearchedCharactersUseCase(get())
    }
    single {
        GetEpisodesUseCase(get())
    }
}

val repositoryModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl(get())
    }

    single<EpisodesRepository> {
        EpisodesRepositoryImpl(get())
    }
}