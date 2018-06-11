package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.cat_api.TheCatAPI;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;

public class UserDIComponent {

    private static UserDIComponent instance;
    private FavoriteRepoDIModule favoriteRepoDIModule;
    private FavoriteRepository favoriteRepository;

    public static UserDIComponent get() {
        return UserDIComponent.instance;
    }

    public static void initialize(FavoriteRepoDIModule module) {
        if (UserDIComponent.get() != null) {
            throw new RuntimeException("UserDIComponent already initialized.");
        }
        UserDIComponent.instance = new UserDIComponent(module);
    }

    private UserDIComponent(FavoriteRepoDIModule favoriteRepoDIModule) {
        this.favoriteRepoDIModule = favoriteRepoDIModule;
    }

    public TheCatAPI getTheCatAPIService() {
        return this.favoriteRepoDIModule.getAppDIComponent().getTheCatAPI();
    }

    public FavoriteRepository getFavoriteRepository() {
        if (favoriteRepository == null) {
            favoriteRepository = favoriteRepoDIModule.provideFavoriteRepository();
        }
        return favoriteRepository;
    }

    public void close() {
        if (favoriteRepository != null) {
            favoriteRepository.clearChangeListener();
        }
    }
}
