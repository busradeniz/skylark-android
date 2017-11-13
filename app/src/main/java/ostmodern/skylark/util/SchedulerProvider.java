package ostmodern.skylark.util;

import io.reactivex.Scheduler;

/**
 * Provides schedulers for RX.
 */
public class SchedulerProvider {

    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    /**
     * Constructor.
     *
     * @param uiScheduler scheduler for andorid main thread.
     * @param ioScheduler scheduler for io threads.
     */
    public SchedulerProvider(Scheduler uiScheduler, Scheduler ioScheduler) {
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    public Scheduler getUiScheduler() {
        return uiScheduler;
    }

    public Scheduler getIoScheduler() {
        return ioScheduler;
    }
}
