package com.matteogav.rickappmorty.app.viewmodels

import androidx.lifecycle.ViewModel
import com.matteogav.rickappmorty.domain.model.Episode
import com.matteogav.rickappmorty.domain.usecases.GetEpisodesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EpisodesViewModel constructor(private val getEpisodesUseCase: GetEpisodesUseCase): ViewModel() {

    private val _episodes = MutableStateFlow<List<Episode>?>(null)
    val episodes: StateFlow<List<Episode>?>
        get() = _episodes

    suspend fun getEpisodes(episodesRequest: List<Int>) {
        val result = getEpisodesUseCase.getEpisodes(episodesRequest)
        if(result.isNotEmpty()) _episodes.value = result
    }
}