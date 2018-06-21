package me.kevincampos.catsdagger.di;

import me.kevincampos.catsdagger.ListActivity;

public class ListActivityDIComponent {

    private ListActivityDIModule listActivityDIModule;

    public ListActivityDIComponent() {
        this.listActivityDIModule = new ListActivityDIModule(UserDIComponent.get());
    }

    public void inject(ListActivity activity) {
        activity.injectFetchCatImagesUseCase(listActivityDIModule.provideFetchCatImagesUseCase());
        activity.injectAddFavoriteUseCase(listActivityDIModule.provideFavoriteUseCase());
    }

}
