package com.company.newsjetpackcompose.presentation.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.newsjetpackcompose.core.ResultState
import com.company.newsjetpackcompose.domain.model.NewsSource

@Composable
fun SourceChipRow(
    sourcesState: ResultState<List<NewsSource>>,
    selectedSource: NewsSource?,
    onSourceSelected: (NewsSource) -> Unit,
    onClearSourceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(key = "all") {
            FilterChip(
                selected = selectedSource == null,
                onClick = onClearSourceClick,
                label = {
                    Text(text = "All sources")
                }
            )
        }

        when (sourcesState) {
            ResultState.Loading -> item(key = "loading") {
                AssistChip(
                    onClick = {},
                    enabled = false,
                    label = {
                        Text(text = "Loading sources")
                    }
                )
            }

            ResultState.Empty -> item(key = "empty") {
                AssistChip(
                    onClick = {},
                    enabled = false,
                    label = {
                        Text(text = "No sources")
                    }
                )
            }

            is ResultState.Error -> item(key = "error") {
                AssistChip(
                    onClick = {},
                    enabled = false,
                    label = {
                        Text(text = "Sources unavailable")
                    }
                )
            }

            is ResultState.Success -> items(
                items = sourcesState.data,
                key = { it.id }
            ) { source ->
                FilterChip(
                    selected = selectedSource?.id == source.id,
                    onClick = { onSourceSelected(source) },
                    label = {
                        Text(text = source.name)
                    }
                )
            }
        }
    }
}
