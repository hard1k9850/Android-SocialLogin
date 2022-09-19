package com.sociallogin.helper

import android.content.Context
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk.setAutoLogAppEventsEnabled
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.sociallogin.model.FacebookUserResponse
import com.sociallogin.utils.*
import org.json.JSONException
import org.json.JSONObject

class FacebookLogin : SocialLogin() {

    override fun login(config: SocialLoginConfig?) {
        setAutoLogAppEventsEnabled(true)

        val callbackManager = create()
        val loginManager = LoginManager.getInstance()
        config?.let {
            loginManager.logInWithReadPermissions(
                it.getFragment()!!,
                callbackManager,
                it.getDefaultFacebookPermissions()
            )
        }
        loginManager.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    config?.getCallback()?.onLoginFailed(
                        SocialLoginException(
                            "User cancelled the login request",
                            SocialLoginType.Facebook
                        )
                    )
                }

                override fun onError(error: FacebookException) {
                    config?.getCallback()?.onLoginFailed(
                        SocialLoginException(
                            error.message,
                            error,
                            SocialLoginType.Facebook
                        )
                    )
                }

                override fun onSuccess(result: LoginResult) {
                    val request = GraphRequest.newMeRequest(
                        result.accessToken
                    ) { jsonObject, _ ->
                        val facebookUser: FacebookUserResponse = populateFacebookUser(
                            jsonObject!!,
                            result.accessToken
                        )
                        config?.getCallback()?.onLoginSuccess(facebookUser)
                    }
                    request.executeAsync()
                }

            }
        )
    }

    override fun logout(context: Context?) {
        LoginManager.getInstance().logOut()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        config: SocialLoginConfig?
    ) {

    }

    fun populateFacebookUser(
        jsonObject: JSONObject,
        accessToken: AccessToken?
    ): FacebookUserResponse {
        val facebookUser = FacebookUserResponse()
        facebookUser.accessToken = accessToken
        try {
            if (jsonObject.has(Constants.FacebookFields.EMAIL))
                facebookUser.email = jsonObject.getString(
                    Constants.FacebookFields.EMAIL
                )

            if (jsonObject.has(Constants.FacebookFields.BIRTHDAY))
                facebookUser.birthday = jsonObject.getString(
                    Constants.FacebookFields.BIRTHDAY
                )

            if (jsonObject.has(Constants.FacebookFields.GENDER)) {
                facebookUser.gender = jsonObject.getString(Constants.FacebookFields.GENDER)
            }
            if (jsonObject.has(Constants.FacebookFields.LINK))
                facebookUser.profileLink = jsonObject.getString(
                    Constants.FacebookFields.LINK
                )

            if (jsonObject.has(Constants.FacebookFields.ID))
                facebookUser.userId = jsonObject.getString(
                    Constants.FacebookFields.ID
                )

            if (jsonObject.has(Constants.FacebookFields.NAME))
                facebookUser.username = jsonObject.getString(
                    Constants.FacebookFields.NAME
                )

            if (jsonObject.has(Constants.FacebookFields.FIRST_NAME))
                facebookUser.firstName =
                    jsonObject.getString(Constants.FacebookFields.FIRST_NAME)

            if (jsonObject.has(Constants.FacebookFields.MIDDLE_NAME))
                facebookUser.middleName =
                    jsonObject.getString(Constants.FacebookFields.MIDDLE_NAME)

            if (jsonObject.has(Constants.FacebookFields.LAST_NAME))
                facebookUser.lastName = jsonObject.getString(
                    Constants.FacebookFields.LAST_NAME
                )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return facebookUser
    }
}