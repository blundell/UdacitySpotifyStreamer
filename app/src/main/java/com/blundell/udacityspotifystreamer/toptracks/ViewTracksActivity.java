package com.blundell.udacityspotifystreamer.toptracks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.search.Artists;

public class ViewTracksActivity extends AppCompatActivity implements ViewTracksFragment.Provider {

    private static final String EXTRA_ARTIST = "com.blundell.udacityspotifystreamer.EXTRA_ARTIST";

    private Artists.Artist artist;

    public static Intent createIntent(Context context, Artists.Artist artist) {
        Intent intent = new Intent(context, ViewTracksActivity.class);
        intent.putExtra(ViewTracksActivity.EXTRA_ARTIST, artist);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artist = (Artists.Artist) getIntent().getSerializableExtra(EXTRA_ARTIST);
        if (artist == null) {
            throw new InstantiationError("Use the createIntent() method and ensure you pass non null params.");
        }
        setContentView(R.layout.activity_view_tracks);
    }

    @Override
    public Artists.Artist provideArtist() {
        return artist;
    }
}
