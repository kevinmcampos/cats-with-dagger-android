package me.kevincampos.catsdagger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.kevincampos.catsdagger.di.FavoritesActivityDIComponent;
import me.kevincampos.catsdagger.favorites.GetFavoritesUseCase;

public class FavoritesActivity extends AppCompatActivity {

    private static String TAG = "ImagesRvAdapter";

    static public void launch(Context context, boolean clearTop) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        if (clearTop) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    private RecyclerView recyclerView;
    private ImagesRvAdapter rvAdapter;

    private GetFavoritesUseCase getFavoritesUseCase;

    public void injectGetFavoritesUserCase(GetFavoritesUseCase getFavoritesUseCase) {
        this.getFavoritesUseCase = getFavoritesUseCase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.launch(FavoritesActivity.this);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.favorites_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new ImagesRvAdapter(null);
        recyclerView.setAdapter(rvAdapter);

        new FavoritesActivityDIComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFavoritesUseCase.getFavoritesUrl(new GetFavoritesUseCase.Callback() {
            @Override
            public void favoriteUrlsUpdated(List<String> favoriteUrls) {
                Log.d(TAG, "Updated favorites: " + favoriteUrls.toString());
                rvAdapter.updateImageUrls(favoriteUrls);
            }
        });
    }

    @Override
    protected void onPause() {
        getFavoritesUseCase.clear();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getFavoritesUseCase = null;
        super.onDestroy();
    }
}
