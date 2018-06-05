package me.kevincampos.catsdagger.cat_api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FetchCatImagesUseCaseTest {

    @Test
    public void testNoImages() throws InterruptedException {
        StubAPI stubAPI = new StubAPI();
        FetchCatImagesUseCase fetchCatImagesUseCase = new FetchCatImagesUseCase(stubAPI);

        final CountDownLatch latch = new CountDownLatch(1);
        fetchCatImagesUseCase.fetchImages(new FetchCatImagesUseCase.Callback() {
            @Override
            public void imagesUrls(List<String> imagesUrls) {
                assertThat(imagesUrls.size(), equalTo(0));
                latch.countDown();
            }
        });

        stubAPI.respond(new ArrayList<String>());
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testTwoImages() throws InterruptedException {
        StubAPI stubAPI = new StubAPI();
        FetchCatImagesUseCase fetchCatImagesUseCase = new FetchCatImagesUseCase(stubAPI);

        final CountDownLatch latch = new CountDownLatch(1);
        fetchCatImagesUseCase.fetchImages(new FetchCatImagesUseCase.Callback() {
            @Override
            public void imagesUrls(List<String> imagesUrls) {
                assertThat(imagesUrls.size(), equalTo(2));
                assertThat(imagesUrls.get(0), equalTo("kitty.jpg"));
                assertThat(imagesUrls.get(1), equalTo("cat.jpg"));
                latch.countDown();
            }
        });


        ArrayList<String> respondUrls = new ArrayList<>();
        respondUrls.add("kitty.jpg");
        respondUrls.add("cat.jpg");
        stubAPI.respond(respondUrls);

        latch.await(10, TimeUnit.SECONDS);
    }

    class StubAPI implements TheCatAPI {

        Callback callback;

        @Override
        public void getCatsWithHat(Callback callback) {
            this.callback = callback;
        }

        void respond(List<String> urls) {
            CatImagesModel response = new CatImagesModel();
            ArrayList<CatImageModel> images = new ArrayList<>();

            for (String url : urls) {
                CatImageModel catImageModel = new CatImageModel();
                catImageModel.id = url + "id";
                catImageModel.url = url;
                catImageModel.sourceUrl = url;

                images.add(catImageModel);
            }

            response.catImages = images;
            callback.response(response);
        }

    }
}
