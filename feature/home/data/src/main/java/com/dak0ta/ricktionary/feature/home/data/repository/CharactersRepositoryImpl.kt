package com.dak0ta.ricktionary.feature.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.domain.CharacterGender
import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult
import com.dak0ta.ricktionary.core.network.domain.repository.CharactersRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CharactersRepositoryImpl @Inject constructor(
    private val remote: CharactersRemoteRepository,
    private val dispatchers: CoroutineDispatchers,
) : CharactersRepository {

    override fun getCharactersPager(): Flow<PagingData<CharacterSummary>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2, enablePlaceholders = false),
            pagingSourceFactory = { remote.getCharactersPage() },
        ).flow
    }

    override suspend fun getCharacterByID(id: Int): CharacterDetail = withContext(dispatchers.io) {
        return@withContext when (val result = remote.getCharacterById(id)) {
            is ApiResult.Success -> result.data
            is ApiResult.Failure -> CharacterDetail(
                id = -1,
                name = "Unknown",
                status = CharacterStatus.UNKNOWN,
                species = "",
                type = "",
                gender = CharacterGender.UNKNOWN,
                origin = "",
                location = "",
                image = "",
                episodes = emptyList(),
            )
        }
    }
}
