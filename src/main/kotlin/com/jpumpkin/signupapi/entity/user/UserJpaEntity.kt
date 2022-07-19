package com.jpumpkin.signupapi.entity.user

import com.jpumpkin.signupapi.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity


@Entity
class UserJpaEntity(
    val nickName: String,
    val password: String,
    val name: String,
    @Column(unique = true)
    val email: String,
    @Column(unique = true)
    val mobileNumber: String
): BaseEntity()