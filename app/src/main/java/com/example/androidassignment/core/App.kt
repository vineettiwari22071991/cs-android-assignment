package com.example.androidassignment.core

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.androidassignment.di.ApplicationComponent
import com.example.androidassignment.di.DaggerRealComponent

import kotlin.reflect.KClass

open class App : Application() {

    open val component: ApplicationComponent by lazy {
        DaggerRealComponent.builder()
            .context(this)
            .build()
    }


}

val Context.component: ApplicationComponent
    get() = (this.applicationContext as App).component


fun <T>Fragment.getViewModel(type: KClass<T> ): ViewModel where T:ViewModel{
    val factory = this.context!!.component.viewModelFactory()
    return ViewModelProviders.of(this, factory)[type.java]
}
