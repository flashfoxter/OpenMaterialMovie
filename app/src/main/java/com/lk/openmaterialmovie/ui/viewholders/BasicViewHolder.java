/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.viewholders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.function.Consumer;

import lombok.Getter;
import lombok.Setter;

public class BasicViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T b;
    @Getter
    @Setter
    private Consumer<Integer> onClick;

    public BasicViewHolder(T binding) {
        super(binding.getRoot());
        b = binding;
        itemView.setOnClickListener(v -> {
            if (onClick != null) {
                onClick.accept(getAdapterPosition());
            }
        });
    }

    public void bind() {
        b.executePendingBindings();
    }
}

