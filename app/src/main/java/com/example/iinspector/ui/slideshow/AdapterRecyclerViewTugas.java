package com.example.iinspector.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.R;

public class AdapterRecyclerViewTugas extends RecyclerView.Adapter<AdapterRecyclerViewTugas.ViewHolder> {

    private String[] SubjectValues;
    private Context context;

    AdapterRecyclerViewTugas(Context context1, String[] SubjectValues1) {

        SubjectValues = SubjectValues1;
        context = context1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;


        ViewHolder(View v) {

            super(v);

            textView = v.findViewById(R.id.judul);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerViewTugas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_laporatugas, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(SubjectValues[position]);

    }

    @Override
    public int getItemCount() {

        return SubjectValues.length;
    }
}
