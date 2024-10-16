package edu.ucne.prioridadlh.data.Repository

import edu.ucne.prioridadlh.data.Remote.PrioridadRemoteDataSource
import edu.ucne.prioridadlh.data.Remote.dto.PrioridadesDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PrioridadRepositoryTest {

    @Test
    fun getAllApi() = runBlocking {
        // Given

        val prioridades = listOf(
            PrioridadesDto(1, "Alta", 12),
            PrioridadesDto(2, "Media", 25),
        )
//        val remoteDataSource = mockk<PrioridadRemoteDataSource>()
//        val repository = PrioridadRepository(remoteDataSource)
//
//        coEvery { remoteDataSource.getPrioridades() } returns prioridades
//        // When
//        val result = repository.GetAllApi()
//        // Then
//
//        assertEquals(prioridades, result)
//    }
    }
}