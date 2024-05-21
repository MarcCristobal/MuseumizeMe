package cat.copernic.museumizeme.dao

import cat.copernic.museumizeme.domain.authentication.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserDaoImpl : UserDao {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun addUser(user: User) {
        firestore.collection("users").add(user).await()
    }

    override suspend fun getUserByEmail(email: String): User? {
        val userSnapshot = firestore.collection("users")
            .whereEqualTo("email", email)
            .get().await()

        return if (userSnapshot.isEmpty) {
            null
        } else {
            val document = userSnapshot.documents.first()
            document.toObject(User::class.java)
        }
    }
}