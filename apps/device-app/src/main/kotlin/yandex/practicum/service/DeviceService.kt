package yandex.practicum.service

import yandex.practicum.entity.Device
import yandex.practicum.entity.DeviceStatus
import yandex.practicum.repository.DeviceRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class DeviceService(private val deviceRepository: DeviceRepository) {
    
    fun getDevicesByUser(userId: UUID, status: DeviceStatus? = null): List<Device> {
        return if (status != null) {
            deviceRepository.findByUserIdAndStatus(userId, status)
        } else {
            deviceRepository.findByUserId(userId)
        }
    }
    
    fun getDeviceDetails(deviceId: UUID): Device {
        return deviceRepository.findById(deviceId)
            .orElseThrow { IllegalArgumentException("Device not found") }
    }
    
    fun createDevice(model: String, serialNumber: String, userId: UUID): Device {
        val device = Device(
            name = "$model Device",
            deviceModel = model,
            serialNumber = serialNumber,
            userId = userId,
            status = DeviceStatus.ONLINE,
            lastSeen = LocalDateTime.now()
        )
        return deviceRepository.save(device)
    }
    
    fun updateDeviceConfiguration(deviceId: UUID, configuration: Map<String, Any>): Device {
        val device = getDeviceDetails(deviceId)
        // Здесь можно добавить валидацию конфигурации
        device.configuration = configuration.toString()
        return deviceRepository.save(device)
    }
}