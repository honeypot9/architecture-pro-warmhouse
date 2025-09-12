package yandex.practicum.entity

import jakarta.persistence.*

@Entity
@Table(name = "device_images")
class DeviceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    lateinit var url: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var type: ImageType

    var description: String? = null

    constructor()

    constructor(url: String, type: ImageType, description: String? = null) {
        this.url = url
        this.type = type
        this.description = description
    }
}

enum class DeviceCategory {
    SENSORS, LIGHTING, CLIMATE, SECURITY, ENTERTAINMENT, APPLIANCES
}

enum class ImageType {
    MAIN, DIAGRAM, SETUP, PACKAGING
}