/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.ui.fragments.MovieDetailsViewModel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@Ignore("Ignore on local builds")
public class MovieDetailsViewModelTest {

    private final String TITLE = "Title";
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule;
    private MovieDetailsViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Movie movie = new Movie();
        movie.setTitle(TITLE);
        viewModel = new MovieDetailsViewModel.ViewModelDetailsFactory(movie).create(MovieDetailsViewModel.class);
    }

    @Test
    public void openDetails_whenMovieTitleNotEmpty() {
        instantTaskExecutorRule = new InstantTaskExecutorRule();
        assertEquals(viewModel.getMovie().title, TITLE);
    }
}
