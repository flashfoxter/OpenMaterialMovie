/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.preloader;

import android.view.View;

public class ProgressImpl extends BaseProgress {

    public ProgressImpl(View view) {
        super(view);
    }

    private void setVisible(boolean visible) {
        getView().post(() -> view.setVisibility(visible ? View.VISIBLE : View.GONE));
    }

    @Override
    public void show() {
        setVisible(true);
    }

    @Override
    public void hide() {
        setVisible(false);
    }
}
