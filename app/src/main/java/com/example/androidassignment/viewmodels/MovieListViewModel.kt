package com.example.androidassignment.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidassignment.models.GetMovieService
import com.example.androidassignment.models.MovieModelNowPlaying
import com.example.androidassignment.models.MoviePopularModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val movieService:GetMovieService
):BaseViewModel() {



    val moviedata= MutableLiveData<MovieModelNowPlaying>()

    val moviePopulardata=MutableLiveData<MoviePopularModel>()
    val error=MutableLiveData<Boolean>()


    fun fetchNowPlayingMovieList()
    {
        disposeOnClear.add(

            movieService.getMovie()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<MovieModelNowPlaying>()
                {
                    override fun onSuccess(value: MovieModelNowPlaying?) {

                        moviedata.value=value
                        error.value=false
                    }

                    override fun onError(e: Throwable?) {
                        error.value=true
                    }

                })

        )


    }


    fun fetchPopularMoiveList(count:Int)
    {

        disposeOnClear.add(

            movieService.getMoviePopular(count)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<MoviePopularModel>()
                {
                    override fun onSuccess(value: MoviePopularModel?) {
                       moviePopulardata.value=value
                        error.value=false
                    }

                    override fun onError(e: Throwable?) {
                        error.value=true
                    }

                })
        )


    }

}