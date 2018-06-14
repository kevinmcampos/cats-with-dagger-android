package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.favorites.GetFavoritesUseCase;

public class FavoritesActivityDIModule {

    private UserDIComponent userDIComponent;

    public FavoritesActivityDIModule(UserDIComponent userDIComponent) {
        this.userDIComponent = userDIComponent;
    }

    GetFavoritesUseCase provideGetFavoritesUseCase() {
        return new GetFavoritesUseCase(userDIComponent.getFavoriteRepository());
    }

}
