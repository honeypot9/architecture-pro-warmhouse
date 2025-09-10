package yandex.practicum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import yandex.practicum.entity.DeviceModel
import java.util.*

interface DeviceModelRepository : JpaRepository<DeviceModel, UUID> {
    fun findByNameAndManufacturer(name: String, manufacturer: String): Optional<DeviceModel>

    @Query("SELECT d FROM DeviceModel d WHERE (:protocol IS NULL OR d.protocolId = :protocol) AND (:manufacturer IS NULL OR d.manufacturer = :manufacturer) AND (:category IS NULL OR d.category = :category)")
    fun findByProtocolAndManufacturerAndCategory(
        @Param("protocol") protocol: String?,
        @Param("manufacturer") manufacturer: String?,
        @Param("category") category: String?
    ): List<DeviceModel>
}
