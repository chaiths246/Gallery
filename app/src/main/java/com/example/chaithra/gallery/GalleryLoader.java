package com.example.chaithra.gallery;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chaithra on 3/30/18.
 */



/**
 * Created by chaithra on 3/12/18.
 */

public class GalleryLoader extends AsyncTaskLoader<ArrayList<Gallery>> {
    public static final String LOG_TAG = GalleryLoader.class.getSimpleName();
    private String url;

    public GalleryLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Gallery> loadInBackground() {
        if (url == null) {
            return null;
        }
        ArrayList<Gallery> newslist = Utils.fetchMeowData(url);
        if (newslist.size() == 0) {
            Log.d(LOG_TAG, "news list is empty");
        }
        return newslist;
    }
}
