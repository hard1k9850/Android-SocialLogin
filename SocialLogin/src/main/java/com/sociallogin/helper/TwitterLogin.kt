package com.sociallogin.helper

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.sociallogin.model.TwitterUserResponse
import com.sociallogin.utils.SocialLogin
import com.sociallogin.utils.SocialLoginConfig
import com.sociallogin.utils.SocialLoginException
import com.sociallogin.utils.SocialLoginType


class TwitterLogin : SocialLogin() {

    override fun login(config: SocialLoginConfig?) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val provider = OAuthProvider.newBuilder("twitter.com")

        firebaseAuth.signOut()
        firebaseAuth
            .startActivityForSignInWithProvider(
                config?.getActivity()!!,
                provider.build()
            )
            .addOnSuccessListener {
                if (it.user != null) {
                    val twitterUser = TwitterUserResponse()
                    val name = it.user?.displayName?.split(" ")?.toTypedArray()
                    twitterUser.apply {
                        displayName = it.user?.displayName ?: ""
                        firstName = name?.get(0) ?: ""
                        lastName = if (name?.size != null && name.size > 1) name[1] else ""
                        socialId = it.user?.uid ?: ""
                        email = it.user?.email ?: ""
                    }
                    config.getCallback()?.onLoginSuccess(twitterUser)
                }
            }
            .addOnFailureListener {
                config.getCallback()
                    ?.onLoginFailed(
                        SocialLoginException(
                            it.message ?: "Linkedin login failed",
                            SocialLoginType.Twitter
                        )
                    )
            }
    }

    override fun logout(context: Context?) {

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        config: SocialLoginConfig?
    ) {
        Log.d("Result", requestCode.toString())
    }
}