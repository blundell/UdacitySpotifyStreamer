package com.blundell.udacityspotifystreamer.toptracks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Image;

public class Trackz {

    private final List<Track> tracks;

    public static Trackz from(List<kaaes.spotify.webapi.android.models.Track> apiTracks) {
        List<Track> artists = new ArrayList<>();
        for (kaaes.spotify.webapi.android.models.Track track : apiTracks) {
            artists.add(Track.from(track));
        }
        return new Trackz(artists);
    }

    public Trackz() {
        this(new ArrayList<Track>());
    }

    public Trackz(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void replaceAll(Trackz trackz) {
        this.tracks.clear();
        this.tracks.addAll(trackz.tracks);
    }

    public Track get(int position) {
        return tracks.get(position);
    }

    public int total() {
        return tracks.size();
    }

    public static class Track implements Serializable {

        private final String name;
        private final String albumName;
        private final List<String> albumArtUrls;

        public static Track from(kaaes.spotify.webapi.android.models.Track track) {
            String name = track.name;
            String albumName = track.album.name;
            List<String> albumArtUrls = new ArrayList<>();
            for (Image image : track.album.images) {
                albumArtUrls.add(image.url);
            }
            return new Track(name, albumName, albumArtUrls);
        }

        public Track(String name, String albumName, List<String> albumArtUrls) {
            this.name = name;
            this.albumName = albumName;
            this.albumArtUrls = albumArtUrls;
        }

        public String getName() {
            return name;
        }

        public String getAlbumName() {
            return albumName;
        }

        public List<String> getAlbumArtUrls() {
            return albumArtUrls;
        }
    }
}
