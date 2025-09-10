package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yandex.practicum.yandex.practicum.dto.TemperatureDto

@RestController
@RequestMapping("/temperature")
@Tag(name = "Temperature Api", description = "API датчика температуры")
class TemperatureController {

    companion object {
        private val logger = LoggerFactory.getLogger(TemperatureController::class.java)
    }

    @GetMapping
    @Operation(summary = "Получение температуры датчика")
    fun getLocationTemperature(
        @RequestParam location: String?,
        @RequestParam sensorID: Int?
    ): ResponseEntity<TemperatureDto> {
        // If no location is provided, use a default based on sensor ID
        if (location.isNullOrEmpty()) {
            return when (sensorID) {
                1 -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Living Room",
                        sensorID = sensorID
                    )
                )

                2 -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Bedroom",
                        sensorID = sensorID
                    )
                )

                3 -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Kitchen",
                        sensorID = sensorID
                    )
                )

                else -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Unknown",
                        sensorID = 0
                    )
                )
            }
        }
        if (sensorID == null) {
            return when (location) {
                "Living Room" -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Living Room",
                        sensorID = sensorID
                    )
                )

                "Bedroom" -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Bedroom",
                        sensorID = sensorID
                    )
                )

                "Kitchen" -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Kitchen",
                        sensorID = sensorID
                    )
                )

                else -> ResponseEntity.ok(
                    TemperatureDto(
                        temperature = (15..25).random().toString(),
                        location = "Unknown",
                        sensorID = 0
                    )
                )
            }
        }
        throw Exception("location and sensorID are empty!")
    }

}