package com.blundell.udacityspotifystreamer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    private final List<Artist> artists = new ArrayList<>();

    public void setArtists(List<Artist> artists) {
        this.artists.clear();
        this.artists.addAll(artists);
        notifyDataSetChanged();
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemLayout = View.inflate(viewGroup.getContext(), R.layout.list_item_artist, null);
        return new ArtistViewHolder(itemLayout);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder artistViewHolder, int position) {
        artistViewHolder.bind(artists.get(position));
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {

        private final Picasso imageLoader;
        private final ImageView albumArtImage;
        private final TextView nameText;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            imageLoader = Picasso.with(itemView.getContext());
            albumArtImage = (ImageView) itemView.findViewById(R.id.artist_image_album_art);
            nameText = (TextView) itemView.findViewById(R.id.artist_text_name);
        }

        public void bind(Artist artist) {
            bindArt(artist.images);
            nameText.setText(artist.name);
        }

        private void bindArt(List<Image> art) {
            if (art.isEmpty()) {
                return;
            }
            imageLoader.load(art.get(0).url).into(albumArtImage);
        }
    }
}
