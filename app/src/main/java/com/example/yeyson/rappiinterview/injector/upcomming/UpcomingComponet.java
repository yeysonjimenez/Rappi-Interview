package com.example.yeyson.rappiinterview.injector.upcomming;

import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.ui.fragment.UpcomingFragment;

import dagger.Component;

@Fragments
@Component(modules = UpcomingModule.class, dependencies = AppComponent.class)
public interface UpcomingComponet {
    void inject(UpcomingFragment fragment);
}
