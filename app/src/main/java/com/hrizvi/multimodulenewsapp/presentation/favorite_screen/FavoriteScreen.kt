// In FavoriteScreen.kt

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hrizvi.multimodulenewsapp.navigation.Screen
import com.hrizvi.multimodulenewsapp.presentation.favorite_screen.FavoriteViewModel
import com.hrizvi.multimodulenewsapp.presentation.news_screen.components.NewsArticleCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState().value

    Box(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ){
        AnimatedContent(
            targetState = state.isLoading,
            transitionSpec = {
                (fadeIn(animationSpec = tween(300, 300)) + slideInVertically(initialOffsetY = { it })) with
                        (fadeOut(animationSpec = tween(300, 0)) + slideOutVertically(targetOffsetY = { -it }))
            },
            modifier = Modifier.fillMaxSize()
        ) { isLoading ->
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.articles.isEmpty()) {
                AnimatedEmptyFavoriteState(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column (
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(
                        text = "Favorite Articles",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn {
                        items(
                            items = state.articles,
                            key = { article -> article.url }
                        ) { article ->
                            NewsArticleCard(
                                article = article,
                                onArticleClick = {
                                    navController.navigate(Screen.ArticleDetailScreen.createRoute(article.url))
                                },
                                onFavoriteClicked = { viewModel.onFavoriteClicked(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedEmptyFavoriteState(
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500)) + scaleIn(
            initialScale = 0.8f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Favorite Articles",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "No favorite articles found",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(56.dp)
            )
        }
    }
}