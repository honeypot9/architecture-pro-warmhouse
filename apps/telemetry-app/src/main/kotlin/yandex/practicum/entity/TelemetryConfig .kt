package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
@Entity
@Table(name = "telemetry_configs")
class TelemetryConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, unique = true)
    lateinit var deviceId: String

    @Column(nullable = false)
    var pollingInterval: Int = 60

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "config_id")
    var metrics: MutableList<MetricConfig> = mutableListOf()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(deviceId: String, pollingInterval: Int, metrics: List<MetricConfig>) {
        this.deviceId = deviceId
        this.pollingInterval = pollingInterval
        this.metrics.addAll(metrics)
    }
}

enum class AggregationPeriod {
    RAW, MINUTE, HOUR, DAY
}

enum class AnalyticsPeriod {
    DAY, WEEK, MONTH
}