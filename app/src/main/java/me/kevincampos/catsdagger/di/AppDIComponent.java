package me.kevincampos.catsdagger.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;

@Singleton
@Component(modules = {AppDIModule.class, TheCatAPIDIModule.class})
public abstract class AppDIComponent {

    private static AppDIComponent instance;

    public static AppDIComponent get() {
        return AppDIComponent.instance;
    }

    public static void initialize(AppDIModule appDIModule, TheCatAPIDIModule theCatAPIDIModule) {
        if (AppDIComponent.get() != null) {
            throw new RuntimeException("AppDIComponent already initialized.");
        }

        AppDIComponent.instance = DaggerAppDIComponent.builder()
                .appDIModule(appDIModule)
                .theCatAPIDIModule(theCatAPIDIModule)
                .build();
    }

    abstract Context getAppContext();

    abstract TheCatAPI getTheCatAPI();

}
