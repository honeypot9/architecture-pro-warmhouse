package yandex.practicum.service

import yandex.practicum.entity.OnboardingProcess
import yandex.practicum.entity.OnboardingStatus
import yandex.practicum.repository.OnboardingRepository
import org.springframework.stereotype.Service
import yandex.practicum.dto.OnboardingCompleteRequest
import yandex.practicum.dto.OnboardingCompleteResponse
import yandex.practicum.dto.OnboardingStartRequest
import yandex.practicum.dto.OnboardingStartResponse
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

@Service
class OnboardingService(
    private val onboardingRepository: OnboardingRepository,
    private val deviceService: DeviceService
) {

    fun startOnboarding(request: OnboardingStartRequest): OnboardingStartResponse {
        // Проверяем, не начат ли уже процесс онбординга для этого устройства
        val existingProcess = onboardingRepository.findBySerialNumber(request.serialNumber)
        if (existingProcess != null) {
            throw IllegalArgumentException("Onboarding already started for this device")
        }

        val verificationCode = generateVerificationCode()
        val expiresAt = LocalDateTime.now().plusMinutes(15)

        val process = OnboardingProcess(
            deviceModel = request.deviceModel,
            serialNumber = request.serialNumber,
            userId = request.userId,
            verificationCode = verificationCode,
            expiresAt = expiresAt
        )

        val savedProcess = onboardingRepository.save(process)

        return OnboardingStartResponse(
            onboardingId = savedProcess.onboardingId!!,
            status = savedProcess.status.toString(),
            verificationCode = savedProcess.verificationCode,
            expiresAt = savedProcess.expiresAt
        )
    }

    fun completeOnboarding(onboardingId: UUID, request: OnboardingCompleteRequest): OnboardingCompleteResponse {
        val process = onboardingRepository.findById(onboardingId)
            .orElseThrow { IllegalArgumentException("Onboarding process not found") }

        if (process.status == OnboardingStatus.COMPLETED) {
            throw IllegalArgumentException("Onboarding already completed")
        }

        if (process.verificationCode != request.verificationCode) {
            throw IllegalArgumentException("Invalid verification code")
        }

        if (process.expiresAt.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("Verification code expired")
        }

        // Создаем устройство
        val device = deviceService.createDevice(
            model = process.deviceModel,
            serialNumber = process.serialNumber,
            userId = process.userId
        )

        process.status = OnboardingStatus.COMPLETED
        process.deviceId = device.deviceId
        onboardingRepository.save(process)

        return OnboardingCompleteResponse(
            deviceId = device.deviceId!!,
            status = "completed",
            message = "Device successfully onboarded"
        )
    }

    private fun generateVerificationCode(): String {
        return Random.nextInt(100000, 999999).toString()
    }
}