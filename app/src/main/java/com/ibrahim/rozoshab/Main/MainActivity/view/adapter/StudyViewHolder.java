package com.ibrahim.rozoshab.Main.MainActivity.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ibrahim.rozoshab.R;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by w-t- on 19-Oct-17.
 */

public class StudyViewHolder extends RecyclerView.ViewHolder {

    public TextView studyTitle;
    public MaterialRadioGroup materialRadioGroup;
    public SmoothCheckBox studyCheckBox;
    public MaterialRadioButton radioButtonTafseer, radioButtonRecitation,radioButtonNotRead,getRadioButtonReadOnly;


    public StudyViewHolder(View itemView) {
        super(itemView);
        studyTitle = (TextView) itemView.findViewById(R.id.textviewstudy);
        studyCheckBox = (SmoothCheckBox) itemView.findViewById(R.id.study_check_box);
        materialRadioGroup = (MaterialRadioGroup) itemView.findViewById(R.id.study_radiogroup);

        radioButtonTafseer = (MaterialRadioButton) itemView.findViewById(R.id.radio_recitation);
        radioButtonRecitation = (MaterialRadioButton) itemView.findViewById(R.id.radio_tafseer);
        radioButtonNotRead = (MaterialRadioButton) itemView.findViewById(R.id.radio_not_read);
        getRadioButtonReadOnly = (MaterialRadioButton) itemView.findViewById(R.id.radio_read_only);

        studyCheckBox.setVisibility(View.GONE);
        studyCheckBox.setEnabled(false);



    }
}
