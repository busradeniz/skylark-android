package ostmodern.skylark.di;


import dagger.Module;
import dagger.Provides;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.ui.detail.SetDetailContract;
import ostmodern.skylark.ui.detail.SetDetailPresenter;
import ostmodern.skylark.ui.sets.SetListContract;
import ostmodern.skylark.ui.sets.SetListPresenter;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.SchedulerProvider;


@Module
public class MVPPresenterModule {

    @Provides
    SetDetailContract.Presenter setDetailPresenter(SetDetailContract.View view,
                                                   SkylarkRepository skylarkRepository,
                                                   SchedulerProvider schedulerProvider,
                                                   NetworkStatusProvider networkStatusProvider) {
        return new SetDetailPresenter(skylarkRepository, view, schedulerProvider,
                networkStatusProvider);
    }

    @Provides
    SetListContract.Presenter setListPresenter(SetListContract.View view,
                                               SkylarkRepository skylarkRepository,
                                               SchedulerProvider schedulerProvider,
                                               NetworkStatusProvider networkStatusProvider) {
        return new SetListPresenter(skylarkRepository, view, schedulerProvider,
                networkStatusProvider);
    }


}

