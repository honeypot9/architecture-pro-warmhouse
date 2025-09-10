package yandex.practicum.entity

import jakarta.persistence.*
import yandex.practicum.entity.ScenarioAction
import yandex.practicum.entity.ScenarioTrigger
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "scenarios")
class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, length = 100)
    lateinit var name: String

    @Column(length = 500)
    var description: String? = null

    @Column(nullable = false)
    var userId: UUID? = null

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "scenario_id")
    var triggers: MutableList<ScenarioTrigger> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "scenario_id")
    var actions: MutableList<ScenarioAction> = mutableListOf()

    @Column(nullable = false)
    var enabled: Boolean = true

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    var executionCount: Int = 0
    var lastExecuted: LocalDateTime? = null

    constructor()

    constructor(
        name: String,
        description: String?,
        userId: UUID,
        triggers: List<ScenarioTrigger>,
        actions: List<ScenarioAction>,
        enabled: Boolean = true
    ) {
        this.name = name
        this.description = description
        this.userId = userId
        this.triggers.addAll(triggers)
        this.actions.addAll(actions)
        this.enabled = enabled
    }
}