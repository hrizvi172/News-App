package com.hrizvi.multimodulenewsapp.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object NewsListScreen : Screen("news_list_screen")

    object FavoriteScreen : Screen("favorite_screen")
    object ArticleDetailScreen : Screen("article_detail_screen/{articleUrl}") {
        fun createRoute(articleUrl: String): String {
            val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
            return "article_detail_screen/$encodedUrl"
        }
    }
}