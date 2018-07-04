package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.cat_api.CacheTheCatAPI;
import me.kevincampos.catsdagger.cat_api.RetrofitTheCatAPI;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;

public class CachedRetrofitCatAPIDIModule extends TheCatAPIDIModule {

    @Override
    public TheCatAPI provideTheCatAPI() {
        return provideCacheTheCatAPI();
    }

    private CacheTheCatAPI provideCacheTheCatAPI() {
        return new CacheTheCatAPI(providerRetrofitTheCatAPI());
    }

    private RetrofitTheCatAPI providerRetrofitTheCatAPI() {
        return new RetrofitTheCatAPI();
    }

}
