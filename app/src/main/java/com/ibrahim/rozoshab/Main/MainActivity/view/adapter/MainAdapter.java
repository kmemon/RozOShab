package com.ibrahim.rozoshab.Main.MainActivity.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;

/**
 * Created by w-t- on 18-Oct-17.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainActivityContractor.ViewToPresenter presenter;

    public MainAdapter(MainActivityContractor.ViewToPresenter presenter){
        this.presenter = presenter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.createViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        presenter.bindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {

        return presenter.getItemViewType(position);
    }



}
