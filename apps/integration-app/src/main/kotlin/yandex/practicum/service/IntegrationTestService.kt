package yandex.practicum.service

import org.springframework.stereotype.Service
import yandex.practicum.dto.IntegrationTestRequest
import yandex.practicum.entity.IntegrationTest
import yandex.practicum.entity.TestType
import yandex.practicum.repository.DeviceModelRepository
import yandex.practicum.repository.IntegrationTestRepository
import yandex.practicum.repository.ProtocolRepository
import java.util.*

@Service
class IntegrationTestService(
    private val integrationTestRepository: IntegrationTestRepository,
    private val protocolRepository: ProtocolRepository,
    private val deviceModelRepository: DeviceModelRepository
) {

    fun startIntegrationTest(request: IntegrationTestRequest): IntegrationTest {
        // Проверяем существование протокола и модели устройства
        if (!protocolRepository.existsById(UUID.fromString(request.protocol))) {
            throw IllegalArgumentException("Protocol not found")
        }
        if (!deviceModelRepository.existsById(UUID.fromString(request.deviceModel))) {
            throw IllegalArgumentException("Device model not found")
        }

        val testType = TestType.valueOf(request.testType.uppercase())

        val integrationTest = IntegrationTest(
            protocolId = request.protocol,
            deviceModelId = request.deviceModel,
            testType = testType,
            parameters = request.parameters?.toString()
        )

        return integrationTestRepository.save(integrationTest)
    }

    fun getTestResult(testId: UUID): IntegrationTest {
        return integrationTestRepository.findById(testId)
            .orElseThrow { IllegalArgumentException("Integration test not found") }
    }
}