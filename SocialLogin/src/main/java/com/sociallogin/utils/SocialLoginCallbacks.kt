package com.sociallogin.utils

interface SocialLoginCallbacks {
    fun onLoginSuccess(data: Any)
    fun onLoginFailed(t: SocialLoginException)
}