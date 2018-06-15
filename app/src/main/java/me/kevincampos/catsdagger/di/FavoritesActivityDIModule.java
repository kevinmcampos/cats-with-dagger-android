package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.favorites.GetFavoritesUseCase;

public class FavoritesActivityDIModule {

    private UserDIComponent userDIComponent;

    public static GetFavoritesUseCase testGetFavoritesUseCase;

    public FavoritesActivityDIModule(UserDIComponent userDIComponent) {
        this.userDIComponent = userDIComponent;
    }

    GetFavoritesUseCase provideGetFavoritesUseCase() {
        if (testGetFavoritesUseCase != null) {
            return testGetFavoritesUseCase;
        }
        return new GetFavoritesUseCase(userDIComponent.getFavoriteRepository());
    }

}
