/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import com.lk.openmaterialmovie.Strings;
import com.lk.openmaterialmovie.databinding.FragmentMovieDetailsBinding;
import com.lk.openmaterialmovie.dto.TrailerDto;
import com.lk.openmaterialmovie.dto.TrailersResponse;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.log.Logger;

import java.text.MessageFormat;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import lombok.Getter;
import lombok.Setter;

public class FragmentMovieDetails extends BaseFragment {

    public static final String EXOPLAYER = "exoplayer";

    @Getter
    @Setter
    private MovieDetailsViewModel viewModel;
    private FragmentMovieDetailsBinding binding;
    private SimpleExoPlayer player;
    private final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private String videoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        }
        if (videoUrl == null || videoUrl.isEmpty()) {
            viewModel.getTrailers().onChangeOnce(this, trailers -> {
                TrailersResponse trailersResponse = (TrailersResponse) trailers.getData();
                TrailerDto[] results = trailersResponse.getResults();
                if (results.length > 0) {
                    String urlWithKey = MessageFormat.format("{0}{1}", YOUTUBE_URL, results[0].getKey());
                    // TODO: 2019-04-25 Remove third party libs
                    new YouTubeExtractor(getContext()) {
                        @Override
                        public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                            if (isAdded()) {
                                if (ytFiles != null) {
                                    final int tag = 22;
                                    if (ytFiles.get(tag) != null) {
                                        String downloadUrl = ytFiles.get(tag).getUrl();
                                        initializePlayer(downloadUrl);
                                    } else {
                                        Logger.d(Strings.Log.FAIL);
                                    }
                                }
                            }

                        }
                    }.extract(urlWithKey, true, true);
                }
            });
        }

    }

    private void initializePlayer(String url) {
        videoUrl = url;
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(getContext());
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector();
        DefaultLoadControl defaultLoadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getContext(), defaultRenderersFactory, defaultTrackSelector, defaultLoadControl);
        binding.videoView.setShutterBackgroundColor(Color.TRANSPARENT);
        binding.controls.setPlayer(player);
        binding.videoView.setPlayer(player);
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(Ui.getActivity(), EXOPLAYER, defaultBandwidthMeter);
        final MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);
    }

    private void playerRelease() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if (player != null) {
                player.release();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerRelease();
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void startPlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startPlayer();
    }

}
