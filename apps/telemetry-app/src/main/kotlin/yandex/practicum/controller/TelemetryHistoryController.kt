package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yandex.practicum.dto.TelemetryDataPoint
import yandex.practicum.dto.TelemetryHistoryResponse
import yandex.practicum.service.TelemetryService
import java.time.LocalDateTime

@RestController
@RequestMapping("/telemetry/history")
@Tag(name = "Data Storage", description = "API для работы с историческими данными")
class TelemetryHistoryController(private val telemetryService: TelemetryService) {

    @GetMapping
    @Operation(summary = "Получение исторических данных телеметрии")
    fun getTelemetryHistory(
        @RequestParam deviceId: String,
        @RequestParam(required = false) metric: String?,
        @RequestParam(required = false) startTime: LocalDateTime?,
        @RequestParam(required = false) endTime: LocalDateTime?,
        @RequestParam(defaultValue = "raw") aggregation: String,
        @RequestParam(defaultValue = "1000") limit: Int
    ): TelemetryHistoryResponse {
        val end = endTime ?: LocalDateTime.now()
        val start = startTime ?: end.minusDays(1)

        val data = telemetryService.getHistoricalData(deviceId, start, end)
            .take(limit)
            .flatMap { telemetryData ->
                val metrics = parseMetrics(telemetryData.metrics)
                metric?.let { specificMetric ->
                    metrics[metric]?.let { value ->
                        listOf(TelemetryDataPoint(telemetryData.timestamp, value.toString().toDouble()))
                    } ?: emptyList()
                } ?: metrics.entries.map { (metricName, value) ->
                    TelemetryDataPoint(telemetryData.timestamp, value.toString().toDouble())
                }
            }

        return TelemetryHistoryResponse(
            deviceId = deviceId,
            metric = metric,
            aggregation = aggregation,
            data = data
        )
    }

    private fun parseMetrics(metricsJson: String): Map<String, Any> {
        // Упрощенный парсинг JSON
        return mapOf("raw" to metricsJson)
    }
}