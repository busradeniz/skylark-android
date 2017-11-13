package ostmodern.skylark.di;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    private static final String API_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/";

    @Singleton
    @Provides
    SkylarkClient provideSkylarkClient() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SkylarkClient.class);
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
