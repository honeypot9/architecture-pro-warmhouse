package yandex.practicum.entity

import jakarta.persistence.*

@Entity
@Table(name = "scenario_actions")
class ScenarioAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    lateinit var type: String

    var deviceId: String? = null

    @Column(nullable = false)
    lateinit var command: String

    @Column(columnDefinition = "TEXT")
    var parameters: String? = null

    var message: String? = null
    var delaySeconds: Int? = null

    constructor()

    constructor(
        type: String,
        deviceId: String? = null,
        command: String,
        parameters: String? = null,
        message: String? = null,
        delaySeconds: Int? = null
    ) {
        this.type = type
        this.deviceId = deviceId
        this.command = command
        this.parameters = parameters
        this.message = message
        this.delaySeconds = delaySeconds
    }
}