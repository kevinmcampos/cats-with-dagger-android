package me.kevincampos.catsdagger.di;

import dagger.Component;
import me.kevincampos.catsdagger.UserScope;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;

@UserScope
@Component(modules = FavoriteRepoDIModule.class,
        dependencies = AppDIComponent.class
)
public abstract class UserDIComponent {

    private static UserDIComponent instance;

    public static UserDIComponent get() {
        return UserDIComponent.instance;
    }

    public static void initialize(FavoriteRepoDIModule favoriteRepoDIModule) {
        if (UserDIComponent.get() != null) {
            throw new RuntimeException("UserDIComponent already initialized.");
        }

        UserDIComponent.instance = DaggerUserDIComponent.builder()
                .appDIComponent(AppDIComponent.get())
                .favoriteRepoDIModule(favoriteRepoDIModule)
                .build();
    }

    abstract FavoriteRepository getFavoriteRepository();

    public void close() {
        FavoriteRepository favoriteRepository = getFavoriteRepository();
        if (favoriteRepository != null) {
            favoriteRepository.clearChangeListener();
        }

        UserDIComponent.instance = null;
    }
}
