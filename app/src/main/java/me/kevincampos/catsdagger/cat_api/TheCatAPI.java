package me.kevincampos.catsdagger.cat_api;

public interface TheCatAPI {

    interface Callback {
        void response(CatImagesModel response);
    }

    void getCatsWithHat(Callback response);

}
