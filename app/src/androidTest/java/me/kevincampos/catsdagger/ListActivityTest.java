package me.kevincampos.catsdagger;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.kevincampos.catsdagger.cat_api.FetchCatImagesUseCase;
import me.kevincampos.catsdagger.cat_api.TheCatAPI;
import me.kevincampos.catsdagger.di.ListActivityDIModule;
import me.kevincampos.catsdagger.favorites.AddFavoriteUseCase;
import me.kevincampos.catsdagger.favorites.FavoriteRepository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListActivityTest {

    @Rule
    public ActivityTestRule<ListActivity> activityTestRule =
            new ActivityTestRule<>(ListActivity.class, true, false);

    @Test
    public void testThereAreThreeImages() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("url0");
        expectedUrls.add("url1");
        expectedUrls.add("url2");

        setUpListCatImages(expectedUrls);

        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.list_rv)).check(new RecyclerViewItemCountAssertion(3));
    }

    @Test
    public void testImageTouchAddFavorites() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("url0");

        setUpListCatImages(expectedUrls);

        AddFavoriteUseCase mock = mock(AddFavoriteUseCase.class);
        when(mock.addFavoriteUrl("url0")).thenReturn(true);
        ListActivityDIModule.testAddFavoriteUseCase = mock;

        activityTestRule.launchActivity(new Intent());

        // We have to wait because we are not mocking everything yet.
        // Picasso still trying to fetch images from the network!
        SystemClock.sleep(500);

        onView(withId(R.id.list_rv)).check(new RecyclerViewItemCountAssertion(1));
        // TODO: Use click() instead of recyclerClick()
        onView(withId(R.id.list_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, recyclerClick()));

        verify(mock, times(1)).addFavoriteUrl("url0");
    }



    private void setUpListCatImages(final List<String> urls) {
        ListActivityDIModule.testAddFavoriteUseCase =  // NoOp
                new AddFavoriteUseCase(mock(FavoriteRepository.class));

        ListActivityDIModule.testFetchCatImagesUseCase = new FetchCatImagesUseCase(mock(TheCatAPI.class)) {
            @Override
            public void fetchImages(Callback callback) {
                callback.imagesUrls(urls);
            }
        };
    }

    private static ViewAction recyclerClick() {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return any(View.class);
            }

            @Override
            public String getDescription() {
                return "performing click() on recycler view item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        };
    }
}
