package com.hrizvi.multimodulenewsapp.presentation.article_detail_screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun ArticleDetailScreen(
    articleUrl: String
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        AndroidView(
            factory = {context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    loadUrl(articleUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}