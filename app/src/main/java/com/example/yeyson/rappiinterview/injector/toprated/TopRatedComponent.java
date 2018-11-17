package com.example.yeyson.rappiinterview.injector.toprated;

import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.ui.fragment.TopRatedFragment;

import dagger.Component;


@Fragments
@Component(modules = TopRatedModule.class, dependencies = AppComponent.class)
public interface TopRatedComponent {
    void inject(TopRatedFragment fragment);
}
