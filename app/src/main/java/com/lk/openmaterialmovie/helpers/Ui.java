/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;

import com.lk.openmaterialmovie.MainActivity;
import com.lk.openmaterialmovie.MainApplication;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.ui.activities.BaseActivity;
import com.lk.openmaterialmovie.ui.fragments.BaseFragment;

import lombok.Getter;
import lombok.Setter;

public class Ui {

    //@formatter:off
    @SuppressLint("StaticFieldLeak")
    @Getter
    @Setter
    private static BaseActivity activity;
    @Setter
    private static MainApplication application;
    //@formatter:on

    @SuppressWarnings("WeakerAccess")
    public static Context getBaseContext() {
        return application.getBaseContext();
    }

    public static MainActivity getMainActivity() {
        return (MainActivity) activity;
    }

    public static void run(Runnable action) {
        getActivity().runOnUiThread(action);
    }

    public static FragmentManager getFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    public static String getString(@StringRes int stringResId) {
        return getBaseContext().getString(stringResId);
    }

    public static String getString(@StringRes int stringResId, Object... args) {
        return getBaseContext().getString(stringResId, args);
    }

    /**
     * Use for runtime only, will not work in design mode
     */
    public static @ColorInt
    int getColorRuntime(@ColorRes int colorResId) {
        return getBaseContext().getResources().getColor(colorResId);
    }

    public static @ColorInt
    int getColor(Context context, @ColorRes int colorResId) {
        return context.getResources().getColor(colorResId);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableResId) {
        return context.getDrawable(drawableResId);
    }

    public static int toPixels(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static TypedArray obtainAttributes(Context context, @Nullable AttributeSet attrs, @StyleableRes int[] styleRes) {
        return context.getTheme().obtainStyledAttributes(attrs, styleRes, 0, 0);
    }

    public static boolean isTablet() {
        Configuration config = Ui.application.getResources().getConfiguration();
        return config.smallestScreenWidthDp >= 600;
    }

    public static BaseFragment getCurrentFragment() {
        return (BaseFragment) Ui.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }
}
