package ostmodern.skylark.di;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ostmodern.skylark.api.SkylarkClient;
import ostmodern.skylark.api.SkylarkService;
import ostmodern.skylark.util.ReleaseTree;
import ostmodern.skylark.util.SchedulerProvider;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.BuildConfig;
import timber.log.Timber;

@Module
public class AppModule {

    private static final String API_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000";
    private static final int READ_TIMEOUT = 60;

    @Singleton
    @Provides
    SkylarkClient provideSkylarkClient(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SkylarkClient.class);
    }

    @Singleton
    @Provides
    OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            System.out.println(message);
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        return builder.build();
    }

    @Singleton
    @Provides
    Gson gson() {
        return new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    SkylarkService provideSkylarkService(SkylarkClient skylarkClient) {
        return new SkylarkService(skylarkClient);
    }

    @Singleton
    @Provides
    Timber.Tree provideTimberTree() {
        return BuildConfig.DEBUG ? new Timber.DebugTree() : new ReleaseTree();
    }

    @Singleton
    @Provides
    SchedulerProvider providesSchedulerProvider() {
        return new SchedulerProvider(AndroidSchedulers.mainThread(), Schedulers.io());
    }
}
