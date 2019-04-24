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

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

public class BaseActivity extends AppCompatActivity {

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
}
