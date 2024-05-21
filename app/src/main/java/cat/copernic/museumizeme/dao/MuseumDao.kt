package cat.copernic.museumizeme.dao

import cat.copernic.museumizeme.domain.screens.Museum
import kotlinx.coroutines.flow.Flow

interface MuseumDao {

    suspend fun addMuseum(museum: Museum)

    suspend fun deleteMuseum(noteId: String)

    fun getMuseumsFlow(): Flow<List<Museum>>
}