package yandex.practicum.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*

// Authentication DTOs
data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String? = null,
    val acceptTerms: Boolean = false
)

data class UserRegistrationResponse(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val status: String,
    val message: String = "User registered successfully"
)

data class UserLoginRequest(
    val email: String,
    val password: String
)

data class UserLoginResponse(
    val message: String = "Login successful",
    val user: UserInfo
)

data class UserInfo(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val roles: List<String>
)

data class LogoutResponse(
    val message: String = "Logged out successfully"
)

// User Management DTOs
data class UserProfileResponse(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastLogin: LocalDateTime?,
    val preferences: Map<String, Any>?
)

data class UserProfileUpdateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    val preferences: Map<String, Any>? = null
)

data class PasswordChangeRequest(
    val currentPassword: String,
    val newPassword: String
)

data class PasswordChangeResponse(
    val message: String = "Password changed successfully"
)

// Authorization DTOs
data class PermissionCheckRequest(
    val resource: String,
    val action: String,
    val resourceId: String? = null
)

data class PermissionCheckResponse(
    val hasPermission: Boolean,
    val requiredRoles: List<String> = emptyList()
)

data class RolesListResponse(
    val roles: List<RoleInfo>,
    val totalCount: Int
)

data class RoleInfo(
    val roleId: String,
    val name: String,
    val description: String?,
    val permissions: List<String>
)

data class UserRolesResponse(
    val userId: UUID,
    val roles: List<String>
)

data class UserRolesUpdateRequest(
    @field:NotNull
    val roles: List<String>
)