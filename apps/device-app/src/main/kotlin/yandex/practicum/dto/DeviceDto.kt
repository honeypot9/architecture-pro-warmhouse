package yandex.practicum.dto

import java.time.LocalDateTime
import java.util.*

data class DeviceSummary(
    val deviceId: UUID,
    val name: String,
    val model: String,
    val status: String,
    val lastSeen: LocalDateTime?
)

data class DeviceListResponse(
    val devices: List<DeviceSummary>,
    val totalCount: Int
)

data class DeviceDetailsResponse(
    val deviceId: UUID,
    val name: String,
    val model: String,
    val serialNumber: String,
    val status: String,
    val firmwareVersion: String?,
    val lastSeen: LocalDateTime?,
    val capabilities: Set<String>,
    val configuration: Map<String, Any>?
)

data class DeviceConfigUpdateRequest(
    val configuration: Map<String, Any>
)

data class DeviceConfigUpdateResponse(
    val deviceId: UUID,
    val status: String,
    val updatedAt: LocalDateTime
)