package com.company.newsjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.company.newsjetpackcompose.presentation.news.NewsRoute
import com.company.newsjetpackcompose.ui.theme.NewsJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsJetpackComposeTheme {
                NewsRoute()
            }
        }
    }
}
