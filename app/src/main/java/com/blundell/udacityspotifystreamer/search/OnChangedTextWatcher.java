package com.blundell.udacityspotifystreamer.search;

import android.text.Editable;
import android.text.TextWatcher;

abstract class OnChangedTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // not used
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        // not used
    }

    abstract void onTextChanged(String text);
}
