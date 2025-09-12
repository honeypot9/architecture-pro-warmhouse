package yandex.practicum.entity

import jakarta.persistence.*

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "protocols")
class Protocol {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, length = 50)
    lateinit var name: String

    @Column(nullable = false)
    lateinit var version: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var category: ProtocolCategory

    @Column(length = 500)
    var description: String? = null

    var maxDevices: Int? = null

    @Column(name = "specification_url")
    var specificationUrl: String? = null

    @ElementCollection
    var securityFeatures: MutableList<String> = mutableListOf()

    @Column(nullable = false)
    var enabled: Boolean = true

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(
        name: String,
        version: String,
        category: ProtocolCategory,
        description: String? = null,
        maxDevices: Int? = null,
        specificationUrl: String? = null,
        securityFeatures: List<String> = emptyList(),
        enabled: Boolean = true
    ) {
        this.name = name
        this.version = version
        this.category = category
        this.description = description
        this.maxDevices = maxDevices
        this.specificationUrl = specificationUrl
        this.securityFeatures.addAll(securityFeatures)
        this.enabled = enabled
    }
}

enum class ProtocolCategory {
    WIRELESS, WIRED, HYBRID
}