package com.ibrahim.rozoshab.Main.MainActivity.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ibrahim.rozoshab.R;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by w-t- on 19-Oct-17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {



    public TextView categoryName;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        categoryName = (TextView) itemView.findViewById(R.id.categoryText);



    }


}
