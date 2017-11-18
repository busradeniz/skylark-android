package ostmodern.skylark.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.local.SetEntity;
import ostmodern.skylark.util.CustomGlideUrl;
import ostmodern.skylarkClient.R;

public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.SetListViewHolder> {

    public static final int DEBOUNCE_TIMEOUT = 300;
    private List<SetUI> sets = new ArrayList<>();

    private PublishSubject<SetUI> favouriteClickSubject = PublishSubject.create();

    @Override
    public SetListAdapter.SetListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_set_list, parent, false);
        return new SetListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SetListAdapter.SetListViewHolder holder, int position) {
        final SetEntity setEntity = sets.get(position).getSetEntity();
        holder.txtSetTitle.setText(setEntity.getTitle());
        holder.txtSetFilmCount.setText("");
        if (setEntity.getFilmCount() > 0) {
            holder.txtSetFilmCount.setText(String.format("%d %s", setEntity.getFilmCount(),
                    holder.itemView.getResources().getString(R.string.films)));
        }

        if (sets.get(position).isFavourite()) {
            holder.imgFavourite.setImageDrawable(ContextCompat
                    .getDrawable(holder.itemView.getContext(), R.mipmap.favourite));
        } else {
            holder.imgFavourite.setImageDrawable(ContextCompat
                    .getDrawable(holder.itemView.getContext(), R.mipmap.unfavourite));
        }
        // TODO: set image in case of error.
        Glide.with(holder.itemView.getContext())
                .load(new CustomGlideUrl(setEntity.getImageUrl()))
                .into(holder.imgSet);

        RxView.clicks(holder.imgFavourite).debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    sets.get(position).setFavourite(!sets.get(position).isFavourite());
                    favouriteClickSubject.onNext(sets.get(position));
                    notifyItemChanged(position);
                });
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public Observable<SetUI> getFavouriteObservable() {
        return favouriteClickSubject;
    }

    /**
     * Updates data set with the new coming set list.
     *
     * @param setList list of set
     */
    public void updateDataSet(List<SetUI> setList) {
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(new SetUIDiffCallback(sets, setList), true);
        this.sets.clear();
        sets.addAll(setList);
        diffResult.dispatchUpdatesTo(this);
    }

    protected class SetListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_set_title)
        TextView txtSetTitle;

        @BindView(R.id.txt_set_film_count)
        TextView txtSetFilmCount;

        @BindView(R.id.img_set)
        ImageView imgSet;

        @BindView(R.id.img_favourite)
        ImageView imgFavourite;


        public SetListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
