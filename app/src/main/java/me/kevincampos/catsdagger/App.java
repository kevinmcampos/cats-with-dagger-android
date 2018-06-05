package me.kevincampos.catsdagger;

import android.app.Application;

import me.kevincampos.catsdagger.cat_api.CacheTheCatAPI;
import me.kevincampos.catsdagger.cat_api.RetrofitTheCatAPI;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;
import me.kevincampos.catsdagger.favorites.SharedPrefFavoritesRepository;

public class App extends Application {

    private static TheCatAPI theCatAPI;
    private static FavoriteRepository favoriteRepository;

    public static TheCatAPI getTheCatAPI() {
        return theCatAPI;
    }

    public static FavoriteRepository getFavoriteRepository() {
        return favoriteRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TheCatAPI requestCatAPI = new RetrofitTheCatAPI();
        CacheTheCatAPI cacheCatAPI = new CacheTheCatAPI(requestCatAPI);
        App.theCatAPI = cacheCatAPI;
    }

    public void initializeFavoriteRepository(String userToken) {
        if (App.favoriteRepository != null) {
            throw new RuntimeException("FavoritesRepository already initialized.");
        }
        App.favoriteRepository = new SharedPrefFavoritesRepository(getApplicationContext(), userToken);
    }

    public void destroyFavoriteRepository() {
        if (App.favoriteRepository != null) {
            App.favoriteRepository.clearChangeListener();
            App.favoriteRepository = null;
        }
    }
}
