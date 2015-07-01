package com.blundell.udacityspotifystreamer.toptracks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        private final Duration duration;
        private final String artistName;
        private final String albumName;
        private final List<String> albumArtUrls;

        public static Track from(kaaes.spotify.webapi.android.models.Track track) {
            String name = track.name;
            String albumName = track.album.name;
            List<String> albumArtUrls = new ArrayList<>();
            for (Image image : track.album.images) {
                albumArtUrls.add(image.url);
            }
            String artistName = track.artists.toString();
            Duration duration = new Duration(track.duration_ms);
            return new Track(name, duration, artistName, albumName, albumArtUrls);
        }

        public Track(String name, Duration duration, String artistName, String albumName, List<String> albumArtUrls) {
            this.name = name;
            this.duration = duration;
            this.artistName = artistName;
            this.albumName = albumName;
            this.albumArtUrls = albumArtUrls;
        }

        public String getName() {
            return name;
        }

        public String getArtistName() {
            return artistName;
        }

        public String getAlbumName() {
            return albumName;
        }

        public List<String> getAlbumArtUrls() {
            return albumArtUrls;
        }

        public Duration getDuration() {
            return duration;
        }
    }

    public static class Duration {
        private final long durationMillis;

        public Duration(long durationMillis) {
            this.durationMillis = durationMillis;
        }

        public int toApproximateMillis() {
            return (int) durationMillis;
        }

        public String asMmSs() {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis); // TODO wrong over 60secs
            long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis); // & prob wrong place
            return minutes + ":" + seconds;
        }
    }
}
