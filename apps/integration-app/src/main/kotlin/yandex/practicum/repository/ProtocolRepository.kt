package yandex.practicum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import yandex.practicum.entity.Protocol
import yandex.practicum.entity.ProtocolCategory
import java.util.*

interface ProtocolRepository : JpaRepository<Protocol, UUID> {
    fun findByNameAndVersion(name: String, version: String): Optional<Protocol>

    @Query("SELECT p FROM Protocol p WHERE (:category IS NULL OR p.category = :category) AND (:enabled IS NULL OR p.enabled = :enabled)")
    fun findByCategoryAndEnabled(
        @Param("category") category: ProtocolCategory?,
        @Param("enabled") enabled: Boolean?
    ): List<Protocol>
}