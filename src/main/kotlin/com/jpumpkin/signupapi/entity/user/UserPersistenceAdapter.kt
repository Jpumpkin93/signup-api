package com.jpumpkin.signupapi.entity.user

import com.jpumpkin.signupapi.domain.User
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
}