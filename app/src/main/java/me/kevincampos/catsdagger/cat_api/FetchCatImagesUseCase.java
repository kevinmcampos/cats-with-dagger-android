package me.kevincampos.catsdagger.cat_api;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FetchCatImagesUseCase {

    private static final String TAG = "FetchCatImagesUseCase";

    private TheCatAPI theCatApi;

    public interface Callback {
        void imagesUrls(List<String> imagesUrls);
    }

    public FetchCatImagesUseCase(TheCatAPI theCatApi) {
        this.theCatApi = theCatApi;
    }

    public void fetchImages(final Callback callback) {
        theCatApi.getCatsWithHat(new TheCatAPI.Callback() {
            @Override
            public void response(CatImagesModel response) {
                ArrayList<String> imageUrls = new ArrayList<>();
                for (CatImageModel img : response.catImages) {
                    Log.d(TAG, "Found: " + img.url);
                    imageUrls.add(img.url);
                }
                callback.imagesUrls(imageUrls);
            }
        });
    }

}
