package com.example.iinspector.ui.gallery;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.R;

public class GalleryHolder extends RecyclerView.ViewHolder {
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

//    void setNo (String no){
//        TextView textView = view.findViewById(R.id.no);
//        textView.setText(no);
//    }

    void setjudul(String judul) {
        TextView textView = view.findViewById(R.id.judul);
        textView.setText(judul);
    }

    void setteam(String team) {
        TextView textView = view.findViewById(R.id.team);
        textView.setText(team);
    }

    void setdevisi(String devisi) {
        TextView textView = view.findViewById(R.id.devisi);
        textView.setText(devisi);
    }
    void setlokasi(String lokasi) {
        TextView textView = view.findViewById(R.id.lokasi);
        textView.setText(lokasi);
    }
    void settanggal(String tanggal) {
        TextView textView = view.findViewById(R.id.tanggal);
        textView.setText(tanggal);
    }

//    void visible() {
//        TextView textView = view.findViewById(R.id.ceklis);
//        textView.setVisibility(View.INVISIBLE);
//    }
//    void visibleoff() {
//        TextView textView = view.findViewById(R.id.ceklis);
//        textView.setVisibility(View.VISIBLE);
//    }
    private GalleryHolder.ClickListener mClickListener;

//    public void hiden (int gone) {
//        TextView textView = view.findViewById(R.id.ceklis);
//        textView.setVisibility(View.GONE);
//    }

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
    public void setOnClickListener(GalleryHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
