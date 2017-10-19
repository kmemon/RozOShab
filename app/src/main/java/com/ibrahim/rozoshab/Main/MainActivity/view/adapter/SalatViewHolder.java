package com.ibrahim.rozoshab.Main.MainActivity.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ibrahim.rozoshab.R;

/**
 * Created by w-t- on 18-Oct-17.
 */

public class SalatViewHolder extends  RecyclerView.ViewHolder {

    public TextView salatName;

    public SalatViewHolder(View itemView) {
        super(itemView);

        salatName = (TextView) itemView.findViewById(R.id.textviewfajar);



    }
}
