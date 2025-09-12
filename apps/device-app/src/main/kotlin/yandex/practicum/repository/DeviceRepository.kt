package yandex.practicum.repository

import yandex.practicum.entity.Device
import yandex.practicum.entity.DeviceStatus
import yandex.practicum.entity.OnboardingProcess
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DeviceRepository : JpaRepository<Device, UUID> {
    fun findByUserId(userId: UUID): List<Device>
    
    @Query("SELECT d FROM Device d WHERE d.userId = :userId AND (:status IS NULL OR d.status = :status)")
    fun findByUserIdAndStatus(
        @Param("userId") userId: UUID,
        @Param("status") status: DeviceStatus?
    ): List<Device>
    
    fun findBySerialNumber(serialNumber: String): Device?
}

interface OnboardingRepository : JpaRepository<OnboardingProcess, UUID> {
    fun findBySerialNumber(serialNumber: String): OnboardingProcess?
    fun findByVerificationCode(verificationCode: String): OnboardingProcess?
}