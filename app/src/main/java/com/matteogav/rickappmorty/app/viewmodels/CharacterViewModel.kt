package com.matteogav.rickappmorty.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.data.sources.CharacterSource
import com.matteogav.rickappmorty.data.sources.EnumSource
import com.matteogav.rickappmorty.domain.usecases.GetCharactersUseCase
import com.matteogav.rickappmorty.domain.usecases.GetFilteredCharactersUseCase
import com.matteogav.rickappmorty.domain.usecases.GetFilteredSearchedCharactersUseCase
import com.matteogav.rickappmorty.domain.usecases.GetSearchedCharactersUseCase
import kotlinx.coroutines.flow.*

class CharacterViewModel constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFilteredCharactersUseCase: GetFilteredCharactersUseCase,
    private val getSearchedCharactersUseCase: GetSearchedCharactersUseCase,
    private val getFilteredSearchedCharactersUseCase: GetFilteredSearchedCharactersUseCase,
    ) : ViewModel() {

    private val _currentFilter = MutableStateFlow("")
    val currentFilter: StateFlow<String> = _currentFilter

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search

    val allCharacters: Flow<PagingData<Character>> = _search.flatMapLatest { search ->
        _currentFilter.flatMapLatest { filter ->
            combine(_currentFilter, _search) { filter, search ->
                Pair(filter, search)
            }.flatMapLatest { (filter, search) ->
                val characterSource = CharacterSource(
                    getCharactersUseCase,
                    getFilteredCharactersUseCase,
                    getSearchedCharactersUseCase,
                    getFilteredSearchedCharactersUseCase,
                    when {
                        filter.isNotEmpty() && search.isNotEmpty() -> EnumSource.FILTER_SEARCH
                        filter.isNotEmpty() -> EnumSource.FILTER
                        search.isNotEmpty() -> EnumSource.SEARCH
                        else -> EnumSource.ALL_CHARACTERS
                    },
                    filter,
                    search
                )

                Pager(PagingConfig(1)) {
                    characterSource
                }.flow.cachedIn(viewModelScope)
            }
        }
    }

    fun updateFilter(filter: String) {
        if (_currentFilter.value == filter) {
            _currentFilter.value = ""
        }
        else {
            _currentFilter.value = filter
        }
    }

    fun searchCharacter(text: String) {
        _search.value = text
    }
}
