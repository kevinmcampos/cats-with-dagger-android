package me.kevincampos.catsdagger;

import android.app.Application;

import me.kevincampos.catsdagger.cat_api.CacheTheCatAPI;
import me.kevincampos.catsdagger.cat_api.RetrofitTheCatAPI;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;

public class App extends Application {

    private static TheCatAPI theCatAPI;

    public static TheCatAPI getTheCatAPI() {
        return theCatAPI;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TheCatAPI requestCatAPI = new RetrofitTheCatAPI();
        CacheTheCatAPI cacheCatAPI = new CacheTheCatAPI(requestCatAPI);
        App.theCatAPI = cacheCatAPI;
    }
}
