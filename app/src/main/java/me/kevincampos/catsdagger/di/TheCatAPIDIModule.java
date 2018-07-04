package me.kevincampos.catsdagger.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;

@Module
public class TheCatAPIDIModule {

    @Provides
    @Singleton
    public TheCatAPI provideTheCatAPI() {
        throw new EmptyModuleException();
    }

}
