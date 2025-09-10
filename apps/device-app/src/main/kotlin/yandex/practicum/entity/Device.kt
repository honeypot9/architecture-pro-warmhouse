package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "devices")
class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val deviceId: UUID? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    val deviceModel: String,

    @Column(nullable = false, unique = true)
    val serialNumber: String,

    @Column(nullable = false)
    val userId: UUID,

    @Enumerated(EnumType.STRING)
    var status: DeviceStatus = DeviceStatus.PROVISIONING,

    var firmwareVersion: String? = null,
    var lastSeen: LocalDateTime? = null,

    @ElementCollection
    var capabilities: MutableSet<String> = mutableSetOf(),

    @Column(columnDefinition = "JSON")
    var configuration: String? = null
)

enum class DeviceStatus {
    ONLINE, OFFLINE, PROVISIONING, ERROR
}