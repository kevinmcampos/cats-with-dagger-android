package me.kevincampos.catsdagger.favorites;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddFavoriteUseCaseTest {

    @Test
    public void testAddTrue() {
        StubRepo stubRepo = new StubRepo();
        AddFavoriteUseCase addFavoriteUseCase = new AddFavoriteUseCase(stubRepo);

        Boolean added = addFavoriteUseCase.addFavoriteUrl("cutecatwithhat.jpg");
        Assert.assertTrue(added);
    }

    @Test
    public void testAddFalse() {
        StubRepo stubRepo = new StubRepo();
        AddFavoriteUseCase addFavoriteUseCase = new AddFavoriteUseCase(stubRepo);

        stubRepo.addModel = false;

        Boolean added = addFavoriteUseCase.addFavoriteUrl("dog.jpg");
        Assert.assertFalse(added);
    }

    public class StubRepo implements FavoriteRepository {

        ArrayList<FavoriteModel> models;
        boolean addModel;

        StubRepo() {
            models = new ArrayList<>();
            addModel = true;
        }

        @Override
        public List<FavoriteModel> getFavorites() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public List<FavoriteModel> addFavorite(FavoriteModel favoriteModel) {
            if (addModel) {
                models.add(favoriteModel);
            }
            return models;
        }

        @Override
        public void registerChangeListener(ChangeListener changeListener) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public void clearChangeListener() {
            throw new RuntimeException("Not implemented");
        }
    }

}
