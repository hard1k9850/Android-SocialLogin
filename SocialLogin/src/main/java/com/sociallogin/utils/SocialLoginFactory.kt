package com.sociallogin.utils

import com.sociallogin.helper.*

object SocialLoginFactory {
    fun build(loginType: SocialLoginType?): SocialLogin? {
        return when (loginType) {
            SocialLoginType.Facebook -> FacebookLogin()
            SocialLoginType.Google -> GoogleLogin()
            SocialLoginType.LinkedIn -> LinkedinLogin()
            SocialLoginType.Apple -> AppleLogin()
            SocialLoginType.Twitter -> TwitterLogin()
            else -> null
        }
    }
}