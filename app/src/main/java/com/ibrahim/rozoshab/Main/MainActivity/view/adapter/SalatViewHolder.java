package com.ibrahim.rozoshab.Main.MainActivity.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.R;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

/**
 * Created by w-t- on 18-Oct-17.
 */

public class SalatViewHolder extends  RecyclerView.ViewHolder {

    public TextView salatName;
    public MaterialRadioGroup salartRadioGroup;
    public MaterialRadioButton radioButtonJamat,radioButtonIndividual,radioButtonQaza;
    MainActivityContractor.ViewToPresenter presenter;

    public SalatViewHolder(View itemView, MainActivityContractor.ViewToPresenter presenter) {
        super(itemView);

        this.presenter = presenter;

        salatName = (TextView) itemView.findViewById(R.id.textviewfajar);
        salartRadioGroup = (MaterialRadioGroup) itemView.findViewById(R.id.radiogroupfajar);
        radioButtonJamat = (MaterialRadioButton) itemView.findViewById(R.id.radiobutton_jamat);
        radioButtonIndividual = (MaterialRadioButton) itemView.findViewById(R.id.radiobutton_individual);
        radioButtonQaza = (MaterialRadioButton) itemView.findViewById(R.id.radiobutton_qaza);



    }
}
