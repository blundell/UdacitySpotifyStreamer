package com.blundell.udacityspotifystreamer.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blundell.udacityspotifystreamer.BuildConfig;
import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.player.PlayerActivity;
import com.blundell.udacityspotifystreamer.toptracks.ViewTracksActivity;
import com.blundell.udacityspotifystreamer.toptracks.ViewTracksFragment;
import com.novoda.notils.logger.simple.Log;

import static com.blundell.udacityspotifystreamer.toptracks.Trackz.Track;

public class SearchArtistActivity extends AppCompatActivity
        implements SearchArtistFragment.Listener,
        ViewTracksFragment.Listener, ViewTracksFragment.Provider {

    static { // Lazy mans custom application class
        Log.setShowLogs(BuildConfig.DEBUG);
    }

    private Artists.Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
    }

    @Override
    public void onClicked(Artists.Artist artist) {
        this.artist = artist;
        if (getResources().getBoolean(R.bool.isTablet)) {
            ViewTracksFragment fragment = (ViewTracksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_view_tracks);
            fragment.notifyArtistChanged();
        } else {
            Intent intent = ViewTracksActivity.createIntent(this, artist);
            startActivity(intent);
        }
    }

    @Override
    public Artists.Artist provideArtist() {
        return artist;
    }

    @Override
    public void onClicked(Track track) {
        Intent intent = PlayerActivity.createIntent(this, track);
        startActivity(intent);
    }

}
