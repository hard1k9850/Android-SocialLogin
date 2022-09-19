package com.sociallogin.utils

class SocialLoginException : Exception {
    var loginType: SocialLoginType? = null

    constructor(message: String?, loginType: SocialLoginType) : super(message) {
        this.loginType = loginType
    }

    constructor(message: String?, cause: Throwable?, loginType: SocialLoginType) : super(
        message,
        cause
    ) {
        this.loginType = loginType
    }
}