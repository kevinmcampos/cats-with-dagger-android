package me.kevincampos.catsdagger;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import me.kevincampos.catsdagger.di.FavoritesActivityDIModule;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;
import me.kevincampos.catsdagger.favorites.GetFavoritesUseCase;

import static android.support.test.espresso.Espresso.onView;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class FavoritesActivityTest {

    @Rule
    public ActivityTestRule<FavoritesActivity> activityRule =
            new ActivityTestRule<>(FavoritesActivity.class, true, false);

    @Test
    public void testThereAreThreeImages() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("url0");
        expectedUrls.add("url1");
        expectedUrls.add("url2");
        setUpGetFavoritesResponse(expectedUrls);

        activityRule.launchActivity(new Intent());

        onView(ViewMatchers.withId(R.id.favorites_rv)).check(new RecyclerViewItemCountAssertion(3));
    }

    @Test
    public void testThereAreZeroImages() {
        final ArrayList<String> emptyUrls = new ArrayList<>();
        setUpGetFavoritesResponse(emptyUrls);

        activityRule.launchActivity(new Intent());

        onView(ViewMatchers.withId(R.id.favorites_rv)).check(new RecyclerViewItemCountAssertion(0));
    }

    private void setUpGetFavoritesResponse(final List<String> urls) {
        FavoritesActivityDIModule.testGetFavoritesUseCase = new GetFavoritesUseCase(mock(FavoriteRepository.class)) {
            @Override
            public void getFavoritesUrl(Callback callback) {
                callback.favoriteUrlsUpdated(urls);
            }
        };
    }

}