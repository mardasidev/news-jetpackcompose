package com.company.newsjetpackcompose.presentation.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.newsjetpackcompose.core.ResultState
import com.company.newsjetpackcompose.domain.model.Article
import com.company.newsjetpackcompose.domain.model.NewsCategory
import com.company.newsjetpackcompose.domain.model.NewsSource
import com.company.newsjetpackcompose.presentation.news.components.ArticleCard
import com.company.newsjetpackcompose.presentation.news.components.CategoryChipRow
import com.company.newsjetpackcompose.presentation.news.components.SourceChipRow
import com.company.newsjetpackcompose.ui.theme.NewsJetpackComposeTheme

@Composable
fun NewsScreen(
    uiState: NewsUiState,
    onCategorySelected: (NewsCategory) -> Unit,
    onSourceSelected: (NewsSource) -> Unit,
    onClearSourceClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "News",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            CategoryChipRow(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = onCategorySelected
            )

            Spacer(modifier = Modifier.height(4.dp))

            SourceChipRow(
                sourcesState = uiState.sourcesState,
                selectedSource = uiState.selectedSource,
                onSourceSelected = onSourceSelected,
                onClearSourceClick = onClearSourceClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            ArticlesContent(
                articlesState = uiState.articlesState,
                onRetryClick = onRetryClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ArticlesContent(
    articlesState: ResultState<List<Article>>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (articlesState) {
        is ResultState.Loading -> CenterContent(modifier = modifier) {
            CircularProgressIndicator()
        }

        is ResultState.Empty -> CenterContent(modifier = modifier) {
            Text(
                text = "No news found",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is ResultState.Error -> CenterContent(modifier = modifier) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = articlesState.message,
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(onClick = onRetryClick) {
                    Text(text = "Retry")
                }
            }
        }

        is ResultState.Success -> LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = articlesState.data,
                key = { it.url }
            ) { article ->
                ArticleCard(article = article)
            }
        }
    }
}

@Composable
private fun CenterContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsScreenPreview() {
    NewsJetpackComposeTheme {
        NewsScreen(
            uiState = NewsUiState(
                articlesState = ResultState.Success(
                    listOf(
                        Article(
                            title = "Compose news app reaches first milestone",
                            description = "A clean MVVM structure is now ready for NewsAPI data.",
                            url = "https://example.com",
                            imageUrl = null,
                            publishedAt = "2026-08-06T10:00:00Z",
                            author = "Codex",
                            sourceName = "Example News"
                        )
                    )
                ),
                sourcesState = ResultState.Success(
                    listOf(
                        NewsSource(
                            id = "bbc-news",
                            name = "BBC News",
                            description = null,
                            category = "general",
                            country = "gb",
                            language = "en"
                        )
                    )
                )
            ),
            onCategorySelected = {},
            onSourceSelected = {},
            onClearSourceClick = {},
            onRetryClick = {}
        )
    }
}
