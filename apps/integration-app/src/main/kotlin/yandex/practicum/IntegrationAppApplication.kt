package yandex.practicum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntegrationAppApplication
fun main(args: Array<String>) {
    runApplication<IntegrationAppApplication>(*args)
}