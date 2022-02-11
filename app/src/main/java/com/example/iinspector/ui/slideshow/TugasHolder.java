package com.example.iinspector.ui.slideshow;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.R;

public class TugasHolder extends RecyclerView.ViewHolder{
    private View view;


    TugasHolder(View itemView) {
        super(itemView);

        view = itemView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });
    }

    void setDesk(String deskripsi){
        TextView textView = view.findViewById(R.id.desk);
        textView.setText(deskripsi);
    }

//    void setTgroup(String group) {
//        TextView textView1 = view.findViewById(R.id.Tgroup1);
//        textView1.setText(group);
//
//        TextView textView2 = view.findViewById(R.id.Tgroup2);
//        textView1.setText(group);
//
//        if (group.equals("FSD")) {
//            textView1.setVisibility(View.INVISIBLE);
//        }else{
//            textView2.setVisibility(View.INVISIBLE);
//        }
//    }

    void setTitle(String titleTugas) {
        TextView textView = view.findViewById(R.id.judul);
        textView.setText(titleTugas);
    }

    void setTeam(String teamTugas){
        TextView textView = view.findViewById(R.id.team);
        textView.setText(teamTugas);
    }


    private TugasHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
    public void setOnClickListener(TugasHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
