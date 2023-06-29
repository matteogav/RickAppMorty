package com.matteogav.rickappmorty.app.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.matteogav.rickappmorty.R
import com.matteogav.rickappmorty.app.viewmodels.EpisodesViewModel
import com.matteogav.rickappmorty.domain.model.Episode
import com.matteogav.rickappmorty.utils.theme.AppGreen
import org.koin.androidx.compose.getViewModel

@Composable
fun EpisodesScreen(navController: NavController, episodes: List<Int>) {

    val viewModel = getViewModel<EpisodesViewModel>()
    val episodesList by viewModel.episodes.collectAsState()

    viewModel.getEpisodes(episodes)

    Column(Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterVertically).padding(top = 20.dp, start = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    tint = AppGreen,
                    contentDescription = "Arrow back button icon",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                        .shadow(20.dp, shape = CircleShape)
                        .background(Color.DarkGray, shape = CircleShape)
                        .clip(CircleShape)
                )
            }
            Text(
                text = "Episodes",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 15.dp, top = 15.dp)
            )
        }
        episodesList?.let { episodes ->
            if(episodes.isEmpty()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    items(episodes) {
                        EpisodeCard(it)
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeCard(episode: Episode) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(CornerSize(16.dp)),
        backgroundColor = AppGreen
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${episode.episode}: ${episode.name}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = episode.air_date,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}