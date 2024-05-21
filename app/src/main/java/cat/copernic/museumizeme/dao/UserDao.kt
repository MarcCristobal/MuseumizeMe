package cat.copernic.museumizeme.dao

import cat.copernic.museumizeme.domain.authentication.User

interface UserDao {
    suspend fun addUser(user: User)

    suspend fun getUserByEmail(email: String): User?
}