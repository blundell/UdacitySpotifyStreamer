package com.blundell.udacityspotifystreamer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kaaes.spotify.webapi.android.SpotifyApi;
import retrofit.android.MainThreadExecutor;

public final class SpotifyApiBuilder {

    private Executor executeExecutor;
    private Executor callbackExecutor;

    public SpotifyApiBuilder() {
        this.executeExecutor = Executors.newSingleThreadExecutor();
        this.callbackExecutor = new MainThreadExecutor();
    }

    public SpotifyApiBuilder executeOnBackgroundThread() {
        executeOn(Executors.newSingleThreadExecutor());
        return this;
    }

    public SpotifyApiBuilder executeOnMainThread() {
        executeOn(new MainThreadExecutor());
        return this;
    }

    public SpotifyApiBuilder executeOn(Executor executor) {
        this.executeExecutor = executor;
        return this;
    }

    public SpotifyApiBuilder callbackOnBackgroundThread() {
        callbackOn(Executors.newSingleThreadExecutor());
        return this;
    }

    public SpotifyApiBuilder callbackOnMainThread() {
        callbackOn(new MainThreadExecutor());
        return this;
    }

    public SpotifyApiBuilder callbackOn(Executor executor) {
        this.callbackExecutor = executor;
        return this;
    }

    public SpotifyApi create() {
        return new SpotifyApi(executeExecutor, callbackExecutor);
    }

}
