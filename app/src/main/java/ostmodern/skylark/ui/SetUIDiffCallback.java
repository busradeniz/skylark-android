package ostmodern.skylark.ui;

import android.support.v7.util.DiffUtil;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.util.StringUtils;

/**
 * A diff callback to partially update RecyclerView.
 */
public class SetUIDiffCallback extends DiffUtil.Callback {

    private final List<SetUI> oldItems;
    private final List<SetUI> newItems;

    /**
     * Constructor.
     *
     * @param oldItems old values in a adapter.
     * @param newItems new values from repository.
     */
    public SetUIDiffCallback(List<SetUI> oldItems, List<SetUI> newItems) {
        this.oldItems = ImmutableList.copyOf(oldItems);
        this.newItems = ImmutableList.copyOf(newItems);
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldItems.get(oldItemPosition).getSetEntity().getUid(),
                newItems.get(newItemPosition).getSetEntity().getUid());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        SetUI oldItem = oldItems.get(oldItemPosition);
        SetUI newItem = newItems.get(newItemPosition);
        return oldItem.isFavourite() == newItem.isFavourite()
                && oldItem.getSetEntity().getFilmCount() == newItem.getSetEntity().getFilmCount()
                && Objects.equals(oldItem.getSetEntity().getTitle(),
                    newItem.getSetEntity().getTitle())
                && Objects.equals(StringUtils.meaningfulPart(oldItem.getSetEntity().getImageUrl()),
                    StringUtils.meaningfulPart(newItem.getSetEntity().getImageUrl()));
    }
}
