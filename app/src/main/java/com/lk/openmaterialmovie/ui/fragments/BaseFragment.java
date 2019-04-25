package com.lk.openmaterialmovie.ui.fragments;

import android.support.v4.app.Fragment;

import com.lk.openmaterialmovie.helpers.Ui;

public class BaseFragment extends Fragment {

    protected void showBack() {
        Ui.getActivity().showBack();
    }

    protected void hideBack() {
        Ui.getActivity().hideBack();
    }
}
