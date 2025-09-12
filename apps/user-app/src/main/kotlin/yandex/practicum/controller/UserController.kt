package yandex.practicum.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.PasswordChangeRequest
import yandex.practicum.dto.PasswordChangeResponse
import yandex.practicum.dto.UserProfileResponse
import yandex.practicum.dto.UserProfileUpdateRequest
import yandex.practicum.service.UserService
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/me")
    fun getCurrentUser(userDetails: UserDetails): ResponseEntity<UserProfileResponse> {
        val user = userService.getUserByEmail(userDetails.username)

        val response = UserProfileResponse(
            userId = user.id!!,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            phone = user.phone,
            emailVerified = user.emailVerified,
            phoneVerified = user.phoneVerified,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            lastLogin = user.lastLogin,
            preferences = emptyMap()
        )

        return ResponseEntity.ok(response)
    }

    @PutMapping("/me")
    fun updateProfile(
        userDetails: UserDetails,
        @Valid @RequestBody request: UserProfileUpdateRequest
    ): ResponseEntity<UserProfileResponse> {
        val user = userService.updateUser(
            userService.getUserByEmail(userDetails.username).id!!,
            request.firstName,
            request.lastName,
            request.phone
        )

        val response = UserProfileResponse(
            userId = user.id!!,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            phone = user.phone,
            emailVerified = user.emailVerified,
            phoneVerified = user.phoneVerified,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            lastLogin = user.lastLogin,
            preferences = emptyMap()
        )

        return ResponseEntity.ok(response)
    }

    @PutMapping("/me/password")
    fun changePassword(
        userDetails: UserDetails,
        @Valid @RequestBody request: PasswordChangeRequest
    ): ResponseEntity<PasswordChangeResponse> {
        userService.changePassword(
            userService.getUserByEmail(userDetails.username).id!!,
            request.currentPassword,
            request.newPassword
        )

        return ResponseEntity.ok(PasswordChangeResponse())
    }
}