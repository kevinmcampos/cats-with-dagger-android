package me.kevincampos.catsdagger;

import android.app.Application;
import android.content.Context;

import me.kevincampos.catsdagger.di.AppDIComponent;
import me.kevincampos.catsdagger.di.AppDIModule;
import me.kevincampos.catsdagger.di.CachedRetrofitCatAPIDIModule;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;
import me.kevincampos.catsdagger.favorites.SharedPrefFavoritesRepository;

public class App extends Application {

    private static FavoriteRepository favoriteRepository;

    public static FavoriteRepository getFavoriteRepository() {
        return favoriteRepository;
    }

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

    public void initializeFavoriteRepository(String userToken) {
        if (App.favoriteRepository != null) {
            throw new RuntimeException("FavoritesRepository already initialized.");
        }
        App.favoriteRepository = new SharedPrefFavoritesRepository(getApplicationContext(), userToken);
    }

    public void destroyFavoriteRepository() {
        if (App.favoriteRepository != null) {
            App.favoriteRepository.clearChangeListener();
            App.favoriteRepository = null;
        }
    }
}
