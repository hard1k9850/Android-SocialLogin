package com.sociallogin

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.sociallogin.utils.*

class SocialLoginHelper : SocialLoginCallbacks {

    private var socialLogin: SocialLogin? = null
    private var config: SocialLoginConfig? = null
    private var callBackSocialLogin: SocialLoginCallbacks? = null

    companion object {
        @Volatile
        private lateinit var instance: SocialLoginHelper

        fun getInstance(): SocialLoginHelper {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = SocialLoginHelper()
                }
                return instance
            }
        }
    }

    /*If you are using google login*/
    fun init(
        activity: Activity?, callBackSocialLogin: SocialLoginCallbacks?,
        activityResult: ActivityResultLauncher<Intent>?, fragment: Fragment,
        apiKey: String = "",
        mSecretKey: String = "",
        mRedirectUrl: String = ""
    ) {
        config = SocialLoginConfig(activity, this, activityResult, fragment)
        if (apiKey.isNotEmpty() && mSecretKey.isNotEmpty() && mRedirectUrl.isNotEmpty()) {
            config?.apply {
                setLinkedinKeys(
                    apiKey,
                    mSecretKey,
                    mRedirectUrl
                )
            }
        }
        this.callBackSocialLogin = callBackSocialLogin
    }

    /*If you are not using google login*/
    fun init(
        activity: Activity?, callBackSocialLogin: SocialLoginCallbacks?, fragment: Fragment,
        apiKey: String = "",
        mSecretKey: String = "",
        mRedirectUrl: String = ""
    ) {
        config = SocialLoginConfig(activity, this, fragment)
        if (apiKey.isNotEmpty() && mSecretKey.isNotEmpty() && mRedirectUrl.isNotEmpty()) {
            config?.apply {
                setLinkedinKeys(
                    apiKey,
                    mSecretKey,
                    mRedirectUrl
                )
            }
        }
        this.callBackSocialLogin = callBackSocialLogin
    }

    fun login(loginType: SocialLoginType?) {
        socialLogin = SocialLoginFactory.build(loginType)
        if (config != null)
            socialLogin?.login(config)
    }

    fun setGoogleOnActivityResult(mResultCode: Int, data: Intent?) {
        socialLogin?.onActivityResult(
            Constants.GOOGLE_LOGIN_REQUEST, mResultCode, data, config
        )
    }

    fun setLinkedinActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        socialLogin?.onActivityResult(requestCode, resultCode, data, config)
    }

    override fun onLoginSuccess(data: Any) {
        callBackSocialLogin?.onLoginSuccess(data)
    }

    override fun onLoginFailed(t: SocialLoginException) {
        callBackSocialLogin?.onLoginFailed(t)
    }

}