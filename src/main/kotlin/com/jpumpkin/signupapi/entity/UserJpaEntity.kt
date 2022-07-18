package com.jpumpkin.signupapi.entity

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