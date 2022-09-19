package com.sociallogin.helper

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.shantanudeshmukh.linkedinsdk.LinkedInBuilder
import com.shantanudeshmukh.linkedinsdk.helpers.LinkedInUser
import com.sociallogin.model.LinkedinUserResponse
import com.sociallogin.utils.*


class LinkedinLogin : SocialLogin() {

    /**
     * 31/05 - Here, We use third party library
     * that does not supports for "registerForActivityResult" so therefore
     * we are using "onActivityResult" for linkedin
     */
    override fun login(config: SocialLoginConfig?) {
        if (config?.mClientId == null
            || config.mSecretKey == null
            || config.mRedirectUrl == null
        ) {
            config?.getCallback()?.onLoginFailed(
                SocialLoginException(
                    "Please add keys for linkedin authentication",
                    SocialLoginType.LinkedIn
                )
            )
            return
        }

        LinkedInBuilder.getInstance(config.getActivity()!!)
            .setClientID(config.mClientId)
            .setClientSecret(config.mSecretKey)
            .setRedirectURI(config.mRedirectUrl)
            .authenticate(Constants.LINKEDIN_LOGIN_REQUEST)
    }

    override fun logout(context: Context?) {

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        config: SocialLoginConfig?
    ) {
        if (requestCode == Constants.LINKEDIN_LOGIN_REQUEST && data != null
            && resultCode == RESULT_OK
        ) {
            //Successfully signed in
            val user = data.getParcelableExtra<LinkedInUser>("social_login")
            if (user != null) {
                val linkedInUserResponse = LinkedinUserResponse()
                linkedInUserResponse.apply {
                    id = user.id
                    accessToken = user.accessToken
                    accessTokenExpiry = user.accessTokenExpiry
                    email = user.email
                    firstName = user.firstName
                    lastName = user.lastName
                    profileUrl = user.profileUrl
                }
                config?.getCallback()?.onLoginSuccess(linkedInUserResponse)
            }
        } else {
            config?.getCallback()?.onLoginFailed(
                SocialLoginException(
                    "LinkedIn Login Failed",
                    SocialLoginType.LinkedIn
                )
            )
        }
    }


}