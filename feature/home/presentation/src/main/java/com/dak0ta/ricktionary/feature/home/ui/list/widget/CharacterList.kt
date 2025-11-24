package com.dak0ta.ricktionary.feature.home.ui.list.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dak0ta.ricktionary.feature.home.presentation.list.ui.CharacterListUiState
import com.dak0ta.ricktionary.feature.home.ui.test.TestTags

@Composable
internal fun CharacterList(
    state: CharacterListUiState.Content,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyPagingItems = state.characters.collectAsLazyPagingItems()

    PullToRefreshBox(
        isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { lazyPagingItems.refresh() },
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(TestTags.CHARACTER_LIST),
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = { index -> lazyPagingItems.peek(index)?.id ?: index },
            ) { index ->
                val character = lazyPagingItems[index]
                if (character != null) {
                    CharacterCard(
                        character = character,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCharacterClick(character.id) }
                            .testTag(TestTags.characterItem(character.id)),
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
