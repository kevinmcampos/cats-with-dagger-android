package me.kevincampos.catsdagger.favorites;

import java.util.List;

public interface FavoriteRepository {

    interface ChangeListener {
        void onChangeFavorites(List<FavoriteModel> favorites);
    }

    /**
     * @return A list of favorites sorted by the time it was added.
     */
    List<FavoriteModel> getFavorites();

    /**
     * @param favoriteModel
     * @return A list of favorites sorted by the time it was added, with the newly added favorite.
     */
    List<FavoriteModel> addFavorite(FavoriteModel favoriteModel);

    void registerChangeListener(ChangeListener changeListener);

    void clearChangeListener();

}
