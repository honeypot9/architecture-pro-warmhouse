package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yandex.practicum.dto.AnalyticsStats
import yandex.practicum.dto.AnalyticsSummaryResponse
import yandex.practicum.service.AnalyticsService
import java.time.LocalDate

@RestController
@RequestMapping("/analytics/summary")
@Tag(name = "Data API", description = "API для аналитических данных")
class AnalyticsController(
    private val analyticsService: AnalyticsService
) {

    @GetMapping
    @Operation(summary = "Получение аналитической сводки")
    fun getAnalyticsSummary(
        @RequestParam deviceId: String,
        @RequestParam metric: String,
        @RequestParam(defaultValue = "day") period: String,
        @RequestParam(required = false) date: String?
    ): AnalyticsSummaryResponse {
        val targetDate = date?.let { LocalDate.parse(it) } ?: LocalDate.now()

        val stats = analyticsService.getAnalyticsSummary(deviceId, metric, period, targetDate)

        return AnalyticsSummaryResponse(
            deviceId = deviceId,
            metric = metric,
            period = period,
            date = targetDate.toString(),
            stats = AnalyticsStats(
                avg = stats["avg"] as Double,
                min = stats["min"] as Double,
                max = stats["max"] as Double,
                count = stats["count"] as Int
            )
        )
    }
}