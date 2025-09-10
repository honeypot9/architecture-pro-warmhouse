package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "device_models")
class DeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, length = 100)
    lateinit var name: String

    @Column(nullable = false, length = 50)
    lateinit var manufacturer: String

    @Column(nullable = false)
    lateinit var protocolId: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var category: DeviceCategory

    @Column(length = 1000)
    var description: String? = null

    @ElementCollection
    var capabilities: MutableList<String> = mutableListOf()

    @ElementCollection
    var supportedCommands: MutableList<String> = mutableListOf()

    @Column(columnDefinition = "TEXT")
    var configurationTemplate: String? = null

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "device_model_id")
    var images: MutableList<DeviceImage> = mutableListOf()

    var documentationUrl: String? = null

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(
        name: String,
        manufacturer: String,
        protocolId: String,
        category: DeviceCategory,
        description: String? = null,
        capabilities: List<String> = emptyList(),
        supportedCommands: List<String> = emptyList(),
        configurationTemplate: String? = null,
        images: List<DeviceImage> = emptyList(),
        documentationUrl: String? = null
    ) {
        this.name = name
        this.manufacturer = manufacturer
        this.protocolId = protocolId
        this.category = category
        this.description = description
        this.capabilities.addAll(capabilities)
        this.supportedCommands.addAll(supportedCommands)
        this.configurationTemplate = configurationTemplate
        this.images.addAll(images)
        this.documentationUrl = documentationUrl
    }
}