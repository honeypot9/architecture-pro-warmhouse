package yandex.practicum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*

// Protocol DTOs
data class ProtocolCreateRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val version: String,

    @field:NotNull
    val category: String,

    val description: String? = null,
    val maxDevices: Int? = null,
    val specificationUrl: String? = null,
    val securityFeatures: List<String> = emptyList(),
    val enabled: Boolean = true
)

data class ProtocolResponse(
    val id: UUID,
    val name: String,
    val version: String,
    val category: String,
    val enabled: Boolean,
    val description: String?,
    val maxDevices: Int?,
    val specificationUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class ProtocolSummary(
    val id: UUID,
    val name: String,
    val version: String,
    val category: String,
    val enabled: Boolean,
    val description: String?,
    val maxDevices: Int?
)

data class ProtocolListResponse(
    val protocols: List<ProtocolSummary>,
    val totalCount: Int
)

data class ProtocolDetailResponse(
    val id: UUID,
    val name: String,
    val version: String,
    val category: String,
    val enabled: Boolean,
    val description: String?,
    val maxDevices: Int?,
    val specificationUrl: String?,
    val securityFeatures: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

// Device Model DTOs
data class DeviceModelCreateRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val manufacturer: String,

    @field:NotBlank
    val protocol: String,

    @field:NotNull
    val category: String,

    val description: String? = null,
    val capabilities: List<String> = emptyList(),
    val supportedCommands: List<String> = emptyList(),
    val configurationTemplate: Map<String, Any>? = null,
    val images: List<DeviceImageDto> = emptyList(),
    val documentationUrl: String? = null
)

data class DeviceImageDto(
    @field:NotBlank
    val url: String,

    @field:NotBlank
    val type: String,

    val description: String? = null
)

data class DeviceModelResponse(
    val id: UUID,
    val name: String,
    val manufacturer: String,
    val protocol: String,
    val category: String,
    val description: String?,
    val capabilities: List<String>,
    val supportedCommands: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class DeviceModelSummary(
    val id: UUID,
    val name: String,
    val manufacturer: String,
    val protocol: String,
    val category: String,
    val description: String?,
    val capabilities: List<String>,
    val supportedCommands: List<String>
)

data class DeviceModelListResponse(
    val deviceModels: List<DeviceModelSummary>,
    val totalCount: Int
)

data class DeviceModelDetailResponse(
    val id: UUID,
    val name: String,
    val manufacturer: String,
    val protocol: String,
    val category: String,
    val description: String?,
    val capabilities: List<String>,
    val supportedCommands: List<String>,
    val configurationTemplate: Map<String, Any>?,
    val images: List<DeviceImageDto>,
    val documentationUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

// Integration Test DTOs
data class IntegrationTestRequest(
    @field:NotBlank
    val protocol: String,

    @field:NotBlank
    val deviceModel: String,

    @field:NotBlank
    val testType: String,

    val parameters: Map<String, Any>? = null
)

data class IntegrationTestResponse(
    val testId: String,
    val status: String,
    val protocol: String,
    val deviceModel: String,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime? = null,
    val results: Map<String, Any>? = null,
    val errorMessage: String? = null
)
