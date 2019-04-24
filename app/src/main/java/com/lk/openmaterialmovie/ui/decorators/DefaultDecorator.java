/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.decorators;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("FieldCanBeLocal")
public class DefaultDecorator extends RecyclerView.ItemDecoration {

    // @formatter:off
    @Getter
    @Setter
    private Drawable divider;
    @Getter
    @Setter
    private Boolean showFirstDivider = true;
    @Getter
    @Setter
    private Boolean showMiddle = true;
    @Getter
    @Setter
    private Boolean showLastDivider = true;
    @Getter
    @Setter
    private Boolean firstFullSize = false;
    @Getter
    @Setter
    private Boolean lastFullSize = false;
    @Getter
    @Setter
    private int leftOffset = 0;
    @Getter
    @Setter
    private int rightOffset = 0;
    @Getter
    @Setter
    private int orientation = LinearLayoutManager.VERTICAL;
    // @formatter:on

    public DefaultDecorator(Drawable divider) {
        setDivider(divider);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //TODO: Make it customizable (first separator ON/OFF)
        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.left = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.drawColor(Color.WHITE, PorterDuff.Mode.SRC);
        // Initialization needed to avoid compiler warning
        int left = 0, right = 0, top = 0, bottom = 0, size;

        int childCount = parent.getChildCount();
        if (orientation == LinearLayoutManager.VERTICAL) {
            size = divider.getIntrinsicHeight();
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
        } else { //horizontal
            size = divider.getIntrinsicWidth();
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
        }
        int childCountMiddle = childCount;
        if (!showMiddle && childCount > 1) {
            childCountMiddle = 1;
        }
        for (int i = showFirstDivider ? 0 : 1; i < childCountMiddle; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getTop() - params.topMargin;
                bottom = top + size;
            } else { //horizontal
                left = child.getLeft() - params.leftMargin;
                right = left + size;
            }
            if (i == 0 && firstFullSize) {
                divider.setBounds(left, top, right, bottom);
            } else {
                divider.setBounds(left + leftOffset, top, right + rightOffset, bottom);
            }
            divider.draw(c);
        }
        // show last divider
        if (showLastDivider && childCount > 0) {
            View child = parent.getChildAt(childCount - 1);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getBottom() + params.bottomMargin - size;
                bottom = top + size;
            } else { // horizontal
                left = child.getRight() + params.rightMargin;
                right = left + size;
            }
            if (lastFullSize) {
                divider.setBounds(left, top, right, bottom);
            } else {
                divider.setBounds(left + leftOffset, top, right + rightOffset, bottom);
            }
            divider.draw(c);
        }
    }

}
