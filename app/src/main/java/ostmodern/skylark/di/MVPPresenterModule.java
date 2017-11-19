package ostmodern.skylark.di;


import dagger.Module;
import dagger.Provides;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.ui.detail.SetDetailContract;
import ostmodern.skylark.ui.detail.SetDetailPresenter;
import ostmodern.skylark.ui.sets.SetListContract;
import ostmodern.skylark.ui.sets.SetListPresenter;
import ostmodern.skylark.util.SchedulerProvider;


@Module
public class MVPPresenterModule {

    @Provides
    SetDetailContract.Presenter provideSetDetailPresenter(SetDetailContract.View view,
                                                          SkylarkRepository skylarkRepository,
                                                          SchedulerProvider schedulerProvider) {
        return new SetDetailPresenter(skylarkRepository, view, schedulerProvider);
    }

    @Provides
    SetListContract.Presenter provideSetListPresenter(SetListContract.View view,
                                                      SkylarkRepository skylarkRepository,
                                                      SchedulerProvider schedulerProvider) {
        return new SetListPresenter(skylarkRepository, view, schedulerProvider);
    }


}

