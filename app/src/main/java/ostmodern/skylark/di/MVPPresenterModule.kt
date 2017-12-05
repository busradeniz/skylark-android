package ostmodern.skylark.di


import dagger.Module
import dagger.Provides
import ostmodern.skylark.repository.SkylarkRepository
import ostmodern.skylark.ui.detail.SetDetailContract
import ostmodern.skylark.ui.detail.SetDetailPresenter
import ostmodern.skylark.ui.sets.SetListContract
import ostmodern.skylark.ui.sets.SetListPresenter
import ostmodern.skylark.util.NetworkStatusProvider
import ostmodern.skylark.util.SchedulerProvider


@Module
class MVPPresenterModule {

    @Provides
    internal fun setDetailPresenter(view: SetDetailContract.View,
                                    skylarkRepository: SkylarkRepository,
                                    schedulerProvider: SchedulerProvider,
                                    networkStatusProvider: NetworkStatusProvider): SetDetailContract.Presenter {
        return SetDetailPresenter(skylarkRepository, view, schedulerProvider,
                networkStatusProvider)
    }

    @Provides
    internal fun setListPresenter(view: SetListContract.View,
                                  skylarkRepository: SkylarkRepository,
                                  schedulerProvider: SchedulerProvider,
                                  networkStatusProvider: NetworkStatusProvider): SetListContract.Presenter {
        return SetListPresenter(skylarkRepository, view, schedulerProvider,
                networkStatusProvider)
    }


}

