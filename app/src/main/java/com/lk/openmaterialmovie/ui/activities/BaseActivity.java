/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.navigator.Navigator;
import com.lk.openmaterialmovie.ui.preloader.Progress;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

public class BaseActivity extends AppCompatActivity {

    @Getter
    @Setter
    protected Progress progress;

    @Inject
    public Navigator navigator;

    @Getter
    @Setter
    private @IdRes
    int fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.setActivity(this);
    }

    public void progressShow() {
        progress.show();
    }

    public void progressHide() {
        progress.hide();
    }
}
