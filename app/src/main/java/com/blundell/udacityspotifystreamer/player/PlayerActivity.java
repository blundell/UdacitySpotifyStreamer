package com.blundell.udacityspotifystreamer.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.blundell.udacityspotifystreamer.R;

import static com.blundell.udacityspotifystreamer.toptracks.Trackz.Track;

public class PlayerActivity extends AppCompatActivity {

    private static final String EXTRA_TRACK = "com.blundell.udacityspotifystreamer.player.EXTRA_TRACK";

    public static Intent createIntent(Context context, Track track) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(EXTRA_TRACK, track);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Track track = (Track) getIntent().getSerializableExtra(EXTRA_TRACK);
        PlayerFragment playerFragment = PlayerFragment.newInstance(track);
        transaction.replace(R.id.player_fragment_holder, playerFragment);
        transaction.commit();
    }
}
