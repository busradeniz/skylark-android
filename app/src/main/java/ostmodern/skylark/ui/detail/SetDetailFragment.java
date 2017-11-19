package ostmodern.skylark.ui.detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ostmodern.skylark.MainActivity;
import ostmodern.skylark.di.Injectable;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.util.CustomGlideUrl;
import ostmodern.skylarkClient.R;

public class SetDetailFragment extends Fragment implements Injectable, SetDetailContract.View {

    private static final int DEBOUNCE_TIMEOUT = 300;

    @Inject
    SetDetailContract.Presenter setDetailPresenter;

    @BindView(R.id.img_set)
    ImageView imgSet;

    @BindView(R.id.txt_set_title)
    TextView txtSetTitle;

    @BindView(R.id.txt_film_count)
    TextView txtFilmCount;

    @BindView(R.id.txt_set_body)
    TextView txtSetBody;

    @BindView(R.id.img_favourite)
    ImageView imgFavourite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_set_detail, container, false);
        ButterKnife.bind(this, view);
        setActionBar();


        RxView.clicks(imgFavourite).debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    setDetailPresenter.onFavouriteClicked();
                });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            setDetailPresenter.setSelectedSetId(args.getString("setId"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setDetailPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        setDetailPresenter.unsubscribe();
    }

    @Override
    public void showSetDetails(SetUI setUI) {
        // TODO: set image in case of error.
        Glide.with(getContext())
                .load(new CustomGlideUrl(setUI.getSetEntity().getImageUrl()))
                .into(imgSet);

        txtSetTitle.setText(setUI.getSetEntity().getTitle());
        txtSetBody.setText(Html.fromHtml(setUI.getSetEntity().getFormattedBody()));
        if (setUI.getSetEntity().getFilmCount() > 0) {
            txtFilmCount.setText(String.format("%d %s", setUI.getSetEntity().getFilmCount(),
                    getResources().getString(R.string.films)));
        }
        updateFavouriteView(setUI.isFavourite());
    }

    @Override
    public void updateFavouriteView(boolean isFavourite) {
        if (isFavourite) {
            imgFavourite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.favourite));
        } else {
            imgFavourite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.unfavourite));
        }
    }

    private void setActionBar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
