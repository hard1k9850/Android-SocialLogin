package com.sociallogin.helper

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.sociallogin.model.GoogleUserResponse
import com.sociallogin.utils.*

class GoogleLogin : SocialLogin() {

    override fun login(config: SocialLoginConfig?) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        val signInClient = GoogleSignIn.getClient(config?.getActivity()!!, gso)

        signInClient.signOut()
        val signInIntent: Intent = signInClient.signInIntent

        if (config.getActivityResult() == null) {
            startActivityForResult(
                config.getActivity()!!,
                signInIntent,
                Constants.GOOGLE_LOGIN_REQUEST,
                null
            )
        } else {
            config.getActivityResult()?.launch(signInIntent)
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
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        if (task.isSuccessful) {
            Log.d("Google login", "Success")
            val acct = task.result
            val googleUser: GoogleUserResponse = populateGoogleUser(acct)
            config?.getCallback()?.onLoginSuccess(googleUser)
        } else {
            config!!.getCallback()
                ?.onLoginFailed(
                    SocialLoginException(
                        "Google login failed",
                        SocialLoginType.Google
                    )
                )
        }
    }

    private fun populateGoogleUser(account: GoogleSignInAccount): GoogleUserResponse {
        val googleUser = GoogleUserResponse()
        googleUser.apply {
            displayName = account.displayName
            if (account.photoUrl != null)
                photoUrl = account.photoUrl.toString()
            email = account.email
            idToken = account.idToken
            userId = account.id
            firstName = account.givenName
            lastName = account.familyName
            username = account.account?.name ?: ""
        }
        return googleUser
    }
}