package com.example.androidassignment.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidassignment.R
import com.example.androidassignment.models.GetMovieService
import com.example.androidassignment.utils.ActivityService
import com.example.androidassignment.utils.Navigator
import com.example.androidassignment.viewmodels.MovieDetailViewModel
import com.example.androidassignment.viewmodels.MovieListViewModel
import dagger.*
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.reflect.KClass


@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(
    val value: KClass<out ViewModel>
)


@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ApiBaseUrl

interface ApplicationComponent {

    fun activityService(): ActivityService

    fun viewModelFactory(): ViewModelProvider.Factory

}


@Singleton
@Component(modules = [ApiModule::class,ApplicationModule::class,ViewModelModule::class])


interface RealComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): RealComponent
    }
}



@Module
object ApplicationModule {

    @Provides
    @ApiBaseUrl
    @JvmStatic
    fun apiBaseUrl(context: Context): String = context.getString(R.string.api_base_url)

    @Provides
    @Singleton
    @JvmStatic
    fun activityService(): ActivityService = ActivityService()


    @Provides
    @Singleton
    @JvmStatic
    fun navigator(activityService: ActivityService): Navigator {

        return Navigator(R.id.navigation, activityService)
    }


    @Singleton
    @JvmStatic
    @Provides
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory =
        ViewModelFactory(providers)


}


@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun MovieListViewModel(viewModel: MovieListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun Movie_Detail_ViewModel(viewModel: MovieDetailViewModel): ViewModel



}

@Module
object ApiModule {

    @Provides
    @Singleton
    @JvmStatic
    fun okHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }


    @Provides
    @Singleton
    @JvmStatic
    fun retrofit(@ApiBaseUrl apiBaseUrl: String, OkHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient)
            .build()
    }


    @Provides
    @Singleton
    @JvmStatic
    fun MovieService(retrofit: Retrofit): GetMovieService {
        return retrofit.create(GetMovieService::class.java)
    }


}