package com.blundell.udacityspotifystreamer.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.SpotifyApiBuilder;
import com.novoda.notils.logger.simple.Log;
import com.novoda.notils.logger.toast.ToastDisplayer;
import com.novoda.notils.logger.toast.ToastDisplayers;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.client.Response;

public class SearchArtistFragment extends Fragment {

    private Listener listener;
    private ToastDisplayer toaster;
    private SpotifyService spotifyService;
    private ArtistsAdapter artistsAdapter;

    private RecyclerView artistResultsList;
    private EditText searchArtistInputBox;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toaster = ToastDisplayers.noPendingToastsToastDisplayer(getActivity());
        SpotifyApi spotifyApi = new SpotifyApiBuilder().executeOnBackgroundThread().callbackOnMainThread().create();
        spotifyService = spotifyApi.getService();
        artistsAdapter = new ArtistsAdapter(onArtistClickedViewTracks);
    }

    private final ArtistsAdapter.Listener onArtistClickedViewTracks = new ArtistsAdapter.Listener() {
        @Override
        public void onClicked(Artists.Artist artist) {
            listener.onClicked(artist);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_artist, container, false);

        artistResultsList = (RecyclerView) root.findViewById(R.id.search_artist_list_results);
        searchArtistInputBox = (EditText) root.findViewById(R.id.search_artist_box_input);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artistResultsList.setHasFixedSize(true);
        artistResultsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistResultsList.setAdapter(artistsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        searchArtistInputBox.addTextChangedListener(onChangedQueryApi);
    }

    private final OnChangedTextWatcher onChangedQueryApi = new OnChangedTextWatcher() {
        @Override
        void onTextChanged(String text) {
            if (text.isEmpty()) {
                return;
            }
            spotifyService.searchArtists(text, onArtistsFoundUpdateView);
        }
    };
    private final SpotifyCallback<ArtistsPager> onArtistsFoundUpdateView = new SpotifyCallback<ArtistsPager>() {
        @Override
        public void success(ArtistsPager artistsPager, Response response) {
            if (artistsPager.artists.items.isEmpty()) {
                popToast(R.string.error_no_artist_results);
            }
            artistsAdapter.setArtists(Artists.from(artistsPager.artists.items));
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

    interface Listener {
        void onClicked(Artists.Artist artist);
    }
}
