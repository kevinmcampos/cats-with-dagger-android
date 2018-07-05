package me.kevincampos.catsdagger.di;

import android.content.Context;

import me.kevincampos.catsdagger.favorites.FavoriteRepository;
import me.kevincampos.catsdagger.favorites.SharedPrefFavoritesRepository;

public class SharedPrefFavoriteRepoDIModule extends FavoriteRepoDIModule {

    private String userToken;

    public SharedPrefFavoriteRepoDIModule(String userToken) {
        this.userToken = userToken;
    }

    @Override
    FavoriteRepository provideFavoriteRepository(Context appContext, String userToken) {
        return new SharedPrefFavoritesRepository(appContext, userToken);
    }

    @Override
    String provideUserToken() {
        return userToken;
    }
}
