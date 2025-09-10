package yandex.practicum.repository

import org.springframework.data.jpa.repository.JpaRepository
import yandex.practicum.entity.User
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
}