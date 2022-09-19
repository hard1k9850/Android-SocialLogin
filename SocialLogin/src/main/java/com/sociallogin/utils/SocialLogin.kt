package com.sociallogin.utils

import android.content.Context
import android.content.Intent

abstract class SocialLogin {

    abstract fun login(config: SocialLoginConfig?)

    abstract fun logout(context: Context?)

    abstract fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        config: SocialLoginConfig?
    )

}