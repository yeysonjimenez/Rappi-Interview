package com.example.yeyson.rappiinterview.injector.app;


import com.example.yeyson.rappiinterview.model.api.Api;
import com.example.yeyson.rappiinterview.util.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class, NetworkModule.class })
public interface AppComponent {
    Api apiSource();

    RxBus rxbus();
}
