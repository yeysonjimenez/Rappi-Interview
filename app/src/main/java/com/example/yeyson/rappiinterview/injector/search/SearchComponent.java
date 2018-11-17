package com.example.yeyson.rappiinterview.injector.search;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.ui.activity.MainActivity;

import dagger.Component;

@Activityes
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(MainActivity activity);
}
