package com.example.chaithra.gallery;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Gallery>> {
    private static final String MEO_URL = "https://api.flickr.com/services/rest/?method=flickr.people.getPhotos&api_key=9f7ca9f34d9a471e29331a24d781b6e6&user_id=71381553%40N03&format=json&nojsoncallback=1&api_sig=139db6b450c37f8786989571203ace7a";
    private static final int MEO_LOADER_ID = 1;

    GridView gridView;
    GalleryAdapter galleryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          gridView= (GridView) findViewById(R.id.gridview_android_example);
        galleryAdapter = new GalleryAdapter(this, 0);
        gridView.setAdapter(galleryAdapter);
        TextView emptystate = (TextView) findViewById(R.id.emptyview);
        emptystate.setText(("Loading"));
        boolean isonline = isOnline();
        if (isonline == false) {
            emptystate.setText("No internet");
//            View loadingIndicator = findViewById(R.id.progressbar);
//            loadingIndicator.setVisibility(View.GONE);
        } else {
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MEO_LOADER_ID, null, this);

        }
    }
    @Override
    public Loader<ArrayList<Gallery>> onCreateLoader(int id, Bundle args) {
        return new GalleryLoader(this, MEO_URL);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Gallery>> loader, ArrayList<Gallery> data) {


        galleryAdapter.clear();
        if (data != null) {
            galleryAdapter.addAll(data);
        }
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Gallery>> loader) {
        if (galleryAdapter != null) {
            galleryAdapter.clear();
        }
    }
}


