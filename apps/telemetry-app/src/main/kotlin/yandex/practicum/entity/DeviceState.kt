package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
@Entity
@Table(name = "device_states")
class DeviceState {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, unique = true)
    lateinit var deviceId: String

    @Column(nullable = false)
    var lastSeen: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: DeviceStatus = DeviceStatus.UNKNOWN

    @Column(columnDefinition = "TEXT")
    var state: String? = null

    constructor()

    constructor(deviceId: String, lastSeen: LocalDateTime, status: DeviceStatus, state: String?) {
        this.deviceId = deviceId
        this.lastSeen = lastSeen
        this.status = status
        this.state = state
    }
}

enum class DeviceStatus {
    ONLINE, OFFLINE, UNKNOWN, ERROR
}