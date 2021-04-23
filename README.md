![banner](https://user-images.githubusercontent.com/30006970/115849118-ddafdc80-a424-11eb-80cc-5d52394ddd6c.png)

# Fork
Simple android client that uses **GitHub API** to demonstate one variation of Clean architecture approach. This example only uses handful of API calls because GitHub provides a lot more and implementing all of them would somewhat beat the purpose of the project.
This learning project is dedicated to all android developers who are trying different ways to build their app (in terms of architecture of course)

# What is and what isn't
Main purpose of the project is to demonstate how to break your application into several modules, how to use them, how to split work and via dependency injection and interfaces use functions, classes and their methods etc as sort of a black box. Given that GitHub provides variation of API calls, making entire application to support that, even though it's a good idea, would make this project take longer, and beyond its original scope.
So we only use some calls, and sacrifice some implementations for the sake of the scope of the project.

# Screens
As seen in preview above, we have following screens:
#### 1. Authentication screen (not in the preview because it's single button)
Authentication screen, given that it contains single button, will take you to your default browser where you'll login to your github account. We use OAUTH authentication in this app (more on this in installation section). When you successfully login, we save your access token to encrypted shared preferences and send you to main screen. From that moment on you'll only land on main screen on each app launch. We also use this access token to authorize all our API calls.
#### 2. Home screen
This screen, it's simple purpose is to fetch currently logged in user, and display two informations on the screen which user can open: see repositories and see organisations. These will lead to external browser to your profile following said url.
#### 3. Search screen
Here you simple input your query and we fetch whatever you searched for. We only fetch first 10 items, and we provide no pagination in this case. Again, on one hand it's limitations of this approach, on other it's scope. Purpose here was to provide option to query for something. Once you get result back (in this case list of repositories), you cna open their detail preview.
#### 4. Feed screen
Here currently logged in user will receive all events that are tied to them. That goes from people they follow or people having some interaction on their profile (if someone follows your, or watches your repository, for instance).
#### 5. Notifications screen
At the time of development, when OAUTH application was being created on GitHub, notifications scope was turned off. We have implementation for those, but this screen served as sort of 'error fallback' screen. We use lottie animation to indicate that something went wrong, ie maybe app doesn't contain scope, maybe there some other error, and this way user will for sure know that something went wrong.
#### 6. Profile screen
This screen is combination of two api calls. We fetch current user information (but you can build on that and store it locally, how likely user info will change?), and we fetch their top repositories with some basic information (no need to cover screen with so much info)
#### 7. Repository detail
User can either get here via search screen or profile screen. We, in a similar fashion to profile screen trigger two api calls. We get basic information about repository (or you can pass that data as a bundle when you navigate, hence no need to one api call), and we get events for that repository (who is watching, who is commiting, etc).

# Clean architecture
This project is loosely following clean acrhitecute approach as recommended by [Antonio Levia](https://antonioleiva.com/) in his article [Clean architecture for Android with Kotlin: a pragmatic approach for starters](https://antonioleiva.com/clean-architecture-android/). Idea is to split your app into several modules and via dependency injection and interfaces provide their usage (what we said in intorduction).
Modules are as follows:
##### 1. data
All data related, from repositories, api calls, mappers for our models, datasource (only its interface) is in here. Essentially core of the app is in this module
##### 2. domain
Our response and request models, as well as our mapping classes.
##### 3. usecases
Given we have many api calls, to handle them nicely, we split each call into its use-case. That way we can focus on single flow.
##### 4. app - standard
Here we provide implementation for our datasource (this can be local and/or remote); because we implement it in app module we can use android specific components (interface is in data module) so data module (or core) is still isolated, while specific client provides the implementation however they want.
And here are all UI parts (fragments, viewmodels, and adapters).

# Flow
Flow in this case is pretty simple:
api call -> datasource -> repository -> usecase -> viewmodel -> fragment

Since we, due to use-cases, only focus on one flow, following this won't be an issue. And we handle data however we want, so we know what we get back (for instance, in datasource, we might want to re-map our response to some other model because we won't use all that information).

# Install
Only main thing that needs configuration is authentication.
Firstly you need to [create OAUTH application](https://docs.github.com/en/developers/apps/creating-an-oauth-app) and once you do you'll get access to following information:
1. clientId
2. clientSecret
3. redirectUrl

Redirect url can be whatever you put in (whatever as in for testing purposes, for production callback url will be different), so it can be "youAppPackageName://callback" (eg: "airbnb://callback"). Remember what you select to be the scope of your application.

Once you have those 3 informations, add them into `AppConst.kt` file (or add them to wherever file you preffer).

Finally, update your manifest file; add intent filter into the activity that will run authentication. In this case, since we use single activity approach, it'll be `MainActivity`:

```
    `<intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data
            android:host="callback"
            android:scheme="airbnb" />
     </intent-filter>`
```

Now your app is set up, and only thing you need to do is to run it.

# Architecture

This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

# Contribute

If you want to contribute to this repository, you're always welcome!

# Contact

If you need any help, feel free to contact me: kenan.karic@outlook.com.

# License
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
