package com.example.yeyson.rappiinterview.injector.popular;

import com.example.yeyson.rappiinterview.injector.Fragments;
import com.example.yeyson.rappiinterview.injector.app.AppComponent;
import com.example.yeyson.rappiinterview.ui.fragment.PopularFragment;

import dagger.Component;

@Fragments
@Component(dependencies = AppComponent.class, modules = PopularModule.class)
public interface PopularComponent {
    void inject(PopularFragment fragment);
}
