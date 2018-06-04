package me.kevincampos.catsdagger.favorites;

import android.content.Context;

import java.util.List;

public class AddFavoriteUseCase {

    private FavoriteRepository favoriteRepository;

    public AddFavoriteUseCase(Context context, String userToken) {
        favoriteRepository = new FavoriteRepository(context, userToken);
    }

    /**
     * @param url
     * @return True if the url was added successfully.
     */
    public Boolean addFavoriteUrl(String url) {
        if (url == null) {
            return false;
        }
        long timeNow = System.currentTimeMillis();
        FavoriteModel model = new FavoriteModel(timeNow, url);
        List<FavoriteModel> currentList = favoriteRepository.addFavorite(model);
        return currentList.contains(model);
    }

}
