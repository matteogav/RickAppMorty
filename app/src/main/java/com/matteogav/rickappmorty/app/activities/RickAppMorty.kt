package com.matteogav.rickappmorty.app.activities

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.matteogav.rickappmorty.app.composables.CharacterInfoScreen
import com.matteogav.rickappmorty.app.composables.CharactersGridScreen
import com.matteogav.rickappmorty.app.composables.EpisodesScreen
import com.matteogav.rickappmorty.di.repositoryModule
import com.matteogav.rickappmorty.di.viewModelModule
import com.matteogav.rickappmorty.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.matteogav.rickappmorty.domain.model.Character
import com.matteogav.rickappmorty.di.roomModule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class RickAppMorty : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(androidContext = this@RickAppMorty)
            modules(
                listOf(roomModule, viewModelModule, useCaseModule, repositoryModule)
            )
        }

        setContent {
            val navController = rememberNavController()
            NavHost(navController, NavigationRoute.CHARACTERSGRID.destination) {

                composable(NavigationRoute.CHARACTERSGRID.destination) { CharactersGridScreen(navController) }

                composable("${NavigationRoute.CHARACTERSINFO.destination}/{character}",
                    arguments = listOf(navArgument("character") { type = NavType.StringType} )
                ) { backStackEntry ->
                    val characterJson = backStackEntry.arguments?.getString("character") ?: ""
                    val character = Json.decodeFromString<Character>(characterJson.decodeBase64AndDecompress())

                    CharacterInfoScreen(navController, character)
                }

                composable("${NavigationRoute.EPISODES.destination}/{episodes}",
                    arguments = listOf(navArgument("episodes") { type = NavType.StringType} )
                ) { backStackEntry ->
                    val characterJson = backStackEntry.arguments?.getString("episodes") ?: ""
                    val episodes = Json.decodeFromString<List<Int>>(characterJson.decodeBase64AndDecompress())

                    EpisodesScreen(navController, episodes)
                }
            }
        }
    }
}

enum class NavigationRoute(val destination: String) {
    CHARACTERSGRID("characters_grid"),
    CHARACTERSINFO("character_info"),
    EPISODES("episodes")
}

fun String.decodeBase64AndDecompress(): String {
    val decodedBytes = Base64.decode(this, Base64.DEFAULT)
    val inputStream = ByteArrayInputStream(decodedBytes)
    val gzip = GZIPInputStream(inputStream)
    val buffer = ByteArray(4096)
    val outputStream = ByteArrayOutputStream()
    var bytesRead: Int
    while (gzip.read(buffer).also { bytesRead = it } != -1) {
        outputStream.write(buffer, 0, bytesRead)
    }
    return outputStream.toString()
}
fun String.gzipCompress(): String {
    val bos = ByteArrayOutputStream()
    val gzip = GZIPOutputStream(bos)
    gzip.write(this.toByteArray(Charsets.UTF_8))
    gzip.close()
    val compressed = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
    bos.close()
    return compressed
}
