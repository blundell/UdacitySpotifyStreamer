package com.blundell.udacityspotifystreamer.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blundell.udacityspotifystreamer.BuildConfig;
import com.blundell.udacityspotifystreamer.R;
import com.novoda.notils.logger.simple.Log;

public class SearchArtistActivity extends AppCompatActivity {

    static { // Lazy mans custom application class
        Log.setShowLogs(BuildConfig.DEBUG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
    }

}
