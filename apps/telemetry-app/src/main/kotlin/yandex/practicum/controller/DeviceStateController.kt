package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yandex.practicum.dto.DeviceStateResponse
import yandex.practicum.service.TelemetryService


@RestController
@RequestMapping("/devices/{deviceId}/state")
@Tag(name = "Device State", description = "API для управления состоянием устройств")
class DeviceStateController(private val telemetryService: TelemetryService) {

    @GetMapping
    @Operation(summary = "Получение текущего состояния устройства")
    fun getDeviceState(@PathVariable deviceId: String): DeviceStateResponse {
        val state = telemetryService.getDeviceState(deviceId)

        val stateMap = state.state?.let { parseState(it) }

        return DeviceStateResponse(
            deviceId = state.deviceId,
            lastSeen = state.lastSeen,
            status = state.status.toString(),
            state = stateMap
        )
    }

    private fun parseState(stateJson: String): Map<String, Any> {
        // Упрощенный парсинг JSON
        return mapOf("raw" to stateJson)
    }
}