package ostmodern.skylark.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ostmodern.skylark.model.Set;
import ostmodern.skylarkClient.R;

public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.SetListViewHolder> {

    private ArrayList<Set> sets = new ArrayList<>();

    @Override
    public SetListAdapter.SetListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_set_list, parent, false);
        return new SetListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SetListAdapter.SetListViewHolder holder, int position) {
        final Set set = sets.get(position);
        holder.txtSetTitle.setText(set.getTitle());
        set.getTitle().length()
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public void updateDataSet(List<Set> setList) {
        this.sets.clear();
        sets.addAll(setList);
        notifyDataSetChanged();
    }

    protected class SetListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_set_title)
        TextView txtSetTitle;

        @BindView(R.id.img_set)
        ImageView imgSet;

        public SetListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
