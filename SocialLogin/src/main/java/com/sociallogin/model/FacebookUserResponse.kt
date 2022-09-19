package com.sociallogin.model

import com.facebook.AccessToken

class FacebookUserResponse {
    var accessToken: AccessToken? = null
    var userId: String? = null
    var username: String? = null
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var email: String? = null
    var birthday: String? = null
    var gender: String? = null
    var profileLink: String? = null

}