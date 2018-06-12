package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.favorites.FavoriteRepository;

public interface FavoriteRepoDIModule {

    AppDIComponent getAppDIComponent();

    FavoriteRepository provideFavoriteRepository();

}
