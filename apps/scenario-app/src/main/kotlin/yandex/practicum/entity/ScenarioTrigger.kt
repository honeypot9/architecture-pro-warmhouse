package yandex.practicum.entity

import jakarta.persistence.*

@Entity
@Table(name = "scenario_triggers")
class ScenarioTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    lateinit var type: String

    var deviceId: String? = null

    @Column(columnDefinition = "TEXT")
    var condition: String? = null

    var schedule: String? = null

    constructor()

    constructor(type: String, deviceId: String? = null, condition: String? = null, schedule: String? = null) {
        this.type = type
        this.deviceId = deviceId
        this.condition = condition
        this.schedule = schedule
    }
}