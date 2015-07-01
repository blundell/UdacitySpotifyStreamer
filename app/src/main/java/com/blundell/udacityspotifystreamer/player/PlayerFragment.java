package com.blundell.udacityspotifystreamer.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.toptracks.Trackz.Track;

public class PlayerFragment extends DialogFragment {

    private static final String ARG_TRACK = "com.blundell.udacityspotifystreamer.player.ARG_TRACK";

    private TextView artistLabel;

    public static PlayerFragment newInstance(Track track) {
        PlayerFragment fragment = new PlayerFragment();
        fragment.setArguments(track);
        return fragment;
    }

    private void setArguments(Track track) {
        Bundle args = new Bundle(1);
        args.putSerializable(ARG_TRACK, track);
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player, container, false);

        artistLabel = (TextView) root.findViewById(R.id.player_text_artist);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Track track = getTrackArg();
        artistLabel.setText(track.getName());
    }

    private Track getTrackArg() {
        return (Track) getArguments().getSerializable(ARG_TRACK);
    }
}
