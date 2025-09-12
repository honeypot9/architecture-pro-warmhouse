package yandex.practicum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TemperatureApiApplication
fun main(args: Array<String>) {
    runApplication<TemperatureApiApplication>(*args)
}