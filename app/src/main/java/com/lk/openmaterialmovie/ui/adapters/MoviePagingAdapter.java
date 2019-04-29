/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.adapters;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.Consumer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.data.NetworkState;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.enums.PlaceHolderType;
import com.lk.openmaterialmovie.ui.fragments.FragmentMoviesList;
import com.lk.openmaterialmovie.ui.viewholders.MoviesViewHolder;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MoviePagingAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };
    private NetworkState networkState;
    private LayoutInflater inflater;
    @Getter
    @Setter
    private Consumer<Movie> onSelected;
    private FragmentMoviesList fragmentMoviesList;

    public MoviePagingAdapter(FragmentMoviesList fragmentMoviesList) {
        super(DIFF_CALLBACK);
        this.fragmentMoviesList = fragmentMoviesList;
        inflater = LayoutInflater.from(fragmentMoviesList.getContext());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // TODO: 2019-04-29 Refactor to generic databinding
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(DataBindingUtil.inflate(inflater, R.layout.cell_movies, viewGroup, false));
        moviesViewHolder.itemView.setOnClickListener(v -> {
            Movie item = getItem(i);
            fragmentMoviesList.onMovieSelected(moviesViewHolder);
        });
        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        // TODO: 2019-04-29 Refactor to generic databinding
        MoviesViewHolder viewHolderMovie = (MoviesViewHolder) viewHolder;
        Movie movie = getItem(i);
        if (movie != null) {
            viewHolderMovie.b.txtTitle.setText(movie.title);
            viewHolderMovie.b.txtReleaseDate.setText(movie.release_date);
            viewHolderMovie.b.imgCover.load(movie.poster_path, PlaceHolderType.MOVIE);
        } else {
            //clear
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public List<Movie> getItems() {
        return getCurrentList().snapshot();
    }
}
