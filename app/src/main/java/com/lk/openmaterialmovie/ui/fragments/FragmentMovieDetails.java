/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMovieDetailsBinding;
import com.lk.openmaterialmovie.dto.trailers.TrailersResponse;
import com.lk.openmaterialmovie.dto.trailers.TrailersResponseResults;

import java.text.MessageFormat;

import lombok.Getter;
import lombok.Setter;

public class FragmentMovieDetails extends BaseFragment {

    private final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    @Getter
    @Setter
    private FragmentMovieDetailsViewModel viewModel;
    private FragmentMovieDetailsBinding binding;
    private SimpleExoPlayer player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        //initializePlayer();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewModel = ViewModelProviders.of(this).get(FragmentMovieDetailsViewModel.class);
        viewModel.getTrailers().onChangeOnce(this, trailers -> {
            TrailersResponse trailersResponse = (TrailersResponse) trailers.getData();
            TrailersResponseResults[] results = trailersResponse.getResults();
            //Play first trailer from collection
            //https://www.youtube.com/watch?v=2LqzF5WauAw
            if (results.length > 0) {
                // TODO: 2019-04-24 Check is type youtube
                initializeWebView(YOUTUBE_URL, results[0].getKey());
            }
        });
    }

    private void initializeWebView(String url, String key) {
        WebView webView = binding.webView;
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(MessageFormat.format("{0}{1}", url, key));
    }

    private void initializePlayer(String url, String key) {
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(getContext());
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector();
        DefaultLoadControl defaultLoadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getContext(), defaultRenderersFactory, defaultTrackSelector, defaultLoadControl);
        //binding.playerView.setPlayer(player);

/*
        final MediaSource videoSource = new ExtractorMediaSource(MessageFormat.format("{0}{1}", YOUTUBE_URL, ), new DefaultDataSourceFactory(), new DefaultExtractorsFactory(),
                null, null);
*/
    }
}
