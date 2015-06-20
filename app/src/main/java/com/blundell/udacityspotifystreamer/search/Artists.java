package com.blundell.udacityspotifystreamer.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Image;

public class Artists implements Serializable {

    private final List<Artist> artists;

    public static Artists from(List<kaaes.spotify.webapi.android.models.Artist> apiArtists) {
        List<Artist> artists = new ArrayList<>();
        for (kaaes.spotify.webapi.android.models.Artist artist : apiArtists) {
            artists.add(Artist.from(artist));
        }
        return new Artists(artists);
    }

    public Artists() {
        this(new ArrayList<Artist>());
    }

    Artists(List<Artist> artists) {
        this.artists = artists;
    }

    public Artist get(int position) {
        return artists.get(position);
    }

    public void replaceAll(Artists artists) {
        this.artists.clear();
        this.artists.addAll(artists.artists);
    }

    public int total() {
        return artists.size();
    }

    public static class Artist implements Serializable {

        private final String name;
        private final List<String> images;
        private final String id;

        public static Artist from(kaaes.spotify.webapi.android.models.Artist artist) {
            List<Image> apiImages = artist.images;
            List<String> images = new ArrayList<>();
            for (Image image : apiImages) {
                images.add(image.url);
            }
            return new Artist(artist.id, artist.name, images);
        }

        Artist(String id, String name, List<String> images) {
            this.id = id;
            this.name = name;
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<String> getImages() {
            return images;
        }
    }

}
