/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.annimon.stream.function.BiConsumer;
import com.annimon.stream.function.Consumer;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.enums.DecoratorType;
import com.lk.openmaterialmovie.factories.ComponentFactory;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.ui.EndlessRecyclerViewScrollListener;
import com.lk.openmaterialmovie.ui.adapters.GenericAdapter;
import com.lk.openmaterialmovie.ui.viewholders.BasicViewHolder;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CoreRecycler extends RecyclerView {

    @Getter
    @Setter
    protected Boolean bottomPadding;
    protected int bottomPaddingValueDp;
    protected EndlessRecyclerViewScrollListener scrollListener;
    @Getter
    @Setter
    Consumer<Integer> onLoadMore;

    public CoreRecycler(Context context) {
        super(context);
        init(context, null);
    }

    public CoreRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CoreRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (bottomPadding) {
            setClipToPadding(false);
            setPadding(0, 0, 0, Ui.toPixels(bottomPaddingValueDp));
        }
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = Ui.obtainAttributes(context, attrs, R.styleable.CoreRecycler);
        bottomPadding = typedArray.getBoolean(R.styleable.CoreRecycler_recycler_bottom_padding, true);
        bottomPaddingValueDp = typedArray.getInteger(R.styleable.CoreRecycler_recycler_bottom_padding_value, 16);
        typedArray.recycle();
        setHasFixedSize(true);
        setItemViewCacheSize(20);
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    @NonNull
    private GridLayoutManager getGridLayoutManager(@RecyclerView.Orientation int orientation) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setOrientation(orientation);
        return gridLayoutManager;
    }

    private void initPaging(LinearLayoutManager linearLayoutManager) {
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (onLoadMore != null) {
                    onLoadMore.accept(page);
                }
            }
        };
    }

    public <T, E extends BasicViewHolder> GenericAdapter<T, E> initList(Class<E> viewHolderClass,
                                                                        List<T> adapterList,
                                                                        Consumer<E> onCreateViewHolder,
                                                                        BiConsumer<E, T> onBindViewHolder,
                                                                        LinearLayoutManager layoutManager,
                                                                        DecoratorType decoratorType) {

        initPaging(layoutManager);
        ComponentFactory.InitBuilder<T, E> initBuilder = new ComponentFactory.InitBuilder<T, E>()
                .setViewHolderClass(viewHolderClass)
                .setAdapterList(adapterList)
                .setRecyclerView(this)
                .setOnBindViewHolder(onBindViewHolder)
                .setOnCreateViewHolder(onCreateViewHolder)
                .setLayoutManager(layoutManager)
                .setDecoratorType(decoratorType);

        return ComponentFactory.initList(initBuilder);
    }

    public <T, E extends BasicViewHolder> GenericAdapter<T, E> initList(Class<E> viewHolderClass,
                                                                        List<T> adapterList,
                                                                        Consumer<E> onCreateViewHolder,
                                                                        BiConsumer<E, T> onBindViewHolder) {

        ComponentFactory.InitBuilder<T, E> initBuilder = new ComponentFactory.InitBuilder<T, E>()
                .setViewHolderClass(viewHolderClass)
                .setAdapterList(adapterList)
                .setRecyclerView(this)
                .setOnBindViewHolder(onBindViewHolder)
                .setOnCreateViewHolder(onCreateViewHolder)
                .setDecoratorType(DecoratorType.DEFAULT);
        return ComponentFactory.initList(initBuilder);
    }
}
