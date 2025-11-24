package com.dak0ta.ricktionary.feature.home.data.usecase

import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.domain.CharacterGender
import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.dak0ta.ricktionary.feature.home.data.repository.CharactersRepository
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharacterByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetCharacterByIdUseCaseIntegrationTest {

    private lateinit var repository: CharactersRepository
    private lateinit var useCase: GetCharacterByIdUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetCharacterByIdUseCaseImpl(repository)
    }

    @Test
    fun `GIVEN valid character WHEN invoke THEN returns CharacterDetail`() = runBlocking {
        // Arrange
        val expectedCharacter = CharacterDetail(
            id = 1,
            name = "Rick Sanchez",
            status = CharacterStatus.ALIVE,
            species = "Human",
            type = "",
            gender = CharacterGender.MALE,
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            image = "",
            episodes = emptyList(),
        )
        coEvery { repository.getCharacterByID(1) } returns expectedCharacter

        // Act
        val result = useCase.invoke(1)

        // Assert
        assertEquals(expectedCharacter, result)
    }

    @Test
    fun `GIVEN repository failure WHEN invoke THEN returns Unknown CharacterDetail`() = runBlocking {
        // Arrange
        val failureCharacter = CharacterDetail(
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
        coEvery { repository.getCharacterByID(999) } returns failureCharacter

        // Act
        val result = useCase.invoke(999)

        // Assert
        assertEquals(-1, result.id)
        assertEquals("Unknown", result.name)
        assertTrue(result.episodes.isEmpty())
    }
}
