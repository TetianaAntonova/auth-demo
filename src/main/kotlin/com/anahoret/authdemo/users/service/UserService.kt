package com.anahoret.authdemo.users.service

import com.anahoret.authdemo.users.domain.User
import com.anahoret.authdemo.users.storage.UserEntity
import com.anahoret.authdemo.users.storage.UserRepository
import org.springframework.stereotype.Service

interface UserService {
    fun getByEmail(email: String): User?
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun getByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toUser()
    }

    private fun UserEntity.toUser(): User {
        return User(
            id = id!!,
            email = email,
            userName = userName,
            roles = roles.map { it.roleName },
        )
    }
}
