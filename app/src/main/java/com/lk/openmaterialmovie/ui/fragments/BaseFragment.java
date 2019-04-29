package com.lk.openmaterialmovie.ui.fragments;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.lk.openmaterialmovie.helpers.Ui;

public class BaseFragment extends Fragment {

    protected void showBack() {
        Ui.getActivity().showBack();
    }

    protected void hideBack() {
        Ui.getActivity().hideBack();
    }

    protected void setTitle(String title) {
        Ui.getMainActivity().setTitleText(title);
    }

    protected void setTitle(@StringRes int string) {
        Ui.getMainActivity().setTitleText(string);
    }
}
