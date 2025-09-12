package yandex.practicum.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/actuator")
class HealthController {

    companion object {
        private val logger = LoggerFactory.getLogger(HealthController::class.java)
    }

	@GetMapping("/health")
    fun health(): Map<String, Any> {
        return mapOf("status" to "UP", "service" to "integration-app")
    }

}