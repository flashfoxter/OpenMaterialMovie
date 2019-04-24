/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.helpers.Provider;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.ui.fragments.BaseFragment;


public class Navigate {

    public static void popTransaction(Fragment fragment, int popCount) {
        FragmentManager fm = Ui.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
        for (int i = 0; i < popCount; i++) {
            fm.popBackStack();
        }
    }

    public static void back() {
        Ui.getFragmentManager().popBackStack();
    }

    private static void clearBackStack() {
        FragmentManager fm = Ui.getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public static BaseFragment getCurrentFragment() {
        return (BaseFragment) Ui.getFragmentManager().findFragmentById(R.id.fragment_container);
    }

    public static void toFragment(BaseFragment fromFragment, Class<? extends BaseFragment> toFragmentClass) {
        toFragment(fromFragment, Provider.getFragment(toFragmentClass));
    }

    @SuppressWarnings("WeakerAccess")
    public static void toFragment(BaseFragment fromFragment, BaseFragment toFragment) {
        FragmentTransaction fragmentTransaction = Ui.getFragmentManager().beginTransaction();
        if (fromFragment != null) {
            fragmentTransaction.addToBackStack(fromFragment.getClass().getName());
        }
        fragmentTransaction.replace(Ui.getActivity().findViewById(R.id.fragment_container).getId(), toFragment);
        fragmentTransaction.commit();
    }
}
