package yandex.practicum.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.*
import java.util.*

@RestController
@RequestMapping
class AuthorizationController {

    @PostMapping("/permissions/check")
    fun checkPermission(
        userDetails: UserDetails,
        @RequestBody request: PermissionCheckRequest
    ): ResponseEntity<PermissionCheckResponse> {
        // Simplified permission check - in real app, implement proper authorization logic
        val hasPermission = userDetails.authorities.any { it.authority == "ROLE_ADMIN" }

        return ResponseEntity.ok(PermissionCheckResponse(hasPermission, listOf("ADMIN")))
    }

    @GetMapping("/roles")
    fun getRoles(userDetails: UserDetails): ResponseEntity<RolesListResponse> {
        // Return predefined roles
        val roles = listOf(
            RoleInfo("1", "USER", "Regular user", listOf("read")),
            RoleInfo("2", "ADMIN", "Administrator", listOf("read", "write", "delete")),
            RoleInfo("3", "MODERATOR", "Content moderator", listOf("read", "write"))
        )

        return ResponseEntity.ok(RolesListResponse(roles, roles.size))
    }

    @GetMapping("/users/{user_id}/roles")
    fun getUserRoles(
        @PathVariable("user_id") userId: UUID,
        userDetails: UserDetails
    ): ResponseEntity<UserRolesResponse> {
        // Simplified - return user roles
        return ResponseEntity.ok(UserRolesResponse(userId, listOf("USER")))
    }

    @PutMapping("/users/{user_id}/roles")
    fun updateUserRoles(
        @PathVariable("user_id") userId: UUID,
        @RequestBody request: UserRolesUpdateRequest,
        userDetails: UserDetails
    ): ResponseEntity<UserRolesResponse> {
        // Simplified - in real app, implement role management
        return ResponseEntity.ok(UserRolesResponse(userId, request.roles))
    }
}