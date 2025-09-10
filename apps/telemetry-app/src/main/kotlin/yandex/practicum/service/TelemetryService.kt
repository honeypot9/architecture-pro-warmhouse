package yandex.practicum.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yandex.practicum.entity.DeviceState
import yandex.practicum.entity.DeviceStatus
import yandex.practicum.entity.TelemetryData
import yandex.practicum.repository.DeviceStateRepository
import yandex.practicum.repository.TelemetryRepository
import java.time.LocalDateTime
import java.util.*

@Service
class TelemetryService(
    private val telemetryRepository: TelemetryRepository,
    private val deviceStateRepository: DeviceStateRepository,
    private val objectMapper: ObjectMapper
) {

    @Transactional
    fun processTelemetryData(deviceId: String, timestamp: LocalDateTime, metrics: Map<String, Any>): TelemetryData {
        // Сохраняем телеметрию
        val metricsJson = objectMapper.writeValueAsString(metrics)
        val telemetryData = TelemetryData(deviceId, timestamp, metricsJson)
        val savedData = telemetryRepository.save(telemetryData)

        // Обновляем состояние устройства
        updateDeviceState(deviceId, timestamp, metrics)

        return savedData
    }

    private fun updateDeviceState(deviceId: String, timestamp: LocalDateTime, metrics: Map<String, Any>) {
        val stateJson = objectMapper.writeValueAsString(metrics)
        val deviceState = deviceStateRepository.findByDeviceId(deviceId)
            .orElse(DeviceState(deviceId, timestamp, DeviceStatus.ONLINE, stateJson))

        deviceState.lastSeen = timestamp
        deviceState.status = DeviceStatus.ONLINE
        deviceState.state = stateJson

        deviceStateRepository.save(deviceState)
    }

    fun getDeviceState(deviceId: String): DeviceState {
        return deviceStateRepository.findByDeviceId(deviceId)
            .orElseThrow { IllegalArgumentException("Device state not found") }
    }

    fun getHistoricalData(
        deviceId: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<TelemetryData> {
        return telemetryRepository.findByDeviceIdAndTimestampBetween(deviceId, startTime, endTime)
    }

    fun getLatestTelemetry(deviceId: String): Optional<TelemetryData> {
        return telemetryRepository.findFirstByDeviceIdOrderByTimestampDesc(deviceId)
    }
}