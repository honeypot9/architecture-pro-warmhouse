package yandex.practicum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.*
import yandex.practicum.entity.Protocol
import yandex.practicum.service.ProtocolService
import java.util.*

@RestController
@RequestMapping("/protocols")
@Tag(name = "Protocol Registry", description = "API для управления поддерживаемыми протоколами")
class ProtocolController(private val protocolService: ProtocolService) {

    @PostMapping
    @Operation(summary = "Добавление нового протокола")
    fun createProtocol(@Valid @RequestBody request: ProtocolCreateRequest): ProtocolResponse {
        val protocol = protocolService.createProtocol(request)
        return mapToResponse(protocol)
    }

    @GetMapping
    @Operation(summary = "Получение списка поддерживаемых протоколов")
    fun getProtocols(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) enabled: Boolean?
    ): ProtocolListResponse {
        val protocols = protocolService.getProtocols(category, enabled)
        
        val protocolSummaries = protocols.map { protocol ->
            ProtocolSummary(
                id = protocol.id!!,
                name = protocol.name,
                version = protocol.version,
                category = protocol.category.toString(),
                enabled = protocol.enabled,
                description = protocol.description,
                maxDevices = protocol.maxDevices
            )
        }
        
        return ProtocolListResponse(
            protocols = protocolSummaries,
            totalCount = protocols.size
        )
    }

    @GetMapping("/{protocol_id}")
    @Operation(summary = "Получение информации о протоколе")
    fun getProtocol(@PathVariable("protocol_id") protocolId: UUID): ProtocolDetailResponse {
        val protocol = protocolService.getProtocol(protocolId)
        return mapToDetailResponse(protocol)
    }

    private fun mapToResponse(protocol: Protocol): ProtocolResponse {
        return ProtocolResponse(
            id = protocol.id!!,
            name = protocol.name,
            version = protocol.version,
            category = protocol.category.toString(),
            enabled = protocol.enabled,
            description = protocol.description,
            maxDevices = protocol.maxDevices,
            specificationUrl = protocol.specificationUrl,
            createdAt = protocol.createdAt,
            updatedAt = protocol.updatedAt
        )
    }

    private fun mapToDetailResponse(protocol: Protocol): ProtocolDetailResponse {
        return ProtocolDetailResponse(
            id = protocol.id!!,
            name = protocol.name,
            version = protocol.version,
            category = protocol.category.toString(),
            enabled = protocol.enabled,
            description = protocol.description,
            maxDevices = protocol.maxDevices,
            specificationUrl = protocol.specificationUrl,
            securityFeatures = protocol.securityFeatures,
            createdAt = protocol.createdAt,
            updatedAt = protocol.updatedAt
        )
    }
}