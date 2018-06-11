package me.kevincampos.catsdagger;

import android.app.Application;
import android.content.Context;

import me.kevincampos.catsdagger.di.AppDIComponent;
import me.kevincampos.catsdagger.di.AppDIModule;
import me.kevincampos.catsdagger.di.CachedRetrofitCatAPIDIModule;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppDIModule appDIModule = new AppDIModule() {
            @Override
            public Context provideAppContext() {
                return getApplicationContext();
            }
        };
        AppDIComponent.initialize(appDIModule, new CachedRetrofitCatAPIDIModule());
    }

}
