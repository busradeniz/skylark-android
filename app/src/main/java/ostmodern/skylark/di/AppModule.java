package ostmodern.skylark.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.repository.local.SkylarkDatabase;
import ostmodern.skylark.repository.remote.SkylarkClient;
import ostmodern.skylark.repository.remote.SkylarkService;
import ostmodern.skylark.util.Logs;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.ReleaseTree;
import ostmodern.skylark.util.SchedulerProvider;
import ostmodern.skylarkClient.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class AppModule {

    private static final String API_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000";
    private static final int READ_TIMEOUT = 60;

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
    public SkylarkClient provideSkylarkClient(OkHttpClient okHttpClient,
                                              Gson gson,
                                              @Named("baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SkylarkClient.class);
    }

    /**
     * Provides {@link OkHttpClient} instance.
     * OkHttpClient handles underlying http requests.
     *
     * @return ok http client instance.
     */
    @Singleton
    @Provides
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                    message -> Timber.tag(Logs.WIRE_LOG_TAG).d(message));
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(logging);
        }

        return builder.build();
    }

    /**
     * Provides {@link Gson} instance.
     * Gson handles Json serialization and deserialization.
     *
     * @return gson instance.
     */
    @Singleton
    @Provides
    public Gson gson() {
        return new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    String baseUrl() {
        return API_URL;
    }

    @Singleton
    @Provides
    SkylarkService provideSkylarkService(SkylarkClient skylarkClient) {
        return new SkylarkService(skylarkClient);
    }

    @Singleton
    @Provides
    SkylarkDatabase providesAppDatabase(Application app) {
        return Room.databaseBuilder(app,
                SkylarkDatabase.class, "skylark-db").build();
    }

    @Singleton
    @Provides
    SkylarkRepository providesSkylarkRepository(SkylarkService skylarkService,
                                                SkylarkDatabase skylarkDatabase) {
        return new SkylarkRepository(skylarkService, skylarkDatabase);
    }


    @Singleton
    @Provides
    Timber.Tree provideTimberTree() {
        return BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new ReleaseTree();
    }

    @Singleton
    @Provides
    SchedulerProvider providesSchedulerProvider() {
        return new SchedulerProvider(AndroidSchedulers.mainThread(), Schedulers.io());
    }

    @Singleton
    @Provides
    NetworkStatusProvider provideNetworkStatusProvider(Application application) {
        return new NetworkStatusProvider(application);
    }
}
