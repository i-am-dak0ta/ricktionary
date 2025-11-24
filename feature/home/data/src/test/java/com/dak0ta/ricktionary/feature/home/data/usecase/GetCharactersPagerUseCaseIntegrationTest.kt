package com.dak0ta.ricktionary.feature.home.data.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.feature.home.data.repository.CharactersRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCharactersPagerUseCaseIntegrationTest {

    private lateinit var repository: CharactersRepository
    private lateinit var useCase: GetCharactersPagerUseCaseImpl

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetCharactersPagerUseCaseImpl(repository)
    }

    @Test
    fun `GIVEN repository with characters WHEN invoke THEN returns PagingData with correct items`() = runTest {
        // Arrange
        val characters = listOf(
            CharacterSummary(1, "Rick Sanchez", CharacterStatus.ALIVE, "Human", "img1"),
            CharacterSummary(2, "Morty Smith", CharacterStatus.ALIVE, "Human", "img2")
        )
        val pagingData: PagingData<CharacterSummary> = PagingData.from(characters)
        every { repository.getCharactersPager() } returns flowOf(pagingData)

        // Act
        val flow = useCase.invoke()
        val snapshot = flow.asSnapshot()

        // Assert
        assertEquals(2, snapshot.size)
        assertEquals("Rick Sanchez", snapshot[0].name)
        assertEquals("Morty Smith", snapshot[1].name)
    }
}
