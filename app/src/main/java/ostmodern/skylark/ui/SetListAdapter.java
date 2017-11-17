package ostmodern.skylark.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ostmodern.skylark.repository.local.SetEntity;
import ostmodern.skylarkClient.R;

public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.SetListViewHolder> {

    private List<SetEntity> sets = new ArrayList<>();

    @Override
    public SetListAdapter.SetListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_set_list, parent, false);
        return new SetListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SetListAdapter.SetListViewHolder holder, int position) {
        final SetEntity setEntity = sets.get(position);
        holder.txtSetTitle.setText(setEntity.getTitle());
        holder.txtSetFilmCount.setText("");
        if (setEntity.getFilmCount() > 0) {
            holder.txtSetFilmCount.setText(String.format("%d %s", setEntity.getFilmCount(),
                    holder.itemView.getResources().getString(R.string.films)));
        }
        // TODO: set image in case of error.
        Glide.with(holder.itemView.getContext())
                .load(setEntity.getImageUrl())
                .into(holder.imgSet);
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    /**
     * Updates data set with the new coming set list.
     *
     * @param setList list of set
     */
    public void updateDataSet(List<SetEntity> setList) {
        this.sets.clear();
        sets.addAll(setList);
        notifyDataSetChanged();
    }

    protected class SetListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_set_title)
        TextView txtSetTitle;

        @BindView(R.id.txt_set_film_count)
        TextView txtSetFilmCount;

        @BindView(R.id.img_set)
        ImageView imgSet;

        public SetListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
