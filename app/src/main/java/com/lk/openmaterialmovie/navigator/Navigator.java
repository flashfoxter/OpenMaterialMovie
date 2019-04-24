/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.navigator;

import android.support.annotation.Nullable;

import com.lk.openmaterialmovie.ui.activities.BaseActivity;
import com.lk.openmaterialmovie.ui.fragments.BaseFragment;

public interface Navigator {
    void setFragment(@Nullable BaseFragment fromFragment, BaseFragment toFragment, BaseActivity activity);
}
