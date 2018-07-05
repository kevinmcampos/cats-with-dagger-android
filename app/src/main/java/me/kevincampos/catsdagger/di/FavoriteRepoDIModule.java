package me.kevincampos.catsdagger.di;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.kevincampos.catsdagger.UserScope;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;

@Module
public class FavoriteRepoDIModule {

    @Provides
    @UserScope
    FavoriteRepository provideFavoriteRepository(Context appContext, @Named("UserToken") String userToken) {
        throw new EmptyModuleException();
    }

    @Provides
    @Named("UserToken")
    @UserScope
    String provideUserToken() {
        throw new EmptyModuleException();
    }

}
