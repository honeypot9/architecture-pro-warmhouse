package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, unique = true)
    lateinit var email: String

    @Column(nullable = false)
    lateinit var password: String

    @Column(nullable = false)
    lateinit var firstName: String

    @Column(nullable = false)
    lateinit var lastName: String

    var phone: String? = null

    @Column(nullable = false)
    var emailVerified: Boolean = false

    var phoneVerified: Boolean = false

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE

    @ElementCollection
    var roles: MutableSet<String> = mutableSetOf()

    @Column(columnDefinition = "TEXT")
    var preferences: String? = null

    var lastLogin: LocalDateTime? = null

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String? = null
    ) {
        this.email = email
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.phone = phone
        this.roles.add("USER")
    }
}