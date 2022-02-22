package com.project.digicampus.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.digicampus.R;

import java.util.ArrayList;


public class CalendarCardAdapter extends RecyclerView.Adapter<CalendarCardAdapter.Viewholder> {
    private Context mContext;
    private ArrayList<String> textList;
    private static ClickListener clickListener;

    public CalendarCardAdapter(Context context, ArrayList<String> text){
        this.mContext = context;
        textList = text;
    }

    @NonNull
    @Override
    public CalendarCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_card, parent, false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarCardAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
       holder.textView.setText(textList.get(position));
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(CalendarCardAdapter.ClickListener clickListener){
        CalendarCardAdapter.clickListener = clickListener;
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.calendar_card_text);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
}
