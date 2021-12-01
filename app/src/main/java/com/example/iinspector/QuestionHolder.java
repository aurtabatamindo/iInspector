package com.example.iinspector;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionHolder extends RecyclerView.ViewHolder{
    private View view;


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
        TextView textView = view.findViewById(R.id.qPertanyaan);
        textView.setText(description);
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
