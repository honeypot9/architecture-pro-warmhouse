package yandex.practicum.yandex.practicum.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class  TemperatureDto
    (
    @JsonProperty("location")
    val location: String?,
    @JsonProperty("temperature")
    val temperature: String?,
    @JsonProperty("sensorID")
    val sensorID: Int?
)


