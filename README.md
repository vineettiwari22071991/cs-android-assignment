# cs-android-assignment
About Project:
This Project shows a list of movies into two categories.
Horizontal view: Shows playing now movie list.
Vertical view: Shows the most popular movie which uses pagination.

And if users click on a particular movie item it opens a Transparent Dialog
which shows movie details with an overview and genres.

Architecture:
we have used MVVM architecture with RxJava, Dagger 2, for Network call, we are using Retrofit2, and for image Glide
below are the dependencies which we are using:
//Retrofit
implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
implementation "com.squareup.okhttp3:logging-interceptor:$okHttpLogging"
implementation "com.squareup.okhttp3:okhttp:4.2.1"

//RXjava
implementation "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
implementation "io.reactivex.rxjava2:rxandroid:$rxJavaVersion"

//Dagger
implementation "com.google.dagger:dagger:$daggerVersion"
implementation "com.google.dagger:dagger-android-support:$daggerVersion"
kapt "com.google.dagger:dagger-compiler:$daggerVersion"
kapt "com.google.dagger:dagger-android-processor:$daggerVersion"


//Navigator
implementation "android.arch.navigation:navigation-fragment-ktx:$nav"
implementation "android.arch.navigation:navigation-ui-ktx:$nav"

//Glide
implementation "com.github.bumptech.glide:glide:$glideVersion"


ScreenShot:
MainScreen
![alt text](https://github.com/vineettiwari22071991/cs-android-assignment/blob/master/mainScreen.png?raw=true)

Detail Screen
![alt text](https://github.com/vineettiwari22071991/cs-android-assignment/blob/master/Dialog.png?raw=true)



Note:
In mainScreen, I have not to get the runtime key as used static string
"1h 45m"

