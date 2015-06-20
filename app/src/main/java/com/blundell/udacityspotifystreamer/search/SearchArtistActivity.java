package com.blundell.udacityspotifystreamer.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blundell.udacityspotifystreamer.BuildConfig;
import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.toptracks.ViewTracksActivity;
import com.novoda.notils.logger.simple.Log;

public class SearchArtistActivity extends AppCompatActivity implements SearchArtistFragment.Listener {

    static { // Lazy mans custom application class
        Log.setShowLogs(BuildConfig.DEBUG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
    }

    @Override
    public void onClicked(Artists.Artist artist) {
        Intent intent = ViewTracksActivity.createIntent(this, artist);
        startActivity(intent);
    }
}
