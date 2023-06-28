package com.matteogav.rickappmorty.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.matteogav.rickappmorty.domain.usecases.GetCharactersUseCase
import com.matteogav.rickappmorty.domain.usecases.GetFilteredCharactersUseCase
import com.matteogav.rickappmorty.domain.usecases.GetFilteredSearchedCharactersUseCase
import com.matteogav.rickappmorty.domain.usecases.GetSearchedCharactersUseCase
import com.matteogav.rickappmorty.domain.model.Character

enum class EnumSource {
    ALL_CHARACTERS,
    FILTER,
    SEARCH,
    FILTER_SEARCH
}

class CharacterSource (
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFilteredCharactersUseCase: GetFilteredCharactersUseCase,
    private val getSearchedCharactersUseCase: GetSearchedCharactersUseCase,
    private val getFilteredSearchedCharactersUseCase: GetFilteredSearchedCharactersUseCase,
    private val source: EnumSource,
    private val filter: String = "",
    private val search: String = ""
    ) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val key = params.key ?: 1
            val result = when(source) {
                EnumSource.ALL_CHARACTERS -> getCharactersUseCase.getCharacters(key)
                EnumSource.FILTER -> getFilteredCharactersUseCase.getCharactersFiltered(key, filter)
                EnumSource.SEARCH -> getSearchedCharactersUseCase.getCharactersSearch(key, search)
                EnumSource.FILTER_SEARCH -> getFilteredSearchedCharactersUseCase.getCharactersFilteredSearch(key, filter, search)
            }

            val prevKey = if (key == 1) null else key - 1
            val nextKey = if (result.isEmpty()) null else key + 1

            LoadResult.Page(
                data = result,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}