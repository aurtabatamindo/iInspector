package com.example.iinspector;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionHolder extends RecyclerView.ViewHolder{
    private View view;
//    CardView cardView1;
//    TextView qPertanyaan,qIsiPertanyaan;
    QuestionHolder(View itemView) {
        super(itemView);

        view = itemView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });
    }

    void setdescription(String description){
        TextView textView = view.findViewById(R.id.qIsiPertanyaan);
        textView.setText(description);

//        setOnClickListener(new ClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                if (cardView1.getVisibility() == View.VISIBLE){
//                    cardView1.setVisibility(View.GONE);
//                    qIsiPertanyaan.setVisibility(View.GONE);
//                    qPertanyaan.setVisibility(View.GONE);
//                }else{
//                    cardView1.setVisibility(View.VISIBLE);
//                    qIsiPertanyaan.setVisibility(View.VISIBLE);
//                    qPertanyaan.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
    }


    private QuestionHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
    public void setOnClickListener(QuestionHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
