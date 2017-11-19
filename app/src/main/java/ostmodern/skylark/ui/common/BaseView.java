package ostmodern.skylark.ui.common;

public interface BaseView {

    /**
     * Hides notification view for given type.
     *
     * @param connectionNotification type of notification.
     */
    void hideNotificationView(ViewNotificationType connectionNotification);

    /**
     * Shows notification view for given type.
     *
     * @param connectionNotification type of notification.
     */
    void showNotificationView(ViewNotificationType connectionNotification);
}
