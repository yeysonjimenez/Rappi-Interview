package com.example.yeyson.rappiinterview.injector.detail;

import com.example.yeyson.rappiinterview.injector.Activityes;
import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.ui.activity.FilmDetailActivity;

import dagger.Component;

@Activityes
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent {
    void inject(FilmDetailActivity activity);
}
