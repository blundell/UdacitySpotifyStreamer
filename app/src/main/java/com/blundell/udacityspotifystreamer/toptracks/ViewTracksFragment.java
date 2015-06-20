package com.blundell.udacityspotifystreamer.toptracks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.SpotifyApiBuilder;
import com.blundell.udacityspotifystreamer.search.Artists;
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

public class ViewTracksFragment extends Fragment {

    private Provider provider;
    private ToastDisplayer toaster;
    private TracksAdapter tracksAdapter;
    private SpotifyService spotifyService;

    private RecyclerView tracksResultsList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        provider = (Provider) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toaster = ToastDisplayers.noPendingToastsToastDisplayer(getActivity());
        tracksAdapter = new TracksAdapter();
        SpotifyApi spotifyApi = new SpotifyApiBuilder().executeOnBackgroundThread().callbackOnMainThread().create();
        spotifyService = spotifyApi.getService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_tracks, container, false);

        tracksResultsList = (RecyclerView) root.findViewById(R.id.track_list_results);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tracksResultsList.setHasFixedSize(true);
        tracksResultsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        tracksResultsList.setAdapter(tracksAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String, Object> queryParams = Collections.singletonMap("country", (Object) "GB");
        spotifyService.getArtistTopTrack(getArtist().getId(), queryParams, onTracksFoundUpdateUi);
    }

    private Artists.Artist getArtist() {
        return provider.provideArtist();
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

    interface Provider {
        Artists.Artist provideArtist();
    }
}
