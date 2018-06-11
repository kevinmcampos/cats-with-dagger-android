package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.favorites.FavoriteRepository;

interface FavoriteRepoDIModule {

    AppDIComponent getAppDIComponent();

    FavoriteRepository provideFavoriteRepository();

}
