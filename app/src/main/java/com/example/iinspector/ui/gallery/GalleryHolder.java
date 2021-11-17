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

    void setjudulKegiatan(String judulKegiatan) {
        TextView textView = view.findViewById(R.id.judul);
        textView.setText(judulKegiatan);
    }

    void setjenisTugas(String jenisTugas) {
        TextView textView = view.findViewById(R.id.jenis);
        textView.setText(jenisTugas);
    }

    void setwaktuMasuk(String waktuMasuk) {
        TextView textView = view.findViewById(R.id.tanggal);
        textView.setText(waktuMasuk.toString());
    }
    void setstatus(String status) {
        TextView textView = view.findViewById(R.id.status);
        textView.setText(status);
    }


    private ClickListener mClickListener;

//    public void hiden (int gone) {
//        TextView textView = view.findViewById(R.id.ceklis);
//        textView.setVisibility(View.GONE);
//    }

    //Interface to send callbacks...
    public interface ClickListener{
        void onItemClick(View view, int position);

    }
    public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }
}
