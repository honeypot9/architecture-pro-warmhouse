package yandex.practicum.repository

import org.springframework.data.jpa.repository.JpaRepository
import yandex.practicum.entity.Role
import yandex.practicum.entity.User
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Optional<Role>
}