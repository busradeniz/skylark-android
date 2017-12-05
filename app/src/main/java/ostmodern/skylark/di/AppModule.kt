package ostmodern.skylark.di


import android.app.Application
import android.arch.persistence.room.Room

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ostmodern.skylark.repository.SkylarkRepository
import ostmodern.skylark.repository.local.SkylarkDatabase
import ostmodern.skylark.repository.remote.SkylarkClient
import ostmodern.skylark.repository.remote.SkylarkService
import ostmodern.skylark.util.Logs
import ostmodern.skylark.util.NetworkStatusProvider
import ostmodern.skylark.util.ReleaseTree
import ostmodern.skylark.util.SchedulerProvider
import ostmodern.skylarkClient.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
class AppModule {

    /**
     * Provides skylark client for remote service calls.
     *
     * @param okHttpClient underlying http client implementation.
     * @param gson         serialisation library.
     * @param baseUrl      remote service url.
     * @return skylark client instance.
     */
    @Singleton
    @Provides
    fun provideSkylarkClient(okHttpClient: OkHttpClient,
                             gson: Gson,
                             @Named("baseUrl") baseUrl: String): SkylarkClient {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SkylarkClient::class.java)
    }

    /**
     * Provides [OkHttpClient] instance.
     * OkHttpClient handles underlying http requests.
     *
     * @return ok http client instance.
     */
    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { message -> Timber.tag(Logs.WIRE_LOG_TAG).d(message) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(logging)
        }

        return builder.build()
    }

    /**
     * Provides [Gson] instance.
     * Gson handles Json serialization and deserialization.
     *
     * @return gson instance.
     */
    @Singleton
    @Provides
    fun gson(): Gson {
        return GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create()
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    internal fun baseUrl(): String {
        return API_URL
    }

    @Singleton
    @Provides
    internal fun provideSkylarkService(skylarkClient: SkylarkClient): SkylarkService {
        return SkylarkService(skylarkClient)
    }

    @Singleton
    @Provides
    internal fun providesAppDatabase(app: Application): SkylarkDatabase {
        return Room.databaseBuilder(app,
                SkylarkDatabase::class.java, "skylark-db").build()
    }

    @Singleton
    @Provides
    internal fun providesSkylarkRepository(skylarkService: SkylarkService,
                                           skylarkDatabase: SkylarkDatabase): SkylarkRepository {
        return SkylarkRepository(skylarkService, skylarkDatabase)
    }


    @Singleton
    @Provides
    internal fun provideTimberTree(): Timber.Tree {
        return if (BuildConfig.DEBUG)
            Timber.DebugTree()
        else
            ReleaseTree()
    }

    @Singleton
    @Provides
    internal fun providesSchedulerProvider(): SchedulerProvider {
        return SchedulerProvider(AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Singleton
    @Provides
    internal fun provideNetworkStatusProvider(application: Application): NetworkStatusProvider {
        return NetworkStatusProvider(application)
    }

    companion object {

        private val API_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000"
        private val READ_TIMEOUT = 60
    }
}
