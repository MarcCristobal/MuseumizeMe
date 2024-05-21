package cat.copernic.museumizeme.domain.screens

import java.util.UUID

data class Museum(
    var id: String? = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
