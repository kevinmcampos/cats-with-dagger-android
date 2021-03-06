package me.kevincampos.catsdagger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.List;

import me.kevincampos.catsdagger.cat_api.FetchCatImagesUseCase;
import me.kevincampos.catsdagger.di.ListActivityDIComponent;
import me.kevincampos.catsdagger.favorites.AddFavoriteUseCase;

public class ListActivity extends AppCompatActivity {

    static public void launch(Context context) {
        Intent intent = new Intent(context, ListActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView recyclerView;

    private AddFavoriteUseCase addFavoriteUseCase;
    private FetchCatImagesUseCase fetchCatImagesUseCase;

    public void injectAddFavoriteUseCase(AddFavoriteUseCase addFavoriteUseCase) {
        this.addFavoriteUseCase = addFavoriteUseCase;
    }

    public void injectFetchCatImagesUseCase(FetchCatImagesUseCase fetchCatImagesUseCase) {
        this.fetchCatImagesUseCase = fetchCatImagesUseCase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new ListActivityDIComponent().inject(this);

        recyclerView = findViewById(R.id.list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ImagesRvAdapter adapter = new ImagesRvAdapter(new ImagesRvAdapter.ImageOnClick() {
            @Override
            public void imageClicked(ImageView view, String url) {
                addUrlToUserFavoritesList(view, url);
            }
        });
        recyclerView.setAdapter(adapter);

        fetchCatImagesUseCase.fetchImages(new FetchCatImagesUseCase.Callback() {
            @Override
            public void imagesUrls(List<String> urls) {
                adapter.updateImageUrls(urls);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addUrlToUserFavoritesList(ImageView view, String url) {
        boolean imageAdded = addFavoriteUseCase.addFavoriteUrl(url);

        int msgId;
        if (imageAdded) {
            msgId = R.string.list_user_favorite_url_added_success;

            new ParticleSystem(ListActivity.this, 500, R.mipmap.azunyan_2, 2000)
                    .setAcceleration(0.0005f, 90)
                    .setSpeedRange(0.2f, 0.5f)
                    .setRotationSpeedRange(90, 180)
                    .setInitialRotationRange(0, 180)
                    .setFadeOut(500)
                    .setScaleRange(0.1f, 1.0f)
                    .oneShot(view, 100);

        } else {
            msgId = R.string.list_user_favorite_url_already_in;
        }
        Snackbar.make(recyclerView, msgId, Snackbar.LENGTH_SHORT)
                .show();
    }
}
