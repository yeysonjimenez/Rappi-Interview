package com.example.yeyson.rappiinterview.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.yeyson.rappiinterview.FilmApp;
import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.entity.toprated.TopRatedResults;
import com.example.yeyson.rappiinterview.injector.toprated.DaggerTopRatedComponent;
import com.example.yeyson.rappiinterview.injector.toprated.TopRatedModule;
import com.example.yeyson.rappiinterview.mvp.presentation.toprated.TopRatedPresenter;
import com.example.yeyson.rappiinterview.mvp.view.toprated.ITopRatedView;
import com.example.yeyson.rappiinterview.ui.adapter.TopRatedRecyclerViewAdapter;
import com.example.yeyson.rappiinterview.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRatedFragment extends Fragment implements ITopRatedView {

    @BindView(R.id.topratedProgress)
    ProgressBar topratedProgress;
    @BindView(R.id.toprated_movies_recyclerView)
    RecyclerView recyclerView;

    @Inject
    TopRatedPresenter presenter;
    @Inject
    RxBus bus;

    TopRatedRecyclerViewAdapter adapter;

    public TopRatedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        ButterKnife.bind(this, view);

        initInjector();

        presenter.loadTopRatedMovies();

        return view;
    }

    @Override
    public void showProgress() {
        topratedProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        topratedProgress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTopRatedMovies(List<TopRatedResults> topRatedResultsList) {
        adapter = new TopRatedRecyclerViewAdapter(topRatedResultsList, getContext(), bus);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }



    private void initInjector() {
        DaggerTopRatedComponent.builder()
                .appComponent(FilmApp.get(getContext()).getAppComponent())
                .topRatedModule(new TopRatedModule(this))
                .build().inject(this);
    }
}
