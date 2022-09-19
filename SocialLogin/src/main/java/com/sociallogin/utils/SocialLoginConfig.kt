package com.sociallogin.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

class SocialLoginConfig {

    private var activity: Activity? = null
    private var callback: SocialLoginCallbacks? = null
    private var activityResult: ActivityResultLauncher<Intent>? = null
    private var fragment: Fragment? = null

    constructor(
        activity: Activity?, callback: SocialLoginCallbacks?, fragment: Fragment
    ) {
        this.activity = activity
        this.callback = callback
        this.fragment = fragment
    }

    /**
     * @Krunal Hyperlink - If you are using google login then need to pass
     * a activity result launcher and must be initialised it in fragment
     */
    constructor(
        activity: Activity?, callback: SocialLoginCallbacks?,
        activityResult: ActivityResultLauncher<Intent>?, fragment: Fragment
    ) {
        this.activity = activity
        this.callback = callback
        this.fragment = fragment
        this.activityResult = activityResult
    }

    fun getActivityResult(): ActivityResultLauncher<Intent>? {
        return activityResult
    }

    fun getActivity(): Activity? {
        return activity
    }

    fun getCallback(): SocialLoginCallbacks? {
        return callback
    }

    fun getFragment(): Fragment? {
        return fragment
    }

    fun getDefaultFacebookPermissions(): ArrayList<String> {
        val defaultPermissions = ArrayList<String>()
        defaultPermissions.add("public_profile")
        defaultPermissions.add("email")
        defaultPermissions.add("user_birthday")
        return defaultPermissions
    }

    /*LinkedIn*/
    var mClientId: String? = null
    var mSecretKey: String? = null
    var mRedirectUrl: String? = null

    fun setLinkedinKeys(
        mClientId: String?,
        mSecretKey: String?,
        mRedirectUrl: String?
    ) {
        this.mClientId = mClientId
        this.mSecretKey = mSecretKey
        this.mRedirectUrl = mRedirectUrl
    }
}