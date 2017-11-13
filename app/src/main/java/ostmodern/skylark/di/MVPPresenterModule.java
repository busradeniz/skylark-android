package ostmodern.skylark.di;


import dagger.Module;
import dagger.Provides;
import ostmodern.skylark.api.SkylarkService;
import ostmodern.skylark.ui.SetListContract;
import ostmodern.skylark.ui.SetListPresenter;
import ostmodern.skylark.util.SchedulerProvider;


@Module
public class MVPPresenterModule {

    @Provides
    SetListContract.Presenter provideSetListPresenter(SetListContract.View view,
                                                      SkylarkService skylarkService,
                                                      SchedulerProvider schedulerProvider) {
        return new SetListPresenter(skylarkService, view, schedulerProvider);
    }
}

