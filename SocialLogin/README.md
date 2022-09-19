# Social Login

## What's in the box

- The login framework for your app
- Implementation of Facebook, Google, Apple, LinkedIn, and Twitter login

## Setup

### 1. Include in your project

#### Using Module

Download this SocialLogin repo and copy the library folder to your project, then you need to add the
following path to your app's build.gradle.

```
implementation project(path: ':SocialLogin')
```

### 2. Usage

- Add `SocialLoginHelper` class to your application component and use it in fragment for
  configuration & login.

- First step in configuring the SocialLoginHelper to initialize
    ```
    socialLoginHelper.init(
        requireActivity()       //Activity-Required,
        this                    //Callback -Required, 
        activityResultLauncher  //ActivityResultLauncher -Required only when using google login for get result,
        this                    //Fragment -Required,
        apiKey                  //Api Key -Required only when using linked login,
        mSecretKey              //Secret Key -Required only when using linked login,
        mRedirectUrl            //Redirect Url -Required only when using linked login
    )
    ```

- Next step is to override the onActivityResult in your Activity for linkedin login and
  ActivityResultLauncher for google login.

  ##### For Google login add it in your fragment

    ```
    val googleLoginResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) 
                socialLoginHelper.setGoogleOnActivityResult(it.resultCode, it.data)
    }
    ```
  ##### For Linked login

    ```
    /* Add it in your parent activity */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        val yourFragmentName: YourFragmentName? =
            supportFragmentManager.findFragmentByTag("YOUR_TAG") as YourFragmentName?
        if (yourFragmentName != null && yourFragmentName.isVisible) 
              yourFragmentName.setActivityResult(requestCode, resultCode, data)
    }

    /* Add it in your fragment */
    fun setOnActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        socialLoginHelper.setLinkedinActivityResult(requestCode, resultCode, data)
    }
    ```

- Final step is to call the login method when user clicks on the login button

  ###### Login Types : Facebook, Google, LinkedIn, Twitter, Apple

    ```
    socialLoginHelper.login(SocialLoginType.Facebook)
    ```

##### That's it!!

##### Once the login succeeds, onLoginSuccess(data : Any) return below below objects according to it's type

```
1. Facebook - FacebookUserResponse
2. Google - GoogleUserResponse
3. LinkedIn - LinkedinUserResponse
4. Twitter - TwitterUserResponse
5. Apple - AppleUserResponse
Example,
when (data) {
    is GoogleUserResponse -> {
        Log.d("Google-Login","Success")
    }
}
```

##### When logging in fails, onLoginFailed(t: SocialLoginException)

- Return exception message and login type(t.loginType)

## Add some plugins and ids

```
When using Google Login
- Enable Google service in project's Gradle : classpath 'com.google.gms:google-services:{version}'
- Add plugin  {id 'com.google.gms.google-services'} in you app-level build.Gradle
When using Facebook login 
- Add Facebook app id to your manifest 
    Example,
    <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id"/>

```

## Included Libraries

The following third-party libraries were used in this framework.

- Facebook SDK
- Google Play Services - Auth
- Firebase Auth
- Linkedin(Third Party SDK)