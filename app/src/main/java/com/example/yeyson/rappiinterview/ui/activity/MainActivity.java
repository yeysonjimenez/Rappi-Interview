package com.example.yeyson.rappiinterview.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yeyson.rappiinterview.FilmApp;
import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.entity.search.MoviesRes;
import com.example.yeyson.rappiinterview.injector.search.DaggerSearchComponent;
import com.example.yeyson.rappiinterview.injector.search.SearchModule;
import com.example.yeyson.rappiinterview.mvp.presentation.search.SearchPresenter;
import com.example.yeyson.rappiinterview.mvp.view.search.ISearchView;
import com.example.yeyson.rappiinterview.ui.adapter.AutoCompleteAdapter;
import com.example.yeyson.rappiinterview.ui.adapter.ViewPagerAdapter;
import com.example.yeyson.rappiinterview.ui.fragment.PopularFragment;
import com.example.yeyson.rappiinterview.ui.fragment.TopRatedFragment;
import com.example.yeyson.rappiinterview.ui.fragment.UpcomingFragment;
import com.example.yeyson.rappiinterview.util.RxBus;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ISearchView {

    @Inject
    SearchPresenter presenter;
    @Inject
    RxBus bus;

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private SearchView searchView;
    private AutoCompleteAdapter adapter;
    private SearchView.SearchAutoComplete searchAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initInjector();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
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
                .build().inject(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PopularFragment(), "Popular");
        adapter.addFragment(new TopRatedFragment(), "Top Rated");
        adapter.addFragment(new UpcomingFragment(), "Upcoming");
        viewPager.setAdapter(adapter);
    }
}

