package yandex.practicum.dto

import java.time.LocalDateTime
import java.util.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class OnboardingStartRequest(
    @field:NotBlank
    val deviceModel: String,
    
    @field:NotBlank
    val serialNumber: String,
    
    @field:NotNull
    val userId: UUID
)

data class OnboardingStartResponse(
    val onboardingId: UUID,
    val status: String,
    val verificationCode: String,
    val expiresAt: LocalDateTime
)

data class OnboardingCompleteRequest(
    @field:NotBlank
    val verificationCode: String
)

data class OnboardingCompleteResponse(
    val deviceId: UUID,
    val status: String,
    val message: String
)