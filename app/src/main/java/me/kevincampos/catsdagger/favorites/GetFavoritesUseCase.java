package me.kevincampos.catsdagger.favorites;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class GetFavoritesUseCase {

    private FavoriteRepository favoriteRepository;

    public interface Callback {
        void favoriteUrlsUpdated(List<String> favoriteUrls);
    }

    public GetFavoritesUseCase(Context context, String userToken) {
        favoriteRepository = new FavoriteRepository(context, userToken);
    }

    /**
     * @param callback Callback returns a list of favorites once during registration and every time
     *                 the favorites are updated.
     */
    public void getFavoritesUrl(final Callback callback) {
        callback.favoriteUrlsUpdated(favoritesToUrls(favoriteRepository.getFavorites()));

        favoriteRepository.registerChangeListener(new FavoriteRepository.ChangeListener() {
            @Override
            public void onChangeFavorites(List<FavoriteModel> favorites) {
                callback.favoriteUrlsUpdated(favoritesToUrls(favorites));
            }
        });
    }

    /**
     * Clear needs to be called when the use case if no more needed.
     */
    public void clear() {
        favoriteRepository.clearChangeListener();
    }

    private List<String> favoritesToUrls(List<FavoriteModel> favorites) {
        ArrayList<String> urlsSorted = new ArrayList<>(favorites.size());
        for (FavoriteModel favorite : favorites) {
            urlsSorted.add(favorite.getUrl());
        }
        return urlsSorted;
    }
}
