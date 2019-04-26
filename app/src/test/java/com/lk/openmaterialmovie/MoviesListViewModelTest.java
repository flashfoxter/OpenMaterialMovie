/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.lk.openmaterialmovie.ui.fragments.MoviesListViewModel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotSame;

@Ignore("Ignore on local builds")
public class MoviesListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule;
    private MoviesListViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MoviesListViewModel();
        viewModel.getMoviesList(1);
    }

    @Test
    public void loadMovies_whenMoviesListNotEmpty() {
        instantTaskExecutorRule = new InstantTaskExecutorRule();
        assertNotSame(viewModel.getMoviesList(1).getValue(), 0);
    }
}
