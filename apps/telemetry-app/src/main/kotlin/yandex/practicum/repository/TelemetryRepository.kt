package yandex.practicum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import yandex.practicum.entity.DeviceState
import yandex.practicum.entity.TelemetryConfig
import yandex.practicum.entity.TelemetryData
import java.time.LocalDateTime
import java.util.*

interface TelemetryRepository : JpaRepository<TelemetryData, UUID> {

    @Query("SELECT t FROM TelemetryData t WHERE t.deviceId = :deviceId AND t.timestamp BETWEEN :startTime AND :endTime ORDER BY t.timestamp")
    fun findByDeviceIdAndTimestampBetween(
        @Param("deviceId") deviceId: String,
        @Param("startTime") startTime: LocalDateTime,
        @Param("endTime") endTime: LocalDateTime
    ): List<TelemetryData>

    fun findFirstByDeviceIdOrderByTimestampDesc(deviceId: String): Optional<TelemetryData>

    @Query("SELECT COUNT(t) FROM TelemetryData t WHERE t.deviceId = :deviceId AND t.timestamp >= :since")
    fun countByDeviceIdSince(@Param("deviceId") deviceId: String, @Param("since") since: LocalDateTime): Long
}

interface DeviceStateRepository : JpaRepository<DeviceState, UUID> {
    fun findByDeviceId(deviceId: String): Optional<DeviceState>
    fun deleteByDeviceId(deviceId: String)
}

interface TelemetryConfigRepository : JpaRepository<TelemetryConfig, UUID> {
    fun findByDeviceId(deviceId: String): Optional<TelemetryConfig>
}