package com.matteogav.rickappmorty.app.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.matteogav.rickappmorty.R
import com.matteogav.rickappmorty.app.activities.NavigationRoute
import com.matteogav.rickappmorty.app.activities.gzipCompress
import com.matteogav.rickappmorty.app.viewmodels.CharacterViewModel
import org.koin.androidx.compose.getViewModel
import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.utils.theme.AppGreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.util.*

@Composable
fun CharactersGridScreen(navController: NavController) {
    val characterViewModel = getViewModel<CharacterViewModel>()
    val characters = characterViewModel.allCharacters.collectAsLazyPagingItems()

    val searchText by characterViewModel.search.collectAsState()

    if (characters.itemSnapshotList.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            HomeHeader(characterViewModel, searchText)
            if (characters.loadState.refresh is LoadState.Error) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column (modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(id = R.drawable.ic_api_error),
                            "Error image",
                            Modifier.padding(horizontal = 20.dp)
                        )
                        Text(
                            text = "No characters found",
                            Modifier.padding(40.dp, 60.dp),
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                        )
                    }
                }
            }
            else {
                LazyRow(
                    contentPadding = PaddingValues(12.dp)
                ) {
                    val filters = listOf(
                        "Human", "Alien", "Humanoid", "unknown", "Poopybutthole",
                        "Animal", "Mythological Creature", "Robot", "Cronenberg", "Disease"
                    )
                    items(filters) { filter ->
                        FilterCard(filter, characterViewModel)
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp),
                    content = {
                        items(characters.itemCount) { index ->
                            characters[index]?.let { CharacterCard(it, navController) }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeHeader(viewModel: CharacterViewModel, searchText: String) {
    Box(
        Modifier
            .background(Color.Black)
            .padding(20.dp, 15.dp, 10.dp, 20.dp)
    ) {
        Column() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "RickAppMorty",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            CustomTextField(
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        null,
                        tint = Color.White,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                },
                modifier = Modifier
                    .background(
                        Color(0.07f, 0.07f, 0.07f, 1.0f),
                        RoundedCornerShape(percent = 40)
                    )
                    .padding(4.dp)
                    .height(40.dp),
                fontSize = 18.sp,
                placeholderText = "Search a character ...",
                searchText = searchText,
                onTextChanged = { newText ->
                    viewModel.searchCharacter(newText)
                }
            )
        }
    }
}

@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String,
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    searchText: String,
    onTextChanged: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .background(
                Color(0.07f, 0.07f, 0.07f, 1.0f),
                MaterialTheme.shapes.small,
            )
            .fillMaxWidth(),
        value = searchText,
        onValueChange = { newText ->
            onTextChanged(newText)
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    if (searchText.isEmpty()) {
                        Text(
                            placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = fontSize
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun FilterCard(filter: String, viewModel: CharacterViewModel) {

    val currentFilter by viewModel.currentFilter.collectAsState()

    val backgroundColor = if (currentFilter == filter) {
        AppGreen
    } else {
        Color.White
    }

    Card (
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { viewModel.updateFilter(filter) },
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
            Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                text = filter.capitalize(Locale.ROOT)
            )
        }
    }
}

@Composable
fun CharacterCard(character: Character, navController: NavController) {
    Card (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(275.dp)
            .clickable {
                val character = Json.encodeToString(character)
                val encodedJson = URLEncoder.encode(character.gzipCompress(), "UTF-8")
                navController.navigate("${NavigationRoute.CHARACTERSINFO.destination}/${encodedJson}")},
        elevation = 4.dp,
        shape = RoundedCornerShape(CornerSize(16.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize().align(Alignment.TopStart),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .scale(Scale.FIT)
                        .data(character.image)
                        .build(),
                ),
                contentDescription = "CharacterModel card image",
                contentScale = ContentScale.FillBounds,
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.35f))

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = character.name,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}