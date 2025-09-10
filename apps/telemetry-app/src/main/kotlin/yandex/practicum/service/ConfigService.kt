package yandex.practicum.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yandex.practicum.entity.MetricConfig
import yandex.practicum.entity.TelemetryConfig
import yandex.practicum.repository.TelemetryConfigRepository
import java.time.LocalDateTime

@Service
class ConfigService(
    private val configRepository: TelemetryConfigRepository
) {

    fun getConfig(deviceId: String): TelemetryConfig {
        return configRepository.findByDeviceId(deviceId)
            .orElseGet { createDefaultConfig(deviceId) }
    }

    @Transactional
    fun updateConfig(deviceId: String, pollingInterval: Int?, metrics: List<MetricConfig>?): TelemetryConfig {
        val config = configRepository.findByDeviceId(deviceId)
            .orElse(TelemetryConfig(deviceId, 60, emptyList()))

        pollingInterval?.let { config.pollingInterval = it }
        metrics?.let {
            config.metrics.clear()
            config.metrics.addAll(it)
        }
        config.updatedAt = LocalDateTime.now()

        return configRepository.save(config)
    }

    private fun createDefaultConfig(deviceId: String): TelemetryConfig {
        val defaultMetrics = listOf(
            MetricConfig("temperature", true, 2.0),
            MetricConfig("humidity", true, 5.0),
            MetricConfig("battery_level", true, 10.0)
        )
        val config = TelemetryConfig(deviceId, 60, defaultMetrics)
        return configRepository.save(config)
    }
}