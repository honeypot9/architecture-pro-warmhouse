package yandex.practicum.repository

import yandex.practicum.entity.Scenario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ScenarioRepository : JpaRepository<Scenario, UUID> {
    fun findByUserId(userId: UUID): List<Scenario>
    
    @Query("SELECT s FROM Scenario s WHERE s.userId = :userId AND (:enabled IS NULL OR s.enabled = :enabled)")
    fun findByUserIdAndEnabled(
        @Param("userId") userId: UUID,
        @Param("enabled") enabled: Boolean?
    ): List<Scenario>
    
    fun findByIdAndUserId(id: UUID, userId: UUID): Optional<Scenario>
}