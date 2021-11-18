package com.example.iinspector.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.R;
import com.example.iinspector.ui.gallery.GalleryHolder;

public class TodoHolder extends RecyclerView.ViewHolder{
    private View view;


    TodoHolder(View itemView) {
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

    private GalleryHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
    public void setOnClickListener(GalleryHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
