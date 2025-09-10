package yandex.practicum.controller

import yandex.practicum.service.ScenarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.*
import yandex.practicum.entity.Scenario
import java.util.*

@RestController
@RequestMapping("/scenarios")
@Tag(name = "Scenario Registry", description = "API для управления пользовательскими сценариями автоматизации")
class ScenarioController(private val scenarioService: ScenarioService) {

    @PostMapping
    @Operation(summary = "Создание нового сценария")
    fun createScenario(
        @Valid @RequestBody request: ScenarioCreateRequest,
        @RequestParam user_id: UUID
    ): ResponseEntity<Any> {
        val scenario = scenarioService.createScenario(request, user_id)
        return ResponseEntity( mapToResponse(scenario), HttpStatus.OK)
    }

    @GetMapping
    @Operation(summary = "Получение списка сценариев")
    fun getScenarios(
        @RequestParam user_id: UUID,
        @RequestParam(required = false) enabled: Boolean?,
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int
    ): ResponseEntity<Any> {
        val scenarios = scenarioService.getUserScenarios(user_id, enabled)
            .drop(offset)
            .take(limit)
        
        val scenarioSummaries = scenarios.map { scenario ->
            ScenarioSummary(
                id = scenario.id!!,
                name = scenario.name,
                description = scenario.description,
                enabled = scenario.enabled,
                triggerCount = scenario.triggers.size,
                actionCount = scenario.actions.size
            )
        }

        return ResponseEntity( ScenarioListResponse(
            scenarios = scenarioSummaries,
            totalCount = scenarios.size
        ), HttpStatus.OK)
    }

    @GetMapping("/{scenario_id}")
    @Operation(summary = "Получение информации о сценарии")
    fun getScenario(
        @PathVariable("scenario_id") scenarioId: UUID,
        @RequestParam user_id: UUID
    ): ResponseEntity<Any> {
        val scenario = scenarioService.getScenario(scenarioId, user_id)
        return ResponseEntity(mapToDetailResponse(scenario), HttpStatus.OK)

    }

    @PutMapping("/{scenario_id}")
    @Operation(summary = "Обновление сценария")
    fun updateScenario(
        @PathVariable("scenario_id") scenarioId: UUID,
        @RequestParam user_id: UUID,
        @Valid @RequestBody request: ScenarioUpdateRequest
    ): ResponseEntity<Any> {
        val scenario = scenarioService.updateScenario(scenarioId, user_id, request)
        return ResponseEntity(mapToResponse(scenario), HttpStatus.OK)
    }

    @DeleteMapping("/{scenario_id}")
    @Operation(summary = "Удаление сценария")
    fun deleteScenario(
        @PathVariable("scenario_id") scenarioId: UUID,
        @RequestParam user_id: UUID
    ): ResponseEntity<Any> {
        return ResponseEntity(scenarioService.deleteScenario(scenarioId, user_id), HttpStatus.OK)
    }

    @PostMapping("/{scenario_id}/execute")
    @Operation(summary = "Принудительное выполнение сценария")
    fun executeScenario(
        @PathVariable("scenario_id") scenarioId: UUID,
        @RequestParam user_id: UUID
    ): ScenarioExecutionResponse {
        return scenarioService.executeScenario(scenarioId, user_id)
    }

    @PatchMapping("/{scenario_id}/status")
    @Operation(summary = "Изменение статуса сценария")
    fun updateScenarioStatus(
        @PathVariable("scenario_id") scenarioId: UUID,
        @RequestParam user_id: UUID,
        @Valid @RequestBody request: ScenarioStatusUpdateRequest
    ): ScenarioResponse {
        val scenario = scenarioService.updateScenarioStatus(scenarioId, user_id, request.enabled)
        return mapToResponse(scenario)
    }

    private fun mapToResponse(scenario: Scenario): ScenarioResponse {
        return ScenarioResponse(
            id = scenario.id!!,
            name = scenario.name,
            description = scenario.description,
            triggers = scenario.triggers.map { trigger ->
                ScenarioTriggerDto(
                    type = trigger.type,
                    deviceId = trigger.deviceId,
                    condition = trigger.condition?.let { parseMap(it) },
                    schedule = trigger.schedule
                )
            },
            actions = scenario.actions.map { action ->
                ScenarioActionDto(
                    type = action.type,
                    deviceId = action.deviceId,
                    command = action.command,
                    parameters = action.parameters?.let { parseMap(it) },
                    message = action.message,
                    delaySeconds = action.delaySeconds
                )
            },
            enabled = scenario.enabled,
            createdAt = scenario.createdAt,
            updatedAt = scenario.updatedAt
        )
    }

    private fun mapToDetailResponse(scenario: Scenario): ScenarioDetailResponse {
        return ScenarioDetailResponse(
            id = scenario.id!!,
            name = scenario.name,
            description = scenario.description,
            triggers = scenario.triggers.map { trigger ->
                ScenarioTriggerDetail(
                    type = trigger.type,
                    deviceId = trigger.deviceId,
                    condition = trigger.condition?.let { parseMap(it) }
                )
            },
            actions = scenario.actions.map { action ->
                ScenarioActionDetail(
                    type = action.type,
                    deviceId = action.deviceId,
                    command = action.command,
                    parameters = action.parameters?.let { parseMap(it) }
                )
            },
            enabled = scenario.enabled,
            createdAt = scenario.createdAt,
            updatedAt = scenario.updatedAt,
            executionCount = scenario.executionCount,
            lastExecuted = scenario.lastExecuted
        )
    }

    private fun parseMap(str: String): Map<String, Any> {
        // Упрощенный парсинг JSON строки
        return mapOf("raw" to str)
    }
}