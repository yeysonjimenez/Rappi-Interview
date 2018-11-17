package com.example.yeyson.rappiinterview.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.yeyson.rappiinterview.FilmApp;
import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.entity.popular.PopularResults;
import com.example.yeyson.rappiinterview.injector.popular.DaggerPopularComponent;
import com.example.yeyson.rappiinterview.injector.popular.PopularModule;
import com.example.yeyson.rappiinterview.mvp.presentation.popular.PopularPresentation;
import com.example.yeyson.rappiinterview.mvp.view.popular.IfPopularView;
import com.example.yeyson.rappiinterview.ui.adapter.PopularRecyclerViewAdapter;
import com.example.yeyson.rappiinterview.util.RxBus;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Module;
import dagger.internal.DaggerCollections;

public class PopularFragment extends Fragment implements IfPopularView {

    @BindView(R.id.popular_movies_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.popularProgress)
    ProgressBar progressBar;

    @Inject
    PopularPresentation presenter;
    @Inject
    RxBus bus;

    private PopularRecyclerViewAdapter adapter;

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popular_fragment, container, false);
        ButterKnife.bind(this, view);

        initInjector();

        presenter.loadPopularMovies();

        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPopularMovies(List<PopularResults> popularResultsList) {
        adapter = new PopularRecyclerViewAdapter(popularResultsList, getContext(), bus);
        adapter.notifyDataSetChanged();
        // PopularResults results = adapter.getItemId(2);
        Log.e("datos","");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }



    private void initInjector() {

        DaggerPopularComponent.builder()
                .appComponent(FilmApp.get(Objects.requireNonNull(this.getContext())).getAppComponent())
                .popularModule(new PopularModule(this))
                .build().inject(this);
    }
}
