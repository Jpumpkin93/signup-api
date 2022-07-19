package com.jpumpkin.signupapi.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MobileNumberUtilTest {

    @Test
    fun `핸드폰 번호(11자리) 포맷을 변경한다`() {
        val koreaMobileNumber = "01012345678"

        val formattedMobileNumber = MobileNumberUtil.formatMobileNumber(koreaMobileNumber)

        assertThat(formattedMobileNumber).isEqualTo("010-1234-5678")
    }

    @Test
    fun `핸드폰 번호(10자리) 포맷을 변경한다`() {
        val koreaMobileNumber = "0101234567"

        val formattedMobileNumber = MobileNumberUtil.formatMobileNumber(koreaMobileNumber)

        assertThat(formattedMobileNumber).isEqualTo("010-123-4567")
    }
}