package yandex.practicum.yandex.practicum.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yandex.practicum.yandex.practicum.dto.TemperatureDto

@RestController
@RequestMapping("/actuator")
class HealthController {

    companion object {
        private val logger = LoggerFactory.getLogger(HealthController::class.java)
    }

	@GetMapping("/health")
    fun health(): Map<String, Any> {
        return mapOf("status" to "UP", "service" to "temperature-api")
    }

}