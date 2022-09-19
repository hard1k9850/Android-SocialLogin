package com.socialloginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.sociallogin.SocialLoginHelper
import com.sociallogin.model.*
import com.sociallogin.utils.SocialLoginCallbacks
import com.sociallogin.utils.SocialLoginException
import com.sociallogin.utils.SocialLoginType

class LoginFragment : Fragment(), SocialLoginCallbacks {

    val socialLoginHelper = SocialLoginHelper.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        socialLoginHelper.init(
            requireActivity(), this, this,
            getString(R.string.linkedInApiKey),
            getString(R.string.linkedInSecretKey),
            getString(R.string.linkedInRedirectUrl)
        )

        view.findViewById<AppCompatButton>(R.id.buttonLoginWithTwitter).setOnClickListener {
            socialLoginHelper.login(SocialLoginType.Twitter)
        }

        view.findViewById<AppCompatButton>(R.id.buttonLoginWithFacebook).setOnClickListener {
            socialLoginHelper.login(SocialLoginType.Facebook)
        }

        view.findViewById<AppCompatButton>(R.id.buttonLoginWithGoogle).setOnClickListener {
            socialLoginHelper.login(SocialLoginType.Google)
        }

        view.findViewById<AppCompatButton>(R.id.buttonLoginWithApple).setOnClickListener {
            socialLoginHelper.login(SocialLoginType.Apple)
        }

        view.findViewById<AppCompatButton>(R.id.buttonLoginWithLinkedin).setOnClickListener {
            socialLoginHelper.login(SocialLoginType.LinkedIn)
        }
    }

    override fun onLoginSuccess(data: Any) {
        when (data) {
            is GoogleUserResponse -> {
                Log.d("User-Google", data.displayName ?: "")
            }
            is FacebookUserResponse -> {
                Log.d("User-Facebook", data.username ?: "")
            }
            is LinkedinUserResponse -> {
                Log.d("User-Linkedin", data.firstName ?: "")
            }
            is TwitterUserResponse -> {
                Log.d("User-Twitter", data.displayName ?: "")
            }
            is AppleUserResponse -> {
                Log.d("User-Apple", data.displayName ?: "")
            }
        }
    }

    override fun onLoginFailed(t: SocialLoginException) {
        Log.d("SocialLoginType", t.loginType.toString())
        t.message?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
    }

    fun setOnActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        socialLoginHelper.setLinkedinActivityResult(requestCode, resultCode, data)
    }

}