package com.blundell.udacityspotifystreamer.player;

import android.widget.SeekBar;

public abstract class SeekBarProgressChangeListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // no-op
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //no-op
    }
}
