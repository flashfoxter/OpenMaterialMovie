/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.widgets.images;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.enums.PlaceHolderType;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.network.ApiClient;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

public class BaseImage extends AppCompatImageView {

    public BaseImage(Context context) {
        super(context);
        init(context, null);
    }

    public BaseImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, @Nullable AttributeSet attrs) {

    }

    public void load(String notFullUrl, PlaceHolderType placeholderType) {
        Drawable placeholder = null;
        switch (placeholderType) {
            case MOVIE:
                placeholder = Ui.getDrawable(getContext(), R.drawable.movie_placeholder);
                break;
        }
        String imageUrl = MessageFormat.format("{0}{1}?api_key={2}", ApiClient.URL_IMAGE, notFullUrl, Constants.KEY_THE_MOVIE_DB);
        Picasso.get().load(imageUrl).placeholder(placeholder).fit().centerCrop().into(this);
    }

    public void setActIion(Runnable action) {
        setOnClickListener(v -> action.run());
    }
}
