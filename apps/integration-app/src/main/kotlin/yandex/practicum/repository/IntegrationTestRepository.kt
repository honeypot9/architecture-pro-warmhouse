package yandex.practicum.repository

import org.springframework.data.jpa.repository.JpaRepository
import yandex.practicum.entity.IntegrationTest
import java.util.*

interface IntegrationTestRepository : JpaRepository<IntegrationTest, UUID>