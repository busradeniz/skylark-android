package ostmodern.skylark.ui.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ostmodern.skylarkClient.R;
import timber.log.Timber;


public class NotificationView extends LinearLayout {

    private static final int ANIMATION_DURATION = 1000;

    @BindView(R.id.view_error_text)
    TextView txtErrorText;

    private View rootView;

    public NotificationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.view_error_message, this);
        ButterKnife.bind(rootView);

    }

    /**
     * Shows notification message on the screen by given notification type.
     */
    public void showErrorMessage(ViewNotificationType notificationType) {
        rootView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.errorViewRed));

        if (notificationType == ViewNotificationType.CONNECTION_NOTIFICATION) {
            txtErrorText.setText(getResources().getString(R.string.network_connectivity_error_message));
        } else {
            txtErrorText.setText(getResources().getString(R.string.api_error_message));
        }

        rootView.animate()
                .alpha(1.0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rootView.setVisibility(View.VISIBLE);
                        Timber.i(notificationType.name() + " notification view showed");
                    }
                });
    }

    /**
     * Hides error message on the screen.
     */
    public void hideErrorMessage(ViewNotificationType notificationType) {
        if (rootView.getVisibility() == GONE) {
            return;
        }
        if (notificationType == ViewNotificationType.CONNECTION_NOTIFICATION) {
            rootView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.infoViewGreen));
            txtErrorText.setText(getResources().getString(R.string.network_connectivity_info_message));
        }
        rootView.animate()
                .alpha(0.0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rootView.setVisibility(View.GONE);
                        Timber.i(notificationType.name() + " notification view hidden");
                    }
                });
    }

}
