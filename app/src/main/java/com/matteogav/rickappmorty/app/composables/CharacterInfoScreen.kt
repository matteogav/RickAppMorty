package com.matteogav.rickappmorty.app.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matteogav.rickappmorty.domain.model.Character
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.matteogav.rickappmorty.R
import com.matteogav.rickappmorty.app.activities.NavigationRoute
import com.matteogav.rickappmorty.app.activities.gzipCompress
import com.matteogav.rickappmorty.utils.theme.AppGreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.util.*

@Composable
fun CharacterInfoScreen(navController: NavController, character: Character) {

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .scale(Scale.FIT)
                            .data(character.image)
                            .build(),
                    ),
                    contentDescription = "CharacterModel image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart).padding(top = 20.dp, start = 20.dp)
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
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomStart)
        ) {
            CharacterInfoCard(character, navController)
        }
    }
}

@Composable
fun CharacterInfoCard(character: Character, navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = AbsoluteRoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
            elevation = 10.dp,
        ) {
            Column(
                Modifier
                    //.background(AppGreen.copy(0.5f))
                    .verticalScroll(rememberScrollState())
                    .padding(25.dp),
            ) {
                Text(
                    text = character.name.uppercase(),
                    style = TextStyle(
                        Color.Black,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W900
                    )
                )
                Box(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    var colorIndicator = Color.Gray
                    val charStatus = character.status.capitalize(Locale.ROOT)
                    when(charStatus) {
                        "Alive" -> colorIndicator = Color.Green
                        "Dead" -> colorIndicator = Color.Red
                        "Unknown" -> colorIndicator = Color.Gray
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle),
                        contentDescription = "Status indicator icon",
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.CenterStart)
                            .background(shape = CircleShape, color = colorIndicator)
                            .shadow(
                                elevation = 10.dp,
                                ambientColor = colorIndicator,
                                spotColor = colorIndicator
                            )
                            .clip(CircleShape)
                            .padding(14.dp),
                    )
                    Text(
                        text = "$charStatus - ${character.species.capitalize(Locale.ROOT)}",
                        modifier = Modifier.align(Alignment.CenterStart)
                            .padding(start = 30.dp),
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Text("ORIGIN",
                    modifier = Modifier.padding(top = 10.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(
                    character.originName.capitalize(Locale.ROOT),
                    modifier = Modifier.padding(top = 10.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium)
                Text("LAST LOCATION",
                    modifier = Modifier.padding(top = 20.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(
                    character.locationName.capitalize(Locale.ROOT),
                    modifier = Modifier.padding(top = 10.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium)
                Card (
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth()
                        .clickable {
                            val episodeNumbers = character.episode.map { url ->
                                val episodeNumber = url.substringAfterLast("/")
                                episodeNumber.toInt()
                            }

                            val episodes = Json.encodeToString(episodeNumbers)
                            val encodedJson = URLEncoder.encode(episodes.gzipCompress(), "UTF-8")
                            navController.navigate("${NavigationRoute.EPISODES.destination}/${encodedJson}")
                        },
                    elevation = 4.dp,
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AppGreen.copy(0.5f))
                            .padding(vertical = 15.dp, horizontal = 15.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Appearance in episodes >",
                            color = Color.Black,
                            fontSize = 20.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}