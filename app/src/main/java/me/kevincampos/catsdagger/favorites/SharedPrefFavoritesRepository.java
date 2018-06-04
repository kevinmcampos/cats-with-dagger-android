package me.kevincampos.catsdagger.favorites;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.kevincampos.catsdagger.R;

public class SharedPrefFavoritesRepository implements FavoriteRepository {

    private static final String TAG = "SharedPrefFavoritesRepo";

    private static String SP_USER_FAVORITES_KEY = "user-favorites-urls-%s";

    private final Context context;
    private final String userToken;
    private ChangeListener changeListener;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPrefChangeListener;

    public SharedPrefFavoritesRepository(Context context, String userToken) {
        this.context = context;
        this.userToken = userToken;
    }

    @Override
    public List<FavoriteModel> getFavorites() {
        SharedPreferences pref = getPref();
        String prefKey = getFavoritesKey();
        Set<String> entriesSet = pref.getStringSet(prefKey, new HashSet<String>());

        ArrayList<FavoriteModel> favorites = new ArrayList<>(entriesSet.size());
        for (String entry : entriesSet) {
            String[] decoded = entry.split(";");
            favorites.add(new FavoriteModel(Long.valueOf(decoded[1]), decoded[0]));
        }

        Collections.sort(favorites, new Comparator<FavoriteModel>() {
            @Override
            public int compare(FavoriteModel o1, FavoriteModel o2) {
                return (int) (o2.getTimeAdded() - o1.getTimeAdded());
            }
        });

        return favorites;
    }

    @Override
    public List<FavoriteModel> addFavorite(FavoriteModel favoriteModel) {
        List<FavoriteModel> oldFavorites = getFavorites();

        boolean hasUrl = false;
        for (FavoriteModel oldFavorite : oldFavorites) {
            if (oldFavorite.getUrl().equals(favoriteModel.getUrl())) {
                hasUrl = true;
                break;
            }
        }

        if (hasUrl) {
            return oldFavorites;
        }

        List<FavoriteModel> newFavorites = new ArrayList<>(oldFavorites);
        newFavorites.add(favoriteModel);

        saveFavorites(newFavorites);

        return newFavorites;
    }

    @Override
    public void registerChangeListener(ChangeListener listener) {
        if (this.changeListener != null) {
            throw new RuntimeException("Listener already registered.");
        }
        this.changeListener = listener;
        this.sharedPrefChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "Key changed: " + key);
                String prefKey = getFavoritesKey();
                if (key.equals(prefKey)) {
                    changeListener.onChangeFavorites(getFavorites());
                }
            }
        };
        getPref().registerOnSharedPreferenceChangeListener(sharedPrefChangeListener);
    }

    @Override
    public void clearChangeListener() {
        changeListener = null;
        if (sharedPrefChangeListener != null) {
            getPref().unregisterOnSharedPreferenceChangeListener(sharedPrefChangeListener);
            sharedPrefChangeListener = null;
        }
    }

    private SharedPreferences getPref() {
        String prefName = context.getString(R.string.pref_key_user_data);
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    private String getFavoritesKey() {
        return String.format(SP_USER_FAVORITES_KEY, userToken);
    }

    private void saveFavorites(List<FavoriteModel> newFavorites) {
        Set<String> newFavoritesSet = new HashSet<>(newFavorites.size());

        for (FavoriteModel entry : newFavorites) {
            newFavoritesSet.add(entry.getUrl() + ";" + entry.getTimeAdded());
        }

        getPref().edit().putStringSet(getFavoritesKey(), newFavoritesSet).apply();
    }

}
