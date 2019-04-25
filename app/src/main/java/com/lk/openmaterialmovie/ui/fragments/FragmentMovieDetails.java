/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMovieDetailsBinding;
import com.lk.openmaterialmovie.dto.TrailersResponse;
import com.lk.openmaterialmovie.helpers.Ui;

import java.text.MessageFormat;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import lombok.Getter;
import lombok.Setter;

public class FragmentMovieDetails extends BaseFragment {

    @Getter
    @Setter
    private FragmentMovieDetailsViewModel viewModel;
    private FragmentMovieDetailsBinding binding;
    private SimpleExoPlayer player;
    public static final String EXOPLAYER = "exoplayer";
    private final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

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
            TrailersResponse.TrailersResponseResults[] results = trailersResponse.getResults();
            //Play first trailer from collection
            //https://www.youtube.com/watch?v=2LqzF5WauAw
            if (results.length > 0) {
                // TODO: 2019-04-24 Check is type youtube
                //initializeWebView(YOUTUBE_URL, results[0].getKey());
                //initializePlayer(YOUTUBE_URL, results[0].getKey());
                String urlWithKey = MessageFormat.format("{0}{1}", YOUTUBE_URL, results[0].getKey());
                new YouTubeExtractor(getContext()) {
                    @Override
                    public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                        if (ytFiles != null) {
                            int itag = 22;
                            String downloadUrl = ytFiles.get(itag).getUrl();
                            initializePlayer(downloadUrl);
                        }
                    }
                }.extract(urlWithKey, true, true);

            }
        });
    }

    private void initializeWebView(String url, String key) {
        //WebView webView = binding.we;
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl(MessageFormat.format("{0}{1}", url, key));
    }

    private void initializePlayer(String url) {
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(getContext());
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector();
        DefaultLoadControl defaultLoadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getContext(), defaultRenderersFactory, defaultTrackSelector, defaultLoadControl);
        binding.playerView.setPlayer(player);
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(Ui.getActivity(), EXOPLAYER, defaultBandwidthMeter);
        final MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            player.release();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            player.release();
        }

    }
}
