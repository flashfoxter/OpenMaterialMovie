/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.decorators;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridEqualOffsetDecoration extends RecyclerView.ItemDecoration {

    private int desiredOffset;
    private int halfOffset;

    public GridEqualOffsetDecoration(int itemOffset) {
        desiredOffset = itemOffset;
        halfOffset = desiredOffset / 2;
    }

    public GridEqualOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int span = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int index = parent.getChildAdapterPosition(view);
        int size = parent.getAdapter().getItemCount();
        int leftOffset;
        int topOffset;
        int rightOffset;
        int bottomOffset;
        // Left
        if (index % span == 0) {
            leftOffset = desiredOffset;
        } else {
            leftOffset = halfOffset;
        }
        // Top
        if (index < span) {
            topOffset = desiredOffset;
        } else {
            topOffset = halfOffset;
        }
        // Right
        if (index % span == span - 1) {
            rightOffset = desiredOffset;
        } else {
            rightOffset = halfOffset;
        }
        // Bottom
        int lastRowElementCount = size % span;
        if (lastRowElementCount == 0) {
            lastRowElementCount = span;
        }
        if (index >= size - lastRowElementCount) {
            bottomOffset = desiredOffset;
        } else {
            bottomOffset = halfOffset;
        }
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset);
    }
}
