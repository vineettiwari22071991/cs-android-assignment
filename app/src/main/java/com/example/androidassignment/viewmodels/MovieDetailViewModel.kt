package com.example.androidassignment.viewmodels


import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.adapter.GenresAdapter
import com.example.androidassignment.models.GetMovieService
import com.example.androidassignment.models.MovieDetaiModel
import com.example.androidassignment.models.MovieModelNowPlaying
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieService: GetMovieService
):BaseViewModel() {

    val movieDetail=MutableLiveData<MovieDetaiModel>()
    val error=MutableLiveData<Boolean>()
     fun getMovieDetails(id:Int)
    {
        disposeOnClear.add(

            movieService.getMovieDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<MovieDetaiModel>()
                {
                    override fun onSuccess(value: MovieDetaiModel?) {

                        movieDetail.value=value
                        error.value=false
                    }

                    override fun onError(e: Throwable?) {
                        error.value=true
                    }

                })

        )


    }

}