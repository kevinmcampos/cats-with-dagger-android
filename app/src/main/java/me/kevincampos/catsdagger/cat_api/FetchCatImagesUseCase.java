package me.kevincampos.catsdagger.cat_api;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FetchCatImagesUseCase {

    private static final String TAG = "FetchCatImagesUseCase";

    public interface Callback {
        void imagesUrls(List<String> imagesUrls);
    }

    public FetchCatImagesUseCase() {
    }

    public void fetchImages(final Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thecatapi.com/api/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        RetrofitTheCatAPI retrofitTheCatApi = retrofit.create(RetrofitTheCatAPI.class);
        retrofitTheCatApi.listCatsWithHat().enqueue(new retrofit2.Callback<CatImagesModel>() {
            @Override
            public void onResponse(@NonNull Call<CatImagesModel> call, @NonNull Response<CatImagesModel> response) {
                ArrayList<String> imageUrls = new ArrayList<>();
                if (response.body().catImages != null) {
                    for (CatImageModel img : response.body().catImages) {
                        Log.d(TAG, "Found: " + img.url);
                        imageUrls.add(img.url);
                    }
                }

                callback.imagesUrls(imageUrls);
            }

            @Override
            public void onFailure(@NonNull Call<CatImagesModel> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching cat images", t);
            }
        });
    }

}
