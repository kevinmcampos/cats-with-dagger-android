package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.cat_api.FetchCatImagesUseCase;
import me.kevincampos.catsdagger.favorites.AddFavoriteUseCase;

public class ListActivityDIModule {

    private UserDIComponent userDIComponent;

    public static AddFavoriteUseCase testAddFavoriteUseCase;
    public static FetchCatImagesUseCase testFetchCatImagesUseCase;


    public ListActivityDIModule(UserDIComponent userDIComponent) {
        this.userDIComponent = userDIComponent;
    }

    public AddFavoriteUseCase provideFavoriteUseCase() {
        if (testAddFavoriteUseCase != null) return testAddFavoriteUseCase;

        return new AddFavoriteUseCase(userDIComponent.getFavoriteRepository());
    }

    public FetchCatImagesUseCase provideFetchCatImagesUseCase() {
        if (testFetchCatImagesUseCase != null) return testFetchCatImagesUseCase;

        return new FetchCatImagesUseCase(AppDIComponent.get().getTheCatAPI());
    }

}
