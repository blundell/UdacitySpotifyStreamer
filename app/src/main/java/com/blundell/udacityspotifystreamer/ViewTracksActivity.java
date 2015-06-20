package com.blundell.udacityspotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.ToastDisplayer;
import com.novoda.notils.logger.toast.ToastDisplayers;

import java.util.Collections;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.client.Response;

public class ViewTracksActivity extends AppCompatActivity {

    private static final String EXTRA_ARTIST = "com.blundell.udacityspotifystreamer.EXTRA_ARTIST";

    private ToastDisplayer toaster;
    private TracksAdapter tracksAdapter;

    public static Intent createIntent(Context context, Artists.Artist artist) {
        Intent intent = new Intent(context, ViewTracksActivity.class);
        intent.putExtra(ViewTracksActivity.EXTRA_ARTIST, artist);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Artists.Artist artist = (Artists.Artist) getIntent().getSerializableExtra(EXTRA_ARTIST);
        if (artist == null) {
            throw new InstantiationError("Use the createIntent() method and ensure you pass non null params.");
        }
        setContentView(R.layout.activity_view_tracks);
        toaster = ToastDisplayers.noPendingToastsToastDisplayer(this);

        RecyclerView tracksResultsList = (RecyclerView) findViewById(R.id.track_list_results);
        tracksResultsList.setHasFixedSize(true);
        tracksResultsList.setLayoutManager(new LinearLayoutManager(this));
        tracksAdapter = new TracksAdapter();
        tracksResultsList.setAdapter(tracksAdapter);

        SpotifyApi spotifyApi = new SpotifyApiBuilder().executeOnBackgroundThread().callbackOnMainThread().create();
        SpotifyService spotifyService = spotifyApi.getService();
        Map<String, Object> queryParams = Collections.singletonMap("country", (Object) "GB");
        spotifyService.getArtistTopTrack(artist.getId(), queryParams, onTracksFoundUpdateUi);
    }

    private final SpotifyCallback<Tracks> onTracksFoundUpdateUi = new SpotifyCallback<Tracks>() {
        @Override
        public void success(Tracks tracks, Response response) {
            if (tracks.tracks.isEmpty()) {
                popToast(R.string.error_no_track_results);
            }
            tracksAdapter.setTracks(tracks.tracks);
        }

        @Override
        public void failure(SpotifyError spotifyError) {
            Log.e(spotifyError);
            popToast(R.string.fubar_error_message);
        }
    };

    private void popToast(int message) {
        toaster.display(message);
    }
}
