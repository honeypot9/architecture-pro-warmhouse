package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "integration_tests")
class IntegrationTest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false)
    lateinit var protocolId: String

    @Column(nullable = false)
    lateinit var deviceModelId: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var testType: TestType

    @Column(columnDefinition = "TEXT")
    var parameters: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TestStatus = TestStatus.STARTED

    @Column(columnDefinition = "TEXT")
    var results: String? = null

    var errorMessage: String? = null

    @Column(nullable = false)
    var startedAt: LocalDateTime = LocalDateTime.now()

    var completedAt: LocalDateTime? = null

    constructor()

    constructor(
        protocolId: String,
        deviceModelId: String,
        testType: TestType,
        parameters: String? = null
    ) {
        this.protocolId = protocolId
        this.deviceModelId = deviceModelId
        this.testType = testType
        this.parameters = parameters
    }
}

enum class TestType {
    CONNECTION, FUNCTIONALITY, PERFORMANCE, SECURITY
}

enum class TestStatus {
    STARTED, IN_PROGRESS, COMPLETED, FAILED
}