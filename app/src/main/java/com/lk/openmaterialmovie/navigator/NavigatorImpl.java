/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.navigator;

import android.support.v4.app.FragmentTransaction;

import com.lk.openmaterialmovie.ui.activities.BaseActivity;
import com.lk.openmaterialmovie.ui.fragments.BaseFragment;


public class NavigatorImpl implements Navigator {
    @Override
    public void setFragment(BaseFragment from, BaseFragment to, BaseActivity activity) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (from != null) {
            fragmentTransaction.addToBackStack(from.getClass().getName());
        }
        fragmentTransaction.replace(activity.getFragmentContainer(), to);
        fragmentTransaction.commit();
    }
}
