package yandex.practicum.controller


import yandex.practicum.service.DeviceModelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.*
import yandex.practicum.entity.DeviceModel
import java.util.*

@RestController
@RequestMapping("/device-models")
@Tag(name = "Device Catalog", description = "API для управления каталогом устройств")
class DeviceModelController(private val deviceModelService: DeviceModelService) {

    @PostMapping
    @Operation(summary = "Добавление модели устройства в каталог")
    fun createDeviceModel(@Valid @RequestBody request: DeviceModelCreateRequest): DeviceModelResponse {
        val deviceModel = deviceModelService.createDeviceModel(request)
        return mapToResponse(deviceModel)
    }

    @GetMapping
    @Operation(summary = "Получение каталога устройств")
    fun getDeviceModels(
        @RequestParam(required = false) protocol: String?,
        @RequestParam(required = false) manufacturer: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int
    ): DeviceModelListResponse {
        val deviceModels = deviceModelService.getDeviceModels(protocol, manufacturer, category)
            .drop(offset)
            .take(limit)

        val modelSummaries = deviceModels.map { model ->
            DeviceModelSummary(
                id = model.id!!,
                name = model.name,
                manufacturer = model.manufacturer,
                protocol = model.protocolId,
                category = model.category.toString(),
                description = model.description,
                capabilities = model.capabilities,
                supportedCommands = model.supportedCommands
            )
        }

        return DeviceModelListResponse(
            deviceModels = modelSummaries,
            totalCount = deviceModels.size
        )
    }

    @GetMapping("/{model_id}")
    @Operation(summary = "Получение информации о модели устройства")
    fun getDeviceModel(@PathVariable("model_id") modelId: UUID): DeviceModelDetailResponse {
        val deviceModel = deviceModelService.getDeviceModel(modelId)
        return mapToDetailResponse(deviceModel)
    }

    private fun mapToResponse(deviceModel: DeviceModel): DeviceModelResponse {
        return DeviceModelResponse(
            id = deviceModel.id!!,
            name = deviceModel.name,
            manufacturer = deviceModel.manufacturer,
            protocol = deviceModel.protocolId,
            category = deviceModel.category.toString(),
            description = deviceModel.description,
            capabilities = deviceModel.capabilities,
            supportedCommands = deviceModel.supportedCommands,
            createdAt = deviceModel.createdAt,
            updatedAt = deviceModel.updatedAt
        )
    }

    private fun mapToDetailResponse(deviceModel: DeviceModel): DeviceModelDetailResponse {
        val imageDtos = deviceModel.images.map { image ->
            DeviceImageDto(
                url = image.url,
                type = image.type.toString(),
                description = image.description
            )
        }

        return DeviceModelDetailResponse(
            id = deviceModel.id!!,
            name = deviceModel.name,
            manufacturer = deviceModel.manufacturer,
            protocol = deviceModel.protocolId,
            category = deviceModel.category.toString(),
            description = deviceModel.description,
            capabilities = deviceModel.capabilities,
            supportedCommands = deviceModel.supportedCommands,
            configurationTemplate = deviceModel.configurationTemplate?.let { parseMap(it) },
            images = imageDtos,
            documentationUrl = deviceModel.documentationUrl,
            createdAt = deviceModel.createdAt,
            updatedAt = deviceModel.updatedAt
        )
    }

    private fun parseMap(str: String): Map<String, Any> {
        // Упрощенный парсинг JSON строки
        return mapOf("raw" to str)
    }
}