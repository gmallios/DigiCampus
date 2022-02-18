package com.project.digicampus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digicampus.R;
import com.project.digicampus.models.SubjectModel;

import java.util.ArrayList;

public class SubjectCardAdapter extends RecyclerView.Adapter<SubjectCardAdapter.Viewholder> {

    private Context context;
    private ArrayList<SubjectModel> subjectModelArrayList;
    private static ClickListener clickListener;

    // Constructor
    public SubjectCardAdapter(Context context, ArrayList<SubjectModel> subjectModelArrayList) {
        this.context = context;
        this.subjectModelArrayList = subjectModelArrayList;
    }

    @NonNull
    @Override
    public SubjectCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects_card_layout, parent, false);
        return new Viewholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SubjectCardAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        SubjectModel model = subjectModelArrayList.get(position);
        holder.subjectModel = model; // Pass whole model
        holder.subjectNameTextView.setText(model.getName());
        holder.subjectInfoTextView.setText(model.getDescription());
        holder.subjectSubscribedView.setVisibility(View.INVISIBLE); //TODO: Display checkmark on subscribed

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return subjectModelArrayList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        SubjectCardAdapter.clickListener = clickListener;
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView subjectNameTextView, subjectInfoTextView;
        private AppCompatImageView subjectSubscribedView;
        private SubjectModel subjectModel;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            subjectNameTextView = itemView.findViewById(R.id.card_subject_name);
            subjectInfoTextView = itemView.findViewById(R.id.card_subject_info);
            subjectSubscribedView = itemView.findViewById(R.id.card_subject_subscribed);
        }

        public SubjectModel getSubjectModel(){
            return subjectModel;
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

