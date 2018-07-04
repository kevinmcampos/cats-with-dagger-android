package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.cat_api.RetrofitTheCatAPI;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;

public class RetrofitCatApiDIModule extends TheCatAPIDIModule {

    @Override
    public TheCatAPI provideTheCatAPI() {
        return new RetrofitTheCatAPI();
    }

}
