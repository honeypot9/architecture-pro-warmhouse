package yandex.practicum.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import yandex.practicum.repository.TelemetryRepository
import java.time.LocalDate

@Service
class AnalyticsService(
    private val telemetryRepository: TelemetryRepository,
    private val objectMapper: ObjectMapper
) {

    fun getAnalyticsSummary(
        deviceId: String,
        metric: String,
        period: String,
        date: LocalDate
    ): Map<String, Any> {
        val startTime = date.atStartOfDay()
        val endTime = date.plusDays(1).atStartOfDay()

        val telemetryData = telemetryRepository.findByDeviceIdAndTimestampBetween(deviceId, startTime, endTime)

        val values = telemetryData.flatMap { data ->
            val metrics = objectMapper.readValue(data.metrics, Map::class.java)
            metrics[metric]?.let { listOf(it.toString().toDoubleOrNull()) } ?: emptyList()
        }.filterNotNull()

        return if (values.isNotEmpty()) {
            mapOf(
                "avg" to values.average(),
                "min" to values.min(),
                "max" to values.max(),
                "count" to values.size
            )
        } else {
            mapOf(
                "avg" to 0.0,
                "min" to 0.0,
                "max" to 0.0,
                "count" to 0
            )
        }
    }
}