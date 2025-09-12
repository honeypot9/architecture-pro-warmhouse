package yandex.practicum.service

import yandex.practicum.dto.ScenarioCreateRequest
import yandex.practicum.dto.ScenarioExecutionResponse
import yandex.practicum.dto.ScenarioUpdateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yandex.practicum.entity.Scenario
import yandex.practicum.entity.ScenarioAction
import yandex.practicum.entity.ScenarioTrigger
import yandex.practicum.repository.ScenarioRepository
import java.time.LocalDateTime
import java.util.*

@Service
class ScenarioService(private val scenarioRepository: ScenarioRepository) {

    fun createScenario(request: ScenarioCreateRequest, userId: UUID): Scenario {
        validateScenario(request)
        
        val triggers = request.triggers.map { triggerDto ->
            ScenarioTrigger(
                type = triggerDto.type,
                deviceId = triggerDto.deviceId,
                condition = triggerDto.condition?.toString(),
                schedule = triggerDto.schedule
            )
        }
        
        val actions = request.actions.map { actionDto ->
            ScenarioAction(
                type = actionDto.type,
                deviceId = actionDto.deviceId,
                command = actionDto.command ?: "",
                parameters = actionDto.parameters?.toString(),
                message = actionDto.message,
                delaySeconds = actionDto.delaySeconds
            )
        }
        
        val scenario = Scenario(
            name = request.name,
            description = request.description,
            userId = userId,
            triggers = triggers,
            actions = actions,
            enabled = request.enabled
        )
        
        return scenarioRepository.save(scenario)
    }

    fun getUserScenarios(userId: UUID, enabled: Boolean?): List<Scenario> {
        return if (enabled != null) {
            scenarioRepository.findByUserIdAndEnabled(userId, enabled)
        } else {
            scenarioRepository.findByUserId(userId)
        }
    }

    fun getScenario(id: UUID, userId: UUID): Scenario {
        return scenarioRepository.findByIdAndUserId(id, userId)
            .orElseThrow { IllegalArgumentException("Scenario not found") }
    }

    @Transactional
    fun updateScenario(id: UUID, userId: UUID, request: ScenarioUpdateRequest): Scenario {
        val scenario = getScenario(id, userId)
        
        request.name?.let { scenario.name = it }
        request.description?.let { scenario.description = it }
        request.enabled?.let { scenario.enabled = it }
        
        request.triggers?.let { triggers ->
            scenario.triggers.clear()
            scenario.triggers.addAll(triggers.map { triggerDto ->
                ScenarioTrigger(
                    type = triggerDto.type,
                    deviceId = triggerDto.deviceId,
                    condition = triggerDto.condition?.toString(),
                    schedule = triggerDto.schedule
                )
            })
        }
        
        request.actions?.let { actions ->
            scenario.actions.clear()
            scenario.actions.addAll(actions.map { actionDto ->
                ScenarioAction(
                    type = actionDto.type,
                    deviceId = actionDto.deviceId,
                    command = actionDto.command ?: "",
                    parameters = actionDto.parameters?.toString(),
                    message = actionDto.message,
                    delaySeconds = actionDto.delaySeconds
                )
            })
        }
        
        scenario.updatedAt = LocalDateTime.now()
        return scenarioRepository.save(scenario)
    }

    fun deleteScenario(id: UUID, userId: UUID) {
        val scenario = getScenario(id, userId)
        scenarioRepository.delete(scenario)
    }

    @Transactional
    fun updateScenarioStatus(id: UUID, userId: UUID, enabled: Boolean): Scenario {
        val scenario = getScenario(id, userId)
        scenario.enabled = enabled
        scenario.updatedAt = LocalDateTime.now()
        return scenarioRepository.save(scenario)
    }

    @Transactional
    fun executeScenario(id: UUID, userId: UUID): ScenarioExecutionResponse {
        val scenario = getScenario(id, userId)
        
        if (!scenario.enabled) {
            throw IllegalStateException("Scenario is disabled")
        }
        
        scenario.executionCount++
        scenario.lastExecuted = LocalDateTime.now()
        scenarioRepository.save(scenario)
        
        // Здесь будет логика выполнения действий сценария
        return ScenarioExecutionResponse(
            executionId = "exec_${UUID.randomUUID()}",
            status = "started",
            scenarioId = scenario.id.toString(),
            startedAt = LocalDateTime.now()
        )
    }

    private fun validateScenario(request: ScenarioCreateRequest) {
        if (request.triggers.isEmpty()) {
            throw IllegalArgumentException("Scenario must have at least one trigger")
        }
        if (request.actions.isEmpty()) {
            throw IllegalArgumentException("Scenario must have at least one action")
        }
        // Дополнительная валидация логики сценария
    }
}