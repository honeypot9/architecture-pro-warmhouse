package yandex.practicum.entity

import jakarta.persistence.*

@Entity
@Table(name = "metric_configs")
class MetricConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    lateinit var name: String

    @Column(nullable = false)
    var enabled: Boolean = true

    var threshold: Double? = null

    constructor()

    constructor(name: String, enabled: Boolean = true, threshold: Double? = null) {
        this.name = name
        this.enabled = enabled
        this.threshold = threshold
    }
}
