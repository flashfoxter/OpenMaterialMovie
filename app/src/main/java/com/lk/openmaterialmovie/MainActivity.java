package com.lk.openmaterialmovie;

import android.os.Bundle;

import com.lk.openmaterialmovie.helpers.Provider;
import com.lk.openmaterialmovie.navigator.Navigate;
import com.lk.openmaterialmovie.ui.activities.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigate.toFragment(null, Provider.getFragmentMoviesByGenre());
    }
}
