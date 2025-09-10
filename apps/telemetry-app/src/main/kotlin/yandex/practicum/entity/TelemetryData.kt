package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "telemetry_data", indexes = [
    Index(name = "idx_device_timestamp", columnList = "deviceId, timestamp"),
    Index(name = "idx_timestamp", columnList = "timestamp")
])
class TelemetryData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false)
    lateinit var deviceId: String

    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now()

    @Column(columnDefinition = "TEXT")
    lateinit var metrics: String

    @Column(nullable = false)
    var receivedAt: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(deviceId: String, timestamp: LocalDateTime, metrics: String) {
        this.deviceId = deviceId
        this.timestamp = timestamp
        this.metrics = metrics
    }
}