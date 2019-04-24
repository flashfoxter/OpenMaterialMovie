/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.ui.viewholders.BasicViewHolder;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class GenericAdapter<T, E extends BasicViewHolder> extends RecyclerView.Adapter<E> {
    @Getter
    @Setter
    private List<T> items;

    protected GenericAdapter(List<T> items) {
        this.items = items;
    }

    public abstract E createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(E holder, T val);

    @NonNull
    @Override
    public E onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull E holder, int position) {
        holder.bind();
        bindViewHolder(holder, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }
}