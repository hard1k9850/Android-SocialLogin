package com.socialloginapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sociallogin.utils.Constants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == Constants.GOOGLE_LOGIN_REQUEST ||
                    requestCode == Constants.LINKEDIN_LOGIN_REQUEST)
        ) {
            val socialLoginFragment: LoginFragment? =
                supportFragmentManager.findFragmentByTag("SocialLogin") as LoginFragment?
            if (socialLoginFragment != null && socialLoginFragment.isVisible) {
                socialLoginFragment.setOnActivityResult(requestCode, resultCode, data)
            }
        }
    }
}