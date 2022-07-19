package com.jpumpkin.signupapi.entity.user

import com.jpumpkin.signupapi.domain.User
import com.jpumpkin.signupapi.extension.findByIdOrThrow
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component


@Component
class UserPersistenceAdapter(
    val userRepository: UserRepository,
    val modelMapper: ModelMapper
): UserPort {

    override fun save(user: User): UserJpaEntity =
        userRepository.save(
            modelMapper.map(user, UserJpaEntity::class.java)
        )

    override fun existsByMobileNumber(mobileNumber: String): Boolean =
        userRepository.existsByMobileNumber(mobileNumber)

    override fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)

    override fun findByMobileNumber(mobileNumber: String): User? {
        val userJpaEntity = userRepository.findByMobileNumber(mobileNumber) ?: return null
        return modelMapper.map(userJpaEntity, User::class.java)
    }

    override fun findByEmail(email: String): User? {
        val userJpaEntity = userRepository.findByEmail(email) ?: return null
        return modelMapper.map(userJpaEntity, User::class.java)
    }

    override fun findById(userId: Long): User =
        modelMapper.map(userRepository.findByIdOrThrow(userId), User::class.java)
}