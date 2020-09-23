package com.example.androidassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.models.GetMovieService
import com.example.androidassignment.models.MovieDetaiModel
import com.example.androidassignment.models.MovieModelNowPlaying
import com.example.androidassignment.viewmodels.MovieDetailViewModel
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

class MovieDetailsViewModelTest {


    @get:Rule
    var rule= InstantTaskExecutorRule()

    @Mock
    lateinit var getMovieService: GetMovieService

    @InjectMocks
    lateinit var movieDetailViewModel: MovieDetailViewModel

    private var testSingle: Single<MovieDetaiModel>?=null


    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        getMovieService.getMovieDetail(605116)
        movieDetailViewModel= MovieDetailViewModel(getMovieService)

    }

    @Test
    fun movieDetailSucess()
    {

        val movieDetaiModel=MovieDetaiModel(false,"","",12000, arrayListOf(),""
        ,12,"","","","",12.3,"", arrayListOf(), arrayListOf(),
        "",12,123, arrayListOf(),"","","",false,12.2,1)

        testSingle= Single.just(movieDetaiModel)

        Mockito.`when`(getMovieService.getMovieDetail(605116)).thenReturn(testSingle)

        movieDetailViewModel.getMovieDetails(605116)

        Assert.assertEquals(false, movieDetailViewModel.error.value)

    }


    @Test
    fun movieDetailFail()
    {
        testSingle=Single.error(Throwable())

        Mockito.`when`(getMovieService.getMovieDetail(605116)).thenReturn(testSingle)

        movieDetailViewModel.getMovieDetails(605116)
        Assert.assertEquals(true, movieDetailViewModel.error.value)

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