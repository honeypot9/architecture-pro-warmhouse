package yandex.practicum.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yandex.practicum.dto.*
import yandex.practicum.service.UserService

@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody request: UserRegistrationRequest): ResponseEntity<UserRegistrationResponse> {
        val user = userService.createUser(
            request.email,
            request.password,
            request.firstName,
            request.lastName,
            request.phone
        )

        val response = UserRegistrationResponse(
            userId = user.id!!,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            status = user.status.toString()
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): ResponseEntity<UserLoginResponse> {
        val user = userService.authenticateUser(request.email, request.password)

        val response = UserLoginResponse(
            user = UserInfo(
                userId = user.id!!,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName,
                roles = user.roles.toList()
            )
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<LogoutResponse> {
        return ResponseEntity.ok(LogoutResponse())
    }
}