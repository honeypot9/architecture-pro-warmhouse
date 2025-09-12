package yandex.practicum.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import yandex.practicum.entity.User
import yandex.practicum.repository.UserRepository
import java.util.*
import org.springframework.transaction.annotation.Transactional
import yandex.practicum.entity.UserStatus

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    @Transactional
    fun createUser(email: String, password: String, firstName: String, lastName: String, phone: String?): User {
        if (userRepository.findByEmail(email).isPresent) {
            throw IllegalArgumentException("User with this email already exists")
        }

        val encodedPassword = passwordEncoder.encode(password)
        val user = User(email, encodedPassword, firstName, lastName, phone)
        return userRepository.save(user)
    }

    fun getUserById(userId: UUID): User {
        return userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow { IllegalArgumentException("User not found") }
    }

    @Transactional
    fun updateUser(userId: UUID, firstName: String?, lastName: String?, phone: String?): User {
        val user = getUserById(userId)
        firstName?.let { user.firstName = it }
        lastName?.let { user.lastName = it }
        phone?.let { user.phone = it }
        user.updatedAt = java.time.LocalDateTime.now()
        return userRepository.save(user)
    }

    @Transactional
    fun changePassword(userId: UUID, currentPassword: String, newPassword: String): User {
        val user = getUserById(userId)

        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw IllegalArgumentException("Current password is incorrect")
        }

        user.password = passwordEncoder.encode(newPassword)
        user.updatedAt = java.time.LocalDateTime.now()
        return userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found with email: $username") }

        if (user.status == UserStatus.BLOCKED) {
            throw UsernameNotFoundException("User account is blocked")
        }

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            user.roles.map { org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_$it") }
        )
    }

    fun authenticateUser(email: String, password: String): User {
        val user = getUserByEmail(email)

        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        if (user.status == UserStatus.BLOCKED) {
            throw IllegalArgumentException("User account is blocked")
        }

        user.lastLogin = java.time.LocalDateTime.now()
        return userRepository.save(user)
    }
}