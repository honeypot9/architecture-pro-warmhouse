package yandex.practicum.dto

import java.time.LocalDateTime

data class TelemetryDataRequest(
    val deviceId: String,
    val timestamp: LocalDateTime,
    val metrics: Map<String, Any>
)

data class TelemetryAcceptanceResponse(
    val message: String = "Data accepted",
    val acceptedAt: LocalDateTime = LocalDateTime.now(),
    val recordId: String? = null
)

data class DeviceStateResponse(
    val deviceId: String,
    val lastSeen: LocalDateTime,
    val status: String,
    val state: Map<String, Any>?
)

data class TelemetryHistoryResponse(
    val deviceId: String,
    val metric: String? = null,
    val aggregation: String = "raw",
    val data: List<TelemetryDataPoint>
)

data class TelemetryDataPoint(
    val timestamp: LocalDateTime,
    val value: Double
)

data class AnalyticsSummaryResponse(
    val deviceId: String,
    val metric: String,
    val period: String,
    val date: String,
    val stats: AnalyticsStats
)

data class AnalyticsStats(
    val avg: Double,
    val min: Double,
    val max: Double,
    val count: Int
)

data class TelemetryConfigResponse(
    val deviceId: String,
    val pollingInterval: Int,
    val metrics: List<MetricConfigDto>
)

data class MetricConfigDto(
    val name: String,
    val enabled: Boolean,
    val threshold: Double?
)

data class TelemetryConfigUpdateRequest(
    val pollingInterval: Int? = null,
    val metrics: List<MetricConfigDto>? = null
)