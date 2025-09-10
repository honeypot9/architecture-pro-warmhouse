package yandex.practicum.dto

import java.time.LocalDateTime
import java.util.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ScenarioCreateRequest(
    @field:NotBlank
    val name: String,

    val description: String? = null,

    @field:NotNull
    val triggers: List<ScenarioTriggerDto>,

    @field:NotNull
    val actions: List<ScenarioActionDto>,

    val enabled: Boolean = true
)

data class ScenarioUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val triggers: List<ScenarioTriggerDto>? = null,
    val actions: List<ScenarioActionDto>? = null,
    val enabled: Boolean? = null
)

data class ScenarioStatusUpdateRequest(
    @field:NotNull
    val enabled: Boolean
)

data class ScenarioTriggerDto(
    @field:NotBlank
    val type: String,
    val deviceId: String? = null,
    val condition: Map<String, Any>? = null,
    val schedule: String? = null
)

data class ScenarioActionDto(
    @field:NotBlank
    val type: String,
    val deviceId: String? = null,
    val command: String? = null,
    val parameters: Map<String, Any>? = null,
    val message: String? = null,
    val delaySeconds: Int? = null
)

data class ScenarioResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val triggers: List<ScenarioTriggerDto>,
    val actions: List<ScenarioActionDto>,
    val enabled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class ScenarioSummary(
    val id: UUID,
    val name: String,
    val description: String?,
    val enabled: Boolean,
    val triggerCount: Int,
    val actionCount: Int
)

data class ScenarioListResponse(
    val scenarios: List<ScenarioSummary>,
    val totalCount: Int
)

data class ScenarioDetailResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val triggers: List<ScenarioTriggerDetail>,
    val actions: List<ScenarioActionDetail>,
    val enabled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val executionCount: Int,
    val lastExecuted: LocalDateTime?
)

data class ScenarioTriggerDetail(
    val type: String,
    val deviceId: String?,
    val deviceName: String? = null,
    val condition: Map<String, Any>?
)

data class ScenarioActionDetail(
    val type: String,
    val deviceId: String?,
    val deviceName: String? = null,
    val command: String?,
    val parameters: Map<String, Any>?
)

data class ScenarioExecutionResponse(
    val executionId: String,
    val status: String,
    val scenarioId: String,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime? = null,
    val results: List<ActionResult>? = null
)

data class ActionResult(
    val actionIndex: Int,
    val status: String,
    val message: String? = null,
    val timestamp: LocalDateTime
)