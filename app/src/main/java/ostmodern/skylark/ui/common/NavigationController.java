package ostmodern.skylark.ui.common;

import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import ostmodern.skylark.MainActivity;
import ostmodern.skylark.ui.SetListFragment;
import ostmodern.skylarkClient.R;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    /**
     * Creates {@link SetListFragment} and replace created fragment in given fragment manager.
     */
    public void navigateToSetList() {
        SetListFragment setListFragment = new SetListFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, setListFragment)
                .commitAllowingStateLoss();
    }
}
