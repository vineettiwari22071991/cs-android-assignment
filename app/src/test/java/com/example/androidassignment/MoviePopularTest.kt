package com.example.androidassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.models.*
import com.example.androidassignment.viewmodels.MovieListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MoviePopularTest {
    @get:Rule
    var rule= InstantTaskExecutorRule()



    @Mock
    lateinit var getMovieService: GetMovieService

    @InjectMocks
    lateinit var movieViewmodel: MovieListViewModel

    private var testSingle: Single<MoviePopularModel>?=null

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        getMovieService.getMovie()
        movieViewmodel= MovieListViewModel(getMovieService)

    }


    @Test
    fun getPopularMovieSucess()
    {
        val dates= Dates("2","1")
        val movie= MoviePopularModel(dates,1, arrayListOf(),1,1)


        testSingle= Single.just(movie)

        Mockito.`when`(getMovieService.getMoviePopular(1)).thenReturn(testSingle)

        movieViewmodel.fetchPopularMoiveList(1)

        Assert.assertEquals(false, movieViewmodel.error.value)
    }

    @Test
    fun getPopularMovieFail(){

        testSingle= Single.error(Throwable())

        Mockito.`when`(getMovieService.getMoviePopular(1)).thenReturn(testSingle)

        movieViewmodel.fetchPopularMoiveList(1)
        Assert.assertEquals(true, movieViewmodel.error.value)

    }





    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}