package com.blundell.udacityspotifystreamer.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blundell.udacityspotifystreamer.search.Artists.Artist;
import com.blundell.udacityspotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    private final Artists artists;
    private final Listener listener;

    public ArtistsAdapter(Listener listener) {
        this.listener = listener;
        this.artists = new Artists();
    }

    public void setArtists(Artists artists) {
        this.artists.replaceAll(artists);
        notifyDataSetChanged();
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemLayout = View.inflate(viewGroup.getContext(), R.layout.list_item_artist, null);
        return new ArtistViewHolder(itemLayout);
    }

    @Override
    public int getItemCount() {
        return artists.total();
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder artistViewHolder, int position) {
        Artist artist = artists.get(position);
        artistViewHolder.bind(artist, listener);
    }

    interface Listener {
        void onClicked(Artist artist);
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

        public void bind(final Artist artist, final Listener listener) {
            bindArt(artist.getImages());
            bindText(artist.getName());
            bindOnClick(artist, listener);
        }

        private void bindArt(List<String> art) {
            if (art.isEmpty()) {
                return;
            }
            imageLoader.load(art.get(0)).into(albumArtImage);
        }

        private void bindText(String name) {
            nameText.setText(name);
        }

        private void bindOnClick(final Artist artist, final Listener listener) {
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClicked(artist);
                        }
                    });
        }

    }
}
