package com.lk.openmaterialmovie;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.ui.fragments.FragmentMovieDetails;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FragmentMoviesByGenreTest extends BaseUiTest {

    public static final int RANDOM_OPEN_DETAILS = 15;
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private RecyclerView recyclerView;
    private int swipeCount = 0;

    @Test
    public void moviesListTest() throws InterruptedException {
        recyclerView = activityActivityTestRule.getActivity().findViewById(R.id.recycler_movies);
        Random openDetailsRandom = new Random();

        do {
            waitFor(3000);
            recyclerScrollToEnd(recyclerView);
            swipeCount += 1;
            waitFor(3000);
            int openDetails = openDetailsRandom.nextInt(RANDOM_OPEN_DETAILS);
            if (openDetails == 1) {
                recyclerClick(recyclerView);
                //Click add to favor
                if (Ui.getCurrentFragment() instanceof FragmentMovieDetails) {
                    waitFor(1000);
                    back();
                    waitFor(3000);
                }
            }
        }
        while (swipeByCount());
    }

    private boolean swipeByAdapter() {
        return recyclerView.getAdapter().getItemCount() < 100000;
    }

    private boolean swipeByCount() {
        return swipeCount < 100000;
    }
}
