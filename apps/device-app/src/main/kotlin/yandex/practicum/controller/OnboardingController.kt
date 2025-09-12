package yandex.practicum.controller

import yandex.practicum.service.OnboardingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.OnboardingCompleteRequest
import yandex.practicum.dto.OnboardingCompleteResponse
import yandex.practicum.dto.OnboardingStartRequest
import yandex.practicum.dto.OnboardingStartResponse
import java.util.*

@RestController
@RequestMapping("/onboarding")
@Tag(name = "Onboarding", description = "API для управления процессом онбординга устройств")
class OnboardingController(private val onboardingService: OnboardingService) {

    @PostMapping("/start")
    @Operation(summary = "Начать процесс онбординга устройства")
    fun startOnboarding(@Valid @RequestBody request: OnboardingStartRequest): ResponseEntity<Any> {
        try {
            return ResponseEntity(onboardingService.startOnboarding(request), HttpStatus.OK)
        }catch (e: Exception){
            return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/{onboardingId}/complete")
    @Operation(summary = "Завершить процесс онбординга")
    fun completeOnboarding(
        @PathVariable onboardingId: UUID,
        @Valid @RequestBody request: OnboardingCompleteRequest
    ): ResponseEntity<Any> {
        try {
            return ResponseEntity(onboardingService.completeOnboarding(onboardingId, request), HttpStatus.OK)
        }catch (e: Exception){
            return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}