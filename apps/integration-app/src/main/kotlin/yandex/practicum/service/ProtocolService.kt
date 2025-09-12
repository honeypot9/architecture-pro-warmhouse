package yandex.practicum.service

import org.springframework.stereotype.Service
import yandex.practicum.dto.ProtocolCreateRequest
import yandex.practicum.entity.Protocol
import yandex.practicum.entity.ProtocolCategory
import yandex.practicum.repository.ProtocolRepository
import java.util.*

@Service
class ProtocolService(private val protocolRepository: ProtocolRepository) {

    fun createProtocol(request: ProtocolCreateRequest): Protocol {
        // Проверяем, не существует ли уже протокол с таким именем и версией
        val existingProtocol = protocolRepository.findByNameAndVersion(request.name, request.version)
        if (existingProtocol.isPresent) {
            throw IllegalArgumentException("Protocol with this name and version already exists")
        }

        val category = ProtocolCategory.valueOf(request.category.uppercase())

        val protocol = Protocol(
            name = request.name,
            version = request.version,
            category = category,
            description = request.description,
            maxDevices = request.maxDevices,
            specificationUrl = request.specificationUrl,
            securityFeatures = request.securityFeatures,
            enabled = request.enabled
        )

        return protocolRepository.save(protocol)
    }

    fun getProtocols(category: String?, enabled: Boolean?): List<Protocol> {
        val protocolCategory = category?.let { ProtocolCategory.valueOf(it.uppercase()) }
        return protocolRepository.findByCategoryAndEnabled(protocolCategory, enabled)
    }

    fun getProtocol(id: UUID): Protocol {
        return protocolRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Protocol not found") }
    }

    fun getProtocolByCode(protocolCode: String): Protocol {
        return protocolRepository.findById(UUID.fromString(protocolCode))
            .orElseThrow { IllegalArgumentException("Protocol not found") }
    }
}