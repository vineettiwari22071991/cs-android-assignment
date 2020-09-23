package com.example.androidassignment.viewmodels
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel:ViewModel() {

     val disposeOnClear = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposeOnClear.clear()
    }

    fun Disposable.untilCleared() =
        disposeOnClear.add(this)

}