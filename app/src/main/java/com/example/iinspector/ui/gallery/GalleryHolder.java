package com.example.iinspector.ui.gallery;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.R;


public class GalleryHolder extends RecyclerView.ViewHolder  {

    private View view;


    GalleryHolder(View itemView) {
        super(itemView);

        view = itemView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });
    }

    void setTitle(String title) {
        TextView textView = view.findViewById(R.id.Ttitle);
        textView.setText(title);
    }

    void setTgroup(String group) {
        TextView textView = view.findViewById(R.id.Tgroup);
        textView.setText(group);
    }

    private ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
    public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }
}
