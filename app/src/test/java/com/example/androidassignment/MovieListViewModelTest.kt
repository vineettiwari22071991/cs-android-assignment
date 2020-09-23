package com.example.androidassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.models.DatesX
import com.example.androidassignment.models.GetMovieService
import com.example.androidassignment.models.MovieModelNowPlaying
import com.example.androidassignment.models.MovieNowPlayingResult
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MovieListViewModelTest {

    @get:Rule
    var rule=InstantTaskExecutorRule()



    @Mock
     lateinit var getMovieService:GetMovieService

    @InjectMocks
    lateinit var movieViewmodel:MovieListViewModel

    private var testSingle: Single<MovieModelNowPlaying>?=null

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        getMovieService.getMovie()
        movieViewmodel=MovieListViewModel(getMovieService)

    }


    @Test
    fun getnowMovieSucess()
    {
        val dates= DatesX("2","1")
        val movie=MovieModelNowPlaying(dates,1, arrayListOf(),1,1)


        testSingle= Single.just(movie)

        `when`(getMovieService.getMovie()).thenReturn(testSingle)

        movieViewmodel.fetchNowPlayingMovieList()

        Assert.assertEquals(false, movieViewmodel.error.value)
    }

    @Test
    fun getnowMovieFail(){

        testSingle=Single.error(Throwable())

        `when`(getMovieService.getMovie()).thenReturn(testSingle)

        movieViewmodel.fetchNowPlayingMovieList()
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


