/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.factories;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.function.BiConsumer;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.Strings;
import com.lk.openmaterialmovie.enums.DecoratorType;
import com.lk.openmaterialmovie.helpers.PackageName;
import com.lk.openmaterialmovie.helpers.Reflection;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.log.Logger;
import com.lk.openmaterialmovie.ui.adapters.GenericAdapter;
import com.lk.openmaterialmovie.ui.decorators.DefaultDecorator;
import com.lk.openmaterialmovie.ui.decorators.GridEqualOffsetDecoration;
import com.lk.openmaterialmovie.ui.viewholders.BasicViewHolder;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.List;

public class ComponentFactory {

    private static final String LAYOUT = "layout";

    public static <T, E extends BasicViewHolder> GenericAdapter<T, E> initList(InitBuilder<T, E> initBuilder) {

        Class<E> viewHolderClass = initBuilder.viewHolderClass;
        RecyclerView recyclerView = initBuilder.recyclerView;
        List<T> adapterList = initBuilder.adapterList;
        Consumer<E> onCreateViewHolder = initBuilder.onCreateViewHolder;
        BiConsumer<E, T> onBindViewHolder = initBuilder.onBindViewHolder;
        LinearLayoutManager layoutManager = initBuilder.layoutManager;
        DecoratorType decoratorType = initBuilder.decoratorType;
        String layoutName = Reflection.getLayoutNameFor(viewHolderClass);
        @LayoutRes int cellResId = recyclerView.getContext().getResources().getIdentifier(layoutName, LAYOUT, PackageName.BASE);
        GenericAdapter<T, E> adapter = new GenericAdapter<T, E>(adapterList) {
            @Override
            public E createViewHolder(ViewGroup parent) {
                return constructViewHolder(viewHolderClass, parent, cellResId, recyclerView, onCreateViewHolder);
            }

            @Override
            public void bindViewHolder(E holder, T val) {
                onBindViewHolder.accept(holder, val);
            }
        };
        recyclerView.setLayoutManager(initLayoutManagerAndItemDecorator(layoutManager, recyclerView, decoratorType));
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private static <E extends BasicViewHolder> E constructViewHolder(Class<E> viewHolderClass, ViewGroup parent, @LayoutRes int cellResId, RecyclerView recyclerView,
                                                                     Consumer<E> onCreateViewHolder) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, cellResId, parent, false);
        E viewHolder;
        try {
            Constructor<E> viewHolderConstructor = viewHolderClass.getConstructor(Reflection.getBindingClassForResId(recyclerView.getContext(), cellResId));
            viewHolder = viewHolderConstructor.newInstance(binding);
        } catch (Exception e) {
            Logger.e(Strings.Log.EXCEPTION);
            throw new IllegalStateException(e);
        }
        onCreateViewHolder.accept(viewHolder);
        return viewHolder;
    }

    private static LinearLayoutManager initLayoutManagerAndItemDecorator(LinearLayoutManager layoutManager, RecyclerView recyclerView, DecoratorType decoratorType) {
        if (decoratorType == null) {
            decoratorType = DecoratorType.NO;
        }
        addItemDecorator(recyclerView, decoratorType);
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
        }
        return layoutManager;
    }

    private static void addItemDecorator(RecyclerView recyclerView, DecoratorType decoratorType) {
        if (recyclerView.getItemDecorationCount() > 0) {
            return; // avoid dancing spaces
        }
        RecyclerView.ItemDecoration itemDecorator;
        DefaultDecorator defaultDecorator = new DefaultDecorator(Ui.getDrawable(recyclerView.getContext(), R.drawable.decorator));
        switch (decoratorType) {
            case NO:
                return;
            case GRID_EQUAL_OFFSET:
                itemDecorator = new GridEqualOffsetDecoration(Ui.toPixels(16));
                break;
            case DEFAULT:
                itemDecorator = new DefaultDecorator(Ui.getDrawable(recyclerView.getContext(), R.drawable.decorator));
                break;
            case NO_TOP:
                defaultDecorator.setShowFirstDivider(false);
                defaultDecorator.setShowLastDivider(true);
                itemDecorator = defaultDecorator;
                break;
            case NO_BOTTOM:
                defaultDecorator.setShowFirstDivider(true);
                defaultDecorator.setShowLastDivider(false);
                itemDecorator = defaultDecorator;
                break;
            case TOP_ONLY:
                defaultDecorator.setShowFirstDivider(true);
                defaultDecorator.setShowLastDivider(false);
                defaultDecorator.setShowMiddle(false);
                itemDecorator = defaultDecorator;
                break;
            case LEFT_56_NO_TOP:
                defaultDecorator.setShowFirstDivider(false);
                defaultDecorator.setShowLastDivider(true);
                defaultDecorator.setLeftOffset(Ui.toPixels(56));
                defaultDecorator.setLastFullSize(true);
                itemDecorator = defaultDecorator;
                break;
            case LEFT_16_NO_BOTTOM:
                defaultDecorator.setShowFirstDivider(true);
                defaultDecorator.setShowLastDivider(false);
                defaultDecorator.setLeftOffset(Ui.toPixels(16));
                itemDecorator = defaultDecorator;
                break;

            default:
                throw new IllegalStateException(Strings.Log.DEFAULT_CASE_REACHED);
        }
        recyclerView.addItemDecoration(itemDecorator);
    }

    public static class InitBuilder<T, E> {

        Class<E> viewHolderClass;
        RecyclerView recyclerView;
        List<T> adapterList;
        Consumer<E> onCreateViewHolder;
        BiConsumer<E, T> onBindViewHolder;
        LinearLayoutManager layoutManager;
        DecoratorType decoratorType;
        Function<T, LocalDateTime> getTimeFunction;
        boolean reverseOrder;

        public InitBuilder<T, E> setViewHolderClass(Class<E> viewHolderClass) {
            this.viewHolderClass = viewHolderClass;
            return this;
        }

        public InitBuilder<T, E> setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public InitBuilder<T, E> setAdapterList(List<T> adapterList) {
            this.adapterList = adapterList;
            return this;
        }

        public InitBuilder<T, E> setOnCreateViewHolder(Consumer<E> onCreateViewHolder) {
            this.onCreateViewHolder = onCreateViewHolder;
            return this;
        }

        public InitBuilder<T, E> setOnBindViewHolder(BiConsumer<E, T> onBindViewHolder) {
            this.onBindViewHolder = onBindViewHolder;
            return this;
        }

        public InitBuilder<T, E> setLayoutManager(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }

        public InitBuilder<T, E> setDecoratorType(DecoratorType decoratorType) {
            this.decoratorType = decoratorType;
            return this;
        }

        // set getTimeFunction is intended
        public InitBuilder<T, E> setGetTimeFunction(Function<T, LocalDateTime> getTimeFunction) {
            this.getTimeFunction = getTimeFunction;
            return this;
        }

        public InitBuilder<T, E> setReverseOrder(boolean reverseOrder) {
            this.reverseOrder = reverseOrder;
            return this;
        }
    }

}

