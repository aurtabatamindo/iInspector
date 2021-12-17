package com.example.iinspector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<GetDataQuestion> dataList;

    public QuestionAdapter(ArrayList<GetDataQuestion> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.qIsipertanyaan.setText(dataList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView qIsipertanyaan;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            qIsipertanyaan = (TextView) itemView.findViewById(R.id.qIsiPertanyaan);
        }
    }

}
