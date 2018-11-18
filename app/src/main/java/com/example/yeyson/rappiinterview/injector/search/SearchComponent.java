package com.example.yeyson.rappiinterview.injector.search;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.injector.popular.PopularModule;
import com.example.yeyson.rappiinterview.injector.toprated.TopRatedModule;
import com.example.yeyson.rappiinterview.injector.upcomming.UpcomingModule;
import com.example.yeyson.rappiinterview.ui.activity.MainActivity;

import dagger.Component;

@Activityes
@Component(modules = {SearchModule.class,PopularModule.class,TopRatedModule.class,UpcomingModule.class}, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(MainActivity activity);
}
