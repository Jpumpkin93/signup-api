package com.jpumpkin.signupapi.entity.authCode

import com.jpumpkin.signupapi.domain.AuthCode
import com.jpumpkin.signupapi.entity.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated


@Entity
class AuthCodeJpaEntity(
    val mobileNumber: String,
    val code: Int,
    @Enumerated(EnumType.STRING)
    val purpose: Purpose,
    val isEnabled: Boolean
) : BaseEntity() {
    enum class Purpose {
        SIGN_UP,
        PASSWORD_UPDATE
    }
}