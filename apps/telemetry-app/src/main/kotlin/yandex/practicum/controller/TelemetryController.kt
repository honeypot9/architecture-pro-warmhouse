package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.*
import yandex.practicum.service.TelemetryService

@RestController
@RequestMapping("/telemetry")
@Tag(name = "Telemetry Ingestion", description = "API для приема телеметрических данных")
class TelemetryController(private val telemetryService: TelemetryService) {

    @PostMapping
    @Operation(summary = "Отправка телеметрических данных")
    fun sendTelemetry(@Valid @RequestBody request: TelemetryDataRequest): TelemetryAcceptanceResponse {
        val telemetryData = telemetryService.processTelemetryData(
            request.deviceId,
            request.timestamp,
            request.metrics
        )

        return TelemetryAcceptanceResponse(recordId = telemetryData.id.toString())
    }
}