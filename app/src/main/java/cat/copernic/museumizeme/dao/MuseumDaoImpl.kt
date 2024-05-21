package cat.copernic.museumizeme.dao

import android.content.Context
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.domain.screens.Museum
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MuseumDaoImpl(context: Context) : MuseumDao {
    private val firestore = FirebaseFirestore.getInstance()

    private val auth = AuthService(context)

    override suspend fun addMuseum(museum: Museum) {
        firestore.collection("museums").add(museum).await()
    }

    override suspend fun deleteMuseum(museumId: String) {
        val museumRef = firestore.collection("museums").document(museumId)
        museumRef.delete().await()
    }

    override fun getMuseumsFlow(): Flow<List<Museum>> = callbackFlow {
        val notesRef = firestore.collection("museums").orderBy("name")

        val subscription = notesRef.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val museums = mutableListOf<Museum>()
                for (document in querySnapshot.documents) {
                    val museum = document.toObject(Museum::class.java)
                    museum?.id = document.id
                    museum?.let { museums.add(it) }
                }
                trySend(museums).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }

}