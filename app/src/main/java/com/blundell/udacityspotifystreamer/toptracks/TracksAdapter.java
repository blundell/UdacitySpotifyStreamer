package com.blundell.udacityspotifystreamer.toptracks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blundell.udacityspotifystreamer.R;
import com.blundell.udacityspotifystreamer.toptracks.Trackz.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

    private final Listener listener;
    private final Trackz trackz;

    public TracksAdapter(Listener listener) {
        this.listener = listener;
        this.trackz = new Trackz();
    }

    public void setTrackz(Trackz trackz) {
        this.trackz.replaceAll(trackz);
        notifyDataSetChanged();
    }

    @Override
    public TracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(parent.getContext(), R.layout.list_item_track, null);
        return new TracksViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(TracksViewHolder holder, int position) {
        Track track = trackz.get(position);
        holder.bind(track, listener);
    }

    @Override
    public int getItemCount() {
        return trackz.total();
    }

    public static class TracksViewHolder extends RecyclerView.ViewHolder {

        private final Picasso imageLoader;
        private final ImageView trackAlbumArtImage;
        private final TextView albumNameText;
        private final TextView nameText;

        public TracksViewHolder(View itemView) {
            super(itemView);
            imageLoader = Picasso.with(itemView.getContext());
            trackAlbumArtImage = (ImageView) itemView.findViewById(R.id.track_image_album_art);
            albumNameText = (TextView) itemView.findViewById(R.id.track_text_album);
            nameText = (TextView) itemView.findViewById(R.id.track_text_name);
        }

        public void bind(Track track, Listener listener) {
            bindArt(track.getAlbumArtUrls());
            albumNameText.setText(track.getAlbumName());
            nameText.setText(track.getName());
            bindOnClick(track, listener);
        }

        private void bindArt(List<String> images) {
            if (images.isEmpty()) {
                return;
            }
            imageLoader.load(images.get(0)).into(trackAlbumArtImage);
        }

        private void bindOnClick(final Track track, final Listener listener) {
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClicked(track);
                        }
                    });
        }
    }

    interface Listener {
        void onClicked(Track track);
    }
}
