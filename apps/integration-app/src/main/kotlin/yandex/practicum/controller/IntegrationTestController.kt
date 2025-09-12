package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.IntegrationTestRequest
import yandex.practicum.dto.IntegrationTestResponse
import yandex.practicum.entity.IntegrationTest
import yandex.practicum.service.IntegrationTestService
import java.util.*

@RestController
@RequestMapping("/integration-test")
@Tag(name = "Integration Testing", description = "API для тестирования интеграции устройств")
class IntegrationTestController(private val integrationTestService: IntegrationTestService) {

    @PostMapping
    @Operation(summary = "Тестирование интеграции устройства")
    fun testIntegration(@Valid @RequestBody request: IntegrationTestRequest): IntegrationTestResponse {
        val test = integrationTestService.startIntegrationTest(request)
        return mapToResponse(test)
    }

    @GetMapping("/{test_id}")
    @Operation(summary = "Получение результатов тестирования")
    fun getTestResult(@PathVariable("test_id") testId: UUID): IntegrationTestResponse {
        val test = integrationTestService.getTestResult(testId)
        return mapToResponse(test)
    }

    private fun mapToResponse(test: IntegrationTest): IntegrationTestResponse {
        return IntegrationTestResponse(
            testId = test.id.toString(),
            status = test.status.toString(),
            protocol = test.protocolId,
            deviceModel = test.deviceModelId,
            startedAt = test.startedAt,
            completedAt = test.completedAt,
            results = test.results?.let { parseMap(it) },
            errorMessage = test.errorMessage
        )
    }

    private fun parseMap(str: String): Map<String, Any> {
        return mapOf("raw" to str)
    }
}