package yandex.practicum.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "onboarding_processes")
class OnboardingProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var onboardingId: UUID? = null

    @Column(nullable = false)
    lateinit var deviceModel: String

    @Column(nullable = false, unique = true)
    lateinit var serialNumber: String

    @Column(nullable = false)
    lateinit var userId: UUID

    @Enumerated(EnumType.STRING)
    var status: OnboardingStatus = OnboardingStatus.PENDING_VERIFICATION

    lateinit var verificationCode: String
    lateinit var expiresAt: LocalDateTime

    var deviceId: UUID? = null

    // Конструктор по умолчанию для Hibernate
    constructor()

    // Конструктор с параметрами
    constructor(
        deviceModel: String,
        serialNumber: String,
        userId: UUID,
        verificationCode: String,
        expiresAt: LocalDateTime
    ) {
        this.deviceModel = deviceModel
        this.serialNumber = serialNumber
        this.userId = userId
        this.verificationCode = verificationCode
        this.expiresAt = expiresAt
    }
}

enum class OnboardingStatus {
    PENDING_VERIFICATION, IN_PROGRESS, COMPLETED, FAILED
}