package ostmodern.skylark.common;

public interface BasePresenter {

    /**
     * Should be called after creation of presenter.
     * It's supposed to initialize all things to be done when view and presenter binded each other.
     */
    void subscribe();

    /**
     * Should be called before destroying presenter.
     * Clears presenter elements before unbinded view and presenter.
     */
    void unsubscribe();

}
