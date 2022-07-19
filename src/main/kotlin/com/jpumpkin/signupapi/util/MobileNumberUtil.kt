package com.jpumpkin.signupapi.util

import com.google.i18n.phonenumbers.PhoneNumberUtil

object MobileNumberUtil {

    private const val KOREA_REGION = "KR"
    private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

    fun formatMobileNumber(mobileNumber: String): String {
        phoneNumberUtil.parse(mobileNumber, KOREA_REGION)
            .run { return phoneNumberUtil.format(this, PhoneNumberUtil.PhoneNumberFormat.NATIONAL) }
    }

}