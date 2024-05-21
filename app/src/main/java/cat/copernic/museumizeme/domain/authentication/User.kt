package cat.copernic.museumizeme.domain.authentication

data class User(
    val email: String?,
    val name: String?,
    val surname: String?,
    val role: UserRole?
) {
    constructor() : this(null, null, null, null)
}