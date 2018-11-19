package com.example.yeyson.rappiinterview.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.yeyson.rappiinterview.FilmApp;
import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.entity.popular.PopularResults;
import com.example.yeyson.rappiinterview.entity.search.MoviesRes;
import com.example.yeyson.rappiinterview.entity.toprated.TopRatedResults;
import com.example.yeyson.rappiinterview.entity.upcoming.UpcomingResults;
import com.example.yeyson.rappiinterview.injector.popular.PopularModule;
import com.example.yeyson.rappiinterview.injector.search.DaggerSearchComponent;
import com.example.yeyson.rappiinterview.injector.search.SearchModule;
import com.example.yeyson.rappiinterview.injector.toprated.TopRatedModule;
import com.example.yeyson.rappiinterview.injector.upcomming.UpcomingModule;
import com.example.yeyson.rappiinterview.mvp.presentation.popular.PopularPresentation;
import com.example.yeyson.rappiinterview.mvp.presentation.search.SearchPresenter;
import com.example.yeyson.rappiinterview.mvp.presentation.toprated.TopRatedPresenter;
import com.example.yeyson.rappiinterview.mvp.presentation.upcomming.UpcomingPresenter;
import com.example.yeyson.rappiinterview.mvp.view.popular.IfPopularView;
import com.example.yeyson.rappiinterview.mvp.view.search.ISearchView;
import com.example.yeyson.rappiinterview.mvp.view.toprated.ITopRatedView;
import com.example.yeyson.rappiinterview.mvp.view.upcomming.IUpcomingView;
import com.example.yeyson.rappiinterview.ui.adapter.AutoCompleteAdapter;
import com.example.yeyson.rappiinterview.ui.adapter.PopularRecyclerViewAdapter;
import com.example.yeyson.rappiinterview.ui.adapter.TopRatedRecyclerViewAdapter;
import com.example.yeyson.rappiinterview.ui.adapter.UpcomingRecyclerViewAdapter;
import com.example.yeyson.rappiinterview.ui.adapter.ViewPagerAdapter;
import com.example.yeyson.rappiinterview.util.RxBus;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ISearchView, IfPopularView,ITopRatedView,IUpcomingView {

    @Inject
    SearchPresenter presenter;
    @Inject
    RxBus bus;
    @Inject
    PopularPresentation presenterPopular;
    @Inject
    TopRatedPresenter presenterTopRated;
    @Inject
    UpcomingPresenter presenterUpcoming;
    @BindView(R.id.mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.popular_movies_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.popularProgress)
    ProgressBar progressBar;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private SearchView searchView;
    private AutoCompleteAdapter adapter;
    private SearchView.SearchAutoComplete searchAutoComplete;
     public MainActivity(){}
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        initInjector();
        presenterPopular.loadPopularMovies();
        presenterTopRated.loadTopRatedMovies();
        presenterUpcoming.loadUpcomingMovies();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        searchView = (SearchView) search.getActionView();
        searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setThreshold(0);
        initSearch();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showSearchResponse(MoviesRes moviesResponse) {
        Log.e("Buscar","entra");
        adapter =
                new AutoCompleteAdapter(this.getApplicationContext(), moviesResponse.getResults(), bus);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("setOnItemClickListener", moviesResponse.getResults().get(i).getTitle());
        });
    }

    private void initSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("query", query);
                presenter.searchMovie(query);
                return query != null;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("onQueryTextChange", query);
                if (query.length() >= 4) presenter.searchMovie(query);
                return query != null;
            }
        });
    }

    private void initInjector() {

        DaggerSearchComponent.builder()
                .appComponent(FilmApp.get(this).getAppComponent())
                .searchModule(new SearchModule(this))
                .popularModule(new PopularModule(this))
                .topRatedModule(new TopRatedModule(this))
                .upcomingModule(new UpcomingModule(this))
                .build().inject(this);

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
    public void showUpComingMovies(List<UpcomingResults> topRatedResultsList) {
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(layoutManager3);
        UpcomingRecyclerViewAdapter adapter3 = new UpcomingRecyclerViewAdapter(topRatedResultsList, getBaseContext(), bus);
        recyclerView3.setAdapter(adapter3);
        dialog.hide();
    }

    @Override
    public void showTopRatedMovies(List<TopRatedResults> topRatedResultsList) {

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(layoutManager2);
        TopRatedRecyclerViewAdapter adapter2 = new TopRatedRecyclerViewAdapter(topRatedResultsList,getBaseContext(), bus);
        recyclerView2.setAdapter(adapter2);

    }

    @Override
    public void showPopularMovies(List<PopularResults> popularResultsList) {
         Log.e("popular list",""+popularResultsList.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        PopularRecyclerViewAdapter adapter = new PopularRecyclerViewAdapter(popularResultsList, getBaseContext(), bus);
        recyclerView.setAdapter(adapter);

    }


}

