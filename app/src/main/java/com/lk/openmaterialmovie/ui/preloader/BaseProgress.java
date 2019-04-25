/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.preloader;

import android.view.View;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseProgress implements Progress {
    @Getter
    @Setter
    protected View view;

    public BaseProgress(View view) {
        this.view = view;
    }

}
