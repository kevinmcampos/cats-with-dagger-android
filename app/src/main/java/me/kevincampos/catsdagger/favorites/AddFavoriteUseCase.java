package me.kevincampos.catsdagger.favorites;

import java.util.List;

public class AddFavoriteUseCase {

    private FavoriteRepository favoriteRepository;

    public AddFavoriteUseCase(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
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
