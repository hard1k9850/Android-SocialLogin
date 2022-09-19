package com.sociallogin.helper

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.sociallogin.model.AppleUserResponse
import com.sociallogin.utils.SocialLogin
import com.sociallogin.utils.SocialLoginConfig
import com.sociallogin.utils.SocialLoginException
import com.sociallogin.utils.SocialLoginType
import java.util.*

class AppleLogin : SocialLogin() {

    override fun login(config: SocialLoginConfig?) {
        initAppleSignIn(config)
    }

    private fun initAppleSignIn(config: SocialLoginConfig?) {
        val provider = OAuthProvider.newBuilder("apple.com")
        provider.scopes = arrayOf("email", "name").toMutableList()
        provider.addCustomParameter("locale", "en")

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.startActivityForSignInWithProvider(config?.getActivity()!!, provider.build())
            .addOnSuccessListener { authResult ->
                // Sign-in successful
                val user = authResult.user
                if (user?.providerData?.isNotEmpty()!!) {
                    val appleUserResponse = AppleUserResponse()
                    val userInfo = authResult.user?.providerData?.filter {
                        it.providerId.lowercase(Locale.getDefault()) == "firebase"
                    }!![0]
                    appleUserResponse.apply {
                        uid = userInfo.uid
                        displayName = userInfo.displayName
                        email = userInfo.email
                    }
                    config.getCallback()?.onLoginSuccess(appleUserResponse)
                }
            }
            .addOnFailureListener { e ->
                config.getCallback()
                    ?.onLoginFailed(
                        SocialLoginException(
                            e.message,
                            e,
                            SocialLoginType.Apple
                        )
                    )
            }
    }

    override fun logout(context: Context?) {
        FirebaseAuth.getInstance().signOut()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        config: SocialLoginConfig?
    ) {

    }
}