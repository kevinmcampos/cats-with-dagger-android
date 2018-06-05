package me.kevincampos.catsdagger.cat_api;

import android.util.Log;

public class CacheTheCatAPI implements TheCatAPI {

    private static final String TAG = "CacheTheCatAPI";

    private TheCatAPI catAPI;

    private CatImagesModel cachedResponse;

    public CacheTheCatAPI(TheCatAPI catAPI) {
        this.catAPI = catAPI;
    }

    @Override
    public void getCatsWithHat(final Callback callback) {
        if (cachedResponse != null) {
            Log.d(TAG, "Using cached response.");
            callback.response(cachedResponse);

        } else {
            catAPI.getCatsWithHat(new Callback() {
                @Override
                public void response(CatImagesModel response) {
                    Log.d(TAG, "Saving response to cache.");
                    cachedResponse = response;
                    callback.response(response);
                }
            });
        }
    }

}
