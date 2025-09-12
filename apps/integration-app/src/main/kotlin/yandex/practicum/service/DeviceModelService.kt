package yandex.practicum.service

import org.springframework.stereotype.Service
import yandex.practicum.dto.DeviceModelCreateRequest
import yandex.practicum.entity.DeviceCategory
import yandex.practicum.entity.DeviceImage
import yandex.practicum.entity.DeviceModel
import yandex.practicum.entity.ImageType
import yandex.practicum.repository.DeviceModelRepository
import yandex.practicum.repository.ProtocolRepository
import java.util.*

@Service
class DeviceModelService(
    private val deviceModelRepository: DeviceModelRepository,
    private val protocolRepository: ProtocolRepository
) {

    fun createDeviceModel(request: DeviceModelCreateRequest): DeviceModel {
        // Проверяем, не существует ли уже модель с таким именем и производителем
        val existingModel = deviceModelRepository.findByNameAndManufacturer(request.name, request.manufacturer)
        if (existingModel.isPresent) {
            throw IllegalArgumentException("Device model with this name and manufacturer already exists")
        }

        // Проверяем, существует ли указанный протокол
        if (!protocolRepository.existsById(UUID.fromString(request.protocol))) {
            throw IllegalArgumentException("Protocol not found")
        }

        val category = DeviceCategory.valueOf(request.category.uppercase())

        val images = request.images.map { imageDto ->
            DeviceImage(
                url = imageDto.url,
                type = ImageType.valueOf(imageDto.type.uppercase()),
                description = imageDto.description
            )
        }

        val deviceModel = DeviceModel(
            name = request.name,
            manufacturer = request.manufacturer,
            protocolId = request.protocol,
            category = category,
            description = request.description,
            capabilities = request.capabilities,
            supportedCommands = request.supportedCommands,
            configurationTemplate = request.configurationTemplate?.toString(),
            images = images,
            documentationUrl = request.documentationUrl
        )

        return deviceModelRepository.save(deviceModel)
    }

    fun getDeviceModels(protocol: String?, manufacturer: String?, category: String?): List<DeviceModel> {
        return deviceModelRepository.findByProtocolAndManufacturerAndCategory(protocol, manufacturer, category)
    }

    fun getDeviceModel(id: UUID): DeviceModel {
        return deviceModelRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Device model not found") }
    }
}