package com.ibrahim.rozoshab.Main.MainActivity.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ibrahim.rozoshab.R;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by w-t- on 20-Oct-17.
 */

public class DistributionFamiylViewHolder extends RecyclerView.ViewHolder {

    public TextView text_distribution_family;
    public SmoothCheckBox smoothCheckBox;

    public DistributionFamiylViewHolder(View itemView) {
        super(itemView);
        text_distribution_family = (TextView) itemView.findViewById(R.id.text_distribution_family);
        smoothCheckBox = (SmoothCheckBox) itemView.findViewById(R.id.circle_check_box);
    }
}
