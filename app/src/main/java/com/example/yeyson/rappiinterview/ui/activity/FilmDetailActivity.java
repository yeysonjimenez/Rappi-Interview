package com.example.yeyson.rappiinterview.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yeyson.rappiinterview.FilmApp;
import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.event.PopularDetailEvent;
import com.example.yeyson.rappiinterview.injector.detail.DaggerDetailComponent;
import com.example.yeyson.rappiinterview.util.Constants;
import com.example.yeyson.rappiinterview.util.RxBus;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class FilmDetailActivity extends AppCompatActivity {

    @Inject
    RxBus bus;

    @BindView(R.id.coverImage)
    ImageView coverImage;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_backdrop)
    ImageView detailBackdrop;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.detail_overview)
    TextView detailOverview;

    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        ButterKnife.bind(this);

        // for transparent status bar
        getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initInjector();
        mSubscription.add(bus.toStickyObservable()
                .subscribe(event -> {
                            if (event instanceof PopularDetailEvent) {
                                detailTitle.setText(((PopularDetailEvent) event).getResults().getTitle());
                                releaseDate.setText(((PopularDetailEvent) event).getResults().getRelease_date());
                                detailOverview.setText(((PopularDetailEvent) event).getResults().getOverview());

                                Glide.with(this)
                                        .load(Constants.IMAGE_BASE_URL
                                                + Constants.IMAGE_W92
                                                + ((PopularDetailEvent) event).getResults().getPoster_path())
                                        .into(detailBackdrop);

                                Glide.with(this)
                                        .load(Constants.IMAGE_BASE_URL
                                                + Constants.IMAGE_W780
                                                + ((PopularDetailEvent) event).getResults().getBackdrop_path())
                                        .into(coverImage);
                            }
                        },
                        e -> Timber.e(e.getMessage())));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void initInjector() {

        DaggerDetailComponent.builder()
                .appComponent(FilmApp.get(this).getAppComponent())
                .build().inject(this);
    }
}

