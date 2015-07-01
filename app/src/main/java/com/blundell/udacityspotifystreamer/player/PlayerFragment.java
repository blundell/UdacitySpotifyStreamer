package com.blundell.udacityspotifystreamer.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.toptracks.Trackz.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayerFragment extends DialogFragment {

    private static final String ARG_TRACK = "com.blundell.udacityspotifystreamer.player.ARG_TRACK";
    private static final String KEY_STATE_PLAY_BUTTON_VISIBLE = "com.blundell.udacityspotifystreamer.player.key.PLAY_BUTTON_VISIBLE";
    private static final String KEY_STATE_PAUSE_BUTTON_VISIBLE = "com.blundell.udacityspotifystreamer.player.key.PAUSE_BUTTON_VISIBLE";

    private TextView artistNameLabel;
    private TextView trackNameLabel;
    private TextView albumnNameLabel;
    private ImageView trackAlbumArtImage;
    private Picasso imageLoader;
    private SeekBar trackDurationBar;
    private TextView trackTimePositionLabel;
    private TextView trackDurationLabel;
    private View previousTrackButton;
    private View trackPlayButton;
    private View trackPauseButton;
    private View nextTrackButton;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = Picasso.with(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player, container, false);

        artistNameLabel = (TextView) root.findViewById(R.id.player_text_artist);
        albumnNameLabel = (TextView) root.findViewById(R.id.player_text_album);
        trackNameLabel = (TextView) root.findViewById(R.id.player_text_track);
        trackAlbumArtImage = (ImageView) root.findViewById(R.id.player_image_album_art);
        trackDurationBar = (SeekBar) root.findViewById(R.id.player_progress_track_duration);
        trackTimePositionLabel = (TextView) root.findViewById(R.id.player_text_time_position);
        trackDurationLabel = (TextView) root.findViewById(R.id.player_text_duration);
        previousTrackButton = root.findViewById(R.id.player_button_previous);
        trackPlayButton = root.findViewById(R.id.player_button_play);
        trackPauseButton = root.findViewById(R.id.player_button_pause);
        nextTrackButton = root.findViewById(R.id.player_button_next);

        return root;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        trackPlayButton.setVisibility(savedInstanceState.getBoolean(KEY_STATE_PLAY_BUTTON_VISIBLE) ? View.VISIBLE : View.GONE);
        trackPauseButton.setVisibility(savedInstanceState.getBoolean(KEY_STATE_PAUSE_BUTTON_VISIBLE) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Track track = getTrackArg();
        artistNameLabel.setText(track.getArtistName());
        albumnNameLabel.setText(track.getAlbumName());
        trackNameLabel.setText(track.getName());
        bindArt(track.getAlbumArtUrls());
        trackDurationBar.setMax(track.getDuration().toApproximateMillis());
        trackDurationBar.setOnSeekBarChangeListener(
                new SeekBarProgressChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        trackTimePositionLabel.setText(String.valueOf(progress)); // TODO
                    }
                }
        );
        trackDurationLabel.setText(track.getDuration().asMmSs());
        previousTrackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "PREVIOUS", Toast.LENGTH_SHORT).show();
                        // TODO inform activity we want the previous track
                    }
                });
        nextTrackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "NEXT", Toast.LENGTH_SHORT).show();
                        // TODO inform activity we want the next track
                    }
                });
        trackPlayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                        trackPauseButton.setVisibility(View.VISIBLE);
                        Toast.makeText(v.getContext(), "PLAY", Toast.LENGTH_SHORT).show();
                        // TODO use MediaPlayer to play the track
                    }
                });
        trackPauseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                        trackPlayButton.setVisibility(View.VISIBLE);
                        Toast.makeText(v.getContext(), "PAUSE", Toast.LENGTH_SHORT).show();
                        // TODO use MediaPlayer to pause the track
                    }
                });
    }

    private void bindArt(List<String> images) {
        if (images.isEmpty()) {
            return;
        }
        imageLoader.load(images.get(0)).into(trackAlbumArtImage);
    }

    private Track getTrackArg() {
        return (Track) getArguments().getSerializable(ARG_TRACK);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_STATE_PLAY_BUTTON_VISIBLE, trackPlayButton.getVisibility() == View.VISIBLE);
        outState.putBoolean(KEY_STATE_PAUSE_BUTTON_VISIBLE, trackPauseButton.getVisibility() == View.VISIBLE);
    }
}
