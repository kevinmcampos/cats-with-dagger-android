package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.FavoritesActivity;

public class FavoritesActivityDIComponent {

    private FavoritesActivityDIModule favoritesActivityDIModule;

    public FavoritesActivityDIComponent() {
        this.favoritesActivityDIModule = new FavoritesActivityDIModule(UserDIComponent.get());
    }

    public void inject(FavoritesActivity activity) {
        activity.injectGetFavoritesUserCase(favoritesActivityDIModule.provideGetFavoritesUseCase());
    }

}
