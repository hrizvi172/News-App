package com.hrizvi.multimodulenewsapp

import FavoriteScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrizvi.multimodulenewsapp.navigation.Screen
import com.hrizvi.multimodulenewsapp.presentation.article_detail_screen.ArticleDetailScreen
import com.hrizvi.multimodulenewsapp.presentation.news_screen.NewsListScreen
import com.hrizvi.multimodulenewsapp.ui.theme.MultiModuleNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiModuleNewsAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    NewsAppNavigation()
                }
            }
        }
    }
}

@Composable
fun NewsAppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController= navController,
        startDestination = Screen.NewsListScreen.route
    ){
        composable(route = Screen.NewsListScreen.route){
            NewsListScreen(navController = navController)
        }

        composable(route = Screen.FavoriteScreen.route){
            FavoriteScreen(navController = navController)
        }
        composable(
            route = Screen.ArticleDetailScreen.route,
            arguments = listOf(
                navArgument("articleUrl"){
                    type = NavType.StringType
                    nullable = false
                }
            )
        ){ backStackEntry ->
            val articleUrl = backStackEntry.arguments?.getString("articleUrl")
            if (articleUrl != null){
                ArticleDetailScreen(articleUrl = articleUrl)
            }
        }
    }
}
