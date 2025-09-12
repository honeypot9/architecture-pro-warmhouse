package yandex.practicum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeviceAppApplication
fun main(args: Array<String>) {
    runApplication<DeviceAppApplication>(*args)
}