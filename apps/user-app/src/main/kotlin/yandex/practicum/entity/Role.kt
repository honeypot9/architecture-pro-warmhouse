package yandex.practicum.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, unique = true)
    lateinit var name: String

    var description: String? = null

    @ElementCollection
    var permissions: MutableSet<String> = mutableSetOf()

    constructor()

    constructor(name: String, description: String? = null) {
        this.name = name
        this.description = description
    }
}

enum class UserStatus {
    ACTIVE, PENDING, BLOCKED
}