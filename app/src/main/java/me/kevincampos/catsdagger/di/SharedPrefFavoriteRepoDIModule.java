package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.favorites.FavoriteRepository;
import me.kevincampos.catsdagger.favorites.SharedPrefFavoritesRepository;

public class SharedPrefFavoriteRepoDIModule implements FavoriteRepoDIModule {

    private AppDIComponent appDIComponent;
    private String userToken;

    public SharedPrefFavoriteRepoDIModule(AppDIComponent appDIComponent, String userToken) {
        this.appDIComponent = appDIComponent;
        this.userToken = userToken;
    }

    @Override
    public AppDIComponent getAppDIComponent() {
        return this.appDIComponent;
    }

    @Override
    public FavoriteRepository provideFavoriteRepository() {
        return new SharedPrefFavoritesRepository(getAppDIComponent().getAppContext(), provideUserToken());
    }

    private String provideUserToken() {
        return userToken;
    }

}
